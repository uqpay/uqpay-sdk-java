package com.uqpay.sdk.banking;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uqpay.sdk.banking.model.*;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayApiException;
import com.uqpay.sdk.config.Configuration;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

class VirtualAccountApplicationContractTest {
    private MockWebServer server;
    private VirtualAccountsService service;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
        Configuration configuration = Configuration.builder()
                .clientId("client-id")
                .apiKey("unused-api-key")
                .baseUrlOverride(server.url("/").toString().replaceAll("/$", ""))
                .build();
        ApiClient apiClient = new ApiClient(configuration, () -> "test-token");
        service = new VirtualAccountsService(apiClient);
        mapper = apiClient.getObjectMapper();
    }

    @AfterEach
    void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    void createNormalizesRequestAndSendsContractHeaders() throws Exception {
        server.enqueue(jsonResponse("{\"data\":{\"application_id\":\"app-1\","
                + "\"account_id\":\"connected-account-id\",\"direct_id\":\"main-account-id\",\"public_version\":1,"
                + "\"country\":\"SG\",\"currency\":\"USD\",\"status\":\"SUBMITTED\","
                + "\"results\":[{\"payment_method\":\"LOCAL\",\"status\":\"SUBMITTED\","
                + "\"virtual_accounts\":[],\"error\":null}]}}"));

        CreateVirtualAccountRequest request = new CreateVirtualAccountRequest();
        request.setCountry(" sg ");
        request.setCurrency(" usd ");
        request.setPaymentMethod(VirtualAccountPaymentMethod.LOCAL);
        request.setNickname("   ");
        RequestOptions options = RequestOptions.builder()
                .idempotencyKey("va-create-001")
                .onBehalfOf("connected-account-id")
                .build();

        CreateVirtualAccountResponse response = service.create(request, options);
        RecordedRequest recorded = server.takeRequest();

        assertThat(recorded.getMethod()).isEqualTo("POST");
        assertThat(recorded.getPath()).isEqualTo("/v1/virtual/accounts");
        assertThat(recorded.getHeader("x-idempotency-key")).isEqualTo("va-create-001");
        assertThat(recorded.getHeader("x-on-behalf-of")).isEqualTo("connected-account-id");
        assertThat(recorded.getHeader("x-request-id")).isNull();
        JsonNode body = mapper.readTree(recorded.getBody().readUtf8());
        assertThat(body.get("country").asText()).isEqualTo("SG");
        assertThat(body.get("currency").asText()).isEqualTo("USD");
        assertThat(body.get("payment_method").asText()).isEqualTo("LOCAL");
        assertThat(body.has("nickname")).isFalse();
        assertThat(response.getData().getApplicationId()).isEqualTo("app-1");
        assertThat(response.getData().getAccountId()).isEqualTo("connected-account-id");
        assertThat(response.getData().getDirectId()).isEqualTo("main-account-id");
    }

    @Test
    void identicalCreateReplayUsesSameKeyAndReturnsOriginalApplication() throws Exception {
        String responseJson = "{\"data\":{\"application_id\":\"app-original\","
                + "\"account_id\":\"account-1\",\"direct_id\":\"0\",\"public_version\":1,"
                + "\"country\":\"SG\",\"currency\":\"USD\",\"status\":\"SUBMITTED\","
                + "\"results\":[{\"payment_method\":\"SWIFT\",\"status\":\"SUBMITTED\","
                + "\"virtual_accounts\":[],\"error\":null}]}}";
        server.enqueue(jsonResponse(responseJson));
        server.enqueue(jsonResponse(responseJson));
        CreateVirtualAccountRequest request = new CreateVirtualAccountRequest();
        request.setCountry("SG");
        request.setCurrency("USD");
        request.setPaymentMethod(VirtualAccountPaymentMethod.SWIFT);
        RequestOptions options = RequestOptions.withIdempotencyKey("stable-replay-key");

        CreateVirtualAccountResponse first = service.create(request, options);
        CreateVirtualAccountResponse replay = service.create(request, options);

        assertThat(first.getData().getApplicationId()).isEqualTo("app-original");
        assertThat(replay.getData().getApplicationId()).isEqualTo("app-original");
        assertThat(first.getData().getAccountId()).isEqualTo("account-1");
        assertThat(first.getData().getDirectId()).isEqualTo("0");
        assertThat(server.takeRequest().getHeader("x-idempotency-key")).isEqualTo("stable-replay-key");
        assertThat(server.takeRequest().getHeader("x-idempotency-key")).isEqualTo("stable-replay-key");
    }

    @Test
    void createRejectsMultipleCurrenciesAndInvalidIdempotencyKey() {
        CreateVirtualAccountRequest request = new CreateVirtualAccountRequest();
        request.setCountry("SG");
        request.setCurrency("USD,EUR");
        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("one ISO-3");

        request.setCurrency("USD");
        assertThatThrownBy(() -> service.create(request, RequestOptions.withIdempotencyKey(" ")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1 to 64");
    }

    @Test
    void listApplicationsUsesDistinctPathRequiredPaginationAndFilters() throws Exception {
        server.enqueue(jsonResponse("{\"total_pages\":1,\"total_items\":1,\"data\":[{"
                + "\"application_id\":\"app-1\",\"account_id\":\"account-1\",\"direct_id\":\"0\","
                + "\"public_version\":2,\"country\":\"SG\","
                + "\"currency\":\"USD\",\"status\":\"PARTIALLY_COMPLETED\","
                + "\"created_at\":\"2026-08-12T10:00:00Z\"}]}"));
        ListVirtualAccountApplicationsRequest request = new ListVirtualAccountApplicationsRequest();
        request.setPageNumber(1);
        request.setPageSize(50);
        request.setStatus(VirtualAccountApplicationStatus.PARTIALLY_COMPLETED);
        request.setCountry(" sg ");
        request.setCurrency(" usd ");

        ListVirtualAccountApplicationsResponse response = service.listApplications(request,
                RequestOptions.builder().onBehalfOf("connected-account-id").build());
        RecordedRequest recorded = server.takeRequest();

        assertThat(recorded.getPath()).isEqualTo("/v1/virtual/applications?page_number=1&page_size=50"
                + "&status=PARTIALLY_COMPLETED&country=SG&currency=USD");
        assertThat(recorded.getHeader("x-on-behalf-of")).isEqualTo("connected-account-id");
        assertThat(response.getTotalItems()).isEqualTo(1);
        assertThat(response.getData().get(0).getAccountId()).isEqualTo("account-1");
        assertThat(response.getData().get(0).getDirectId()).isEqualTo("0");
        assertThat(response.getData().get(0).getCreatedAt().toString()).isEqualTo("2026-08-12T10:00Z");

        ListVirtualAccountApplicationsRequest invalid = new ListVirtualAccountApplicationsRequest();
        invalid.setPageNumber(0);
        invalid.setPageSize(50);
        assertThatThrownBy(() -> service.listApplications(invalid))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void retrieveParsesCompleteApplicationAndIgnoresUndefinedFields() throws Exception {
        server.enqueue(jsonResponse("{\"data\":{\"application_id\":\"app-1\","
                + "\"account_id\":\"connected-account-id\",\"direct_id\":\"main-account-id\",\"public_version\":3,"
                + "\"country\":\"SG\",\"currency\":\"USD\",\"status\":\"CLOSED\","
                + "\"undefined_field\":\"ignored\",\"results\":[{\"payment_method\":\"SWIFT\","
                + "\"status\":\"CLOSED\",\"error\":null,\"virtual_accounts\":[{"
                + "\"account_bank_id\":\"bank-1\",\"account_holder\":\"Example Merchant\","
                + "\"account_number\":\"00123\",\"country_code\":\"SG\",\"currency\":\"USD\","
                + "\"bank_name\":\"Example Bank\",\"bank_address\":\"1 Example Street\","
                + "\"clearing_system\":{\"type\":\"bic_swift\",\"value\":\"EXAMPLE1\"},"
                + "\"status\":\"CLOSED\",\"close_reason\":\"\"}]}]}}"));

        RetrieveVirtualAccountApplicationResponse response = service.retrieveApplication("app-1");
        RecordedRequest recorded = server.takeRequest();
        VirtualAccountApplicationBankDetail detail = response.getData().getResults().get(0)
                .getVirtualAccounts().get(0);

        assertThat(recorded.getPath()).isEqualTo("/v1/virtual/applications/app-1");
        assertThat(response.getData().getPublicVersion()).isEqualTo(3);
        assertThat(response.getData().getAccountId()).isEqualTo("connected-account-id");
        assertThat(response.getData().getDirectId()).isEqualTo("main-account-id");
        assertThat(detail.getClearingSystem().getType()).isEqualTo("bic_swift");
        assertThat(detail.getAccountNumber()).isEqualTo("00123");
        assertThat(detail.getStatus()).isEqualTo(VirtualAccountBankDetailStatus.CLOSED);
        assertThat(detail.getCloseReason()).isEmpty();
    }

    @Test
    void restApplicationDtoExposesRequiredAccountCorrelationFields() {
        VirtualAccountApplication application = new VirtualAccountApplication();
        application.setApplicationId("app-1");
        application.setAccountId("account-1");
        application.setDirectId("0");
        application.setPublicVersion(1);
        application.setCountry("SG");
        application.setCurrency("USD");
        application.setStatus(VirtualAccountApplicationStatus.SUBMITTED);

        JsonNode json = mapper.valueToTree(application);

        assertThat(json.get("account_id").asText()).isEqualTo("account-1");
        assertThat(json.get("direct_id").asText()).isEqualTo("0");
    }

    @Test
    void retrievePreservesStrictHttp400NotFoundError() {
        server.enqueue(new MockResponse().setResponseCode(400)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"type\":\"not_found\","
                        + "\"code\":\"virtual_account_application_not_found\","
                        + "\"message\":\"Virtual account application not found\"}"));

        Throwable thrown = catchThrowable(() -> service.retrieveApplication("missing-or-other-account"));
        assertThat(thrown).isInstanceOf(UqpayApiException.class);
        UqpayApiException error = (UqpayApiException) thrown;
        assertThat(error.getStatusCode()).isEqualTo(400);
        assertThat(error.getType()).isEqualTo("not_found");
        assertThat(error.getCode()).isEqualTo("virtual_account_application_not_found");
        assertThat(error.getErrorMessage()).isEqualTo("Virtual account application not found");
        assertThat(error.isVirtualAccountApplicationNotFound()).isTrue();
    }

    private static MockResponse jsonResponse(String body) {
        return new MockResponse().setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(body);
    }
}
