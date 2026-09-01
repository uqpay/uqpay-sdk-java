package com.uqpay.sdk.connect;

import com.fasterxml.jackson.databind.JsonNode;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.connect.model.CreateSubAccountRequest;
import com.uqpay.sdk.connect.model.EntityType;
import com.uqpay.sdk.connect.model.SubAccountBusinessDetails;
import com.uqpay.sdk.connect.model.SubAccountCompanyAddress;
import com.uqpay.sdk.connect.model.SubAccountCompanyInfo;
import com.uqpay.sdk.connect.model.SubAccountCompanyPurpose;
import com.uqpay.sdk.connect.model.SubAccountOwnershipDetails;
import com.uqpay.sdk.connect.model.SubAccountRepresentative;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountsServiceCompanyValidationTest {
    private MockWebServer server;
    private AccountsService service;
    private ApiClient apiClient;

    @BeforeEach
    void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
        Configuration configuration = Configuration.builder()
                .clientId("client-id")
                .apiKey("unused-api-key")
                .baseUrlOverride(server.url("/").toString().replaceAll("/$", ""))
                .build();
        apiClient = new ApiClient(configuration, () -> "test-token");
        service = new AccountsService(apiClient);
    }

    @AfterEach
    void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    void rejectsMissingCompanySectionsBeforeSendingRequest() {
        CreateSubAccountRequest request = validCompanyRequest();
        request.setCompanyInfo(null);
        assertRejected(request,
                "company_info required for COMPANY entity type when inherit != 1");

        request = validCompanyRequest();
        request.setCompanyAddress(null);
        assertRejected(request,
                "company_address required for COMPANY entity type when inherit != 1");

        request = validCompanyRequest();
        request.setOwnershipDetails(null);
        assertRejected(request,
                "ownership_details required for COMPANY entity type when inherit != 1");

        request = validCompanyRequest();
        request.setBusinessDetails(null);
        assertRejected(request,
                "business_details required for COMPANY entity type when inherit != 1");

        assertThat(server.getRequestCount()).isZero();
    }

    @Test
    void rejectsMissingRepresentativesAndNewRepresentativeFields() {
        CreateSubAccountRequest request = validCompanyRequest();
        request.getOwnershipDetails().setRepresentatives(null);
        assertRejected(request,
                "ownership_details.representatives required for COMPANY entity type when inherit != 1");

        request = validCompanyRequest();
        request.getOwnershipDetails().setRepresentatives(Collections.singletonList(null));
        assertRejected(request,
                "ownership_details.representatives[0] must not be null for COMPANY entity type when inherit != 1");

        request = validCompanyRequest();
        request.getOwnershipDetails().getRepresentatives().get(0).setEmailAddress(null);
        assertRejected(request,
                "ownership_details.representatives[0].email_address required for COMPANY entity type when inherit != 1");

        request = validCompanyRequest();
        request.getOwnershipDetails().getRepresentatives().get(0).setOwnershipPercentage(null);
        assertRejected(request,
                "ownership_details.representatives[0].ownership_percentage required for COMPANY entity type when inherit != 1");

        request = validCompanyRequest();
        request.getOwnershipDetails().getRepresentatives().get(0).setDateOfBirth(null);
        assertRejected(request,
                "ownership_details.representatives[0].date_of_birth required for COMPANY entity type when inherit != 1");

        assertThat(server.getRequestCount()).isZero();
    }

    @Test
    void rejectsMissingNewBusinessFields() {
        CreateSubAccountRequest request = validCompanyRequest();
        request.getBusinessDetails().setAccountPurpose(null);
        assertRejected(request,
                "business_details.account_purpose required for COMPANY entity type when inherit != 1");

        request = validCompanyRequest();
        request.getBusinessDetails().setBankingCurrencies(null);
        assertRejected(request,
                "business_details.banking_currencies required for COMPANY entity type when inherit != 1");

        request = validCompanyRequest();
        request.getBusinessDetails().setBankingCountries(null);
        assertRejected(request,
                "business_details.banking_countries required for COMPANY entity type when inherit != 1");

        request = validCompanyRequest();
        request.getBusinessDetails().setArticlesOfAssociation(null);
        assertRejected(request,
                "business_details.articles_of_association required for COMPANY entity type when inherit != 1");

        assertThat(server.getRequestCount()).isZero();
    }

    @Test
    void sendsValidNonInheritingCompanyRequest() throws Exception {
        server.enqueue(jsonResponse());

        service.createSubAccount(validCompanyRequest());

        RecordedRequest recorded = server.takeRequest();
        JsonNode body = apiClient.getObjectMapper().readTree(recorded.getBody().readUtf8());
        assertThat(recorded.getPath()).isEqualTo("/v1/accounts/create_accounts");
        assertThat(body.get("entity_type").asText()).isEqualTo("COMPANY");
        assertThat(body.get("inherit").asInt()).isEqualTo(-1);
        assertThat(body.at("/ownership_details/representatives/0/email_address").asText())
                .isEqualTo("owner@example.com");
        assertThat(body.at("/business_details/account_purpose/0").asText())
                .isEqualTo("PAYMENT_COLLECTION");
    }

    @Test
    void inheritOneBypassesCompanyDetailValidation() throws Exception {
        server.enqueue(jsonResponse());
        CreateSubAccountRequest request = new CreateSubAccountRequest();
        request.setEntityType(EntityType.COMPANY);
        request.setInherit(1);

        service.createSubAccount(request);

        assertThat(server.takeRequest().getPath()).isEqualTo("/v1/accounts/create_accounts");
    }

    private void assertRejected(CreateSubAccountRequest request, String expectedMessage) {
        assertThatThrownBy(() -> service.createSubAccount(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    private CreateSubAccountRequest validCompanyRequest() {
        SubAccountRepresentative representative = new SubAccountRepresentative();
        representative.setEmailAddress("owner@example.com");
        representative.setOwnershipPercentage("0");
        representative.setDateOfBirth("1985-03-20");

        SubAccountOwnershipDetails ownershipDetails = new SubAccountOwnershipDetails();
        ownershipDetails.setRepresentatives(Collections.singletonList(representative));

        SubAccountBusinessDetails businessDetails = new SubAccountBusinessDetails();
        businessDetails.setAccountPurpose(
                Collections.singletonList(SubAccountCompanyPurpose.PAYMENT_COLLECTION));
        businessDetails.setBankingCurrencies(Collections.singletonList("SGD"));
        businessDetails.setBankingCountries(Collections.singletonList("SG"));
        businessDetails.setArticlesOfAssociation(Collections.singletonList("file-id"));

        CreateSubAccountRequest request = new CreateSubAccountRequest();
        request.setEntityType(EntityType.COMPANY);
        request.setInherit(-1);
        request.setCompanyInfo(new SubAccountCompanyInfo());
        request.setCompanyAddress(new SubAccountCompanyAddress());
        request.setOwnershipDetails(ownershipDetails);
        request.setBusinessDetails(businessDetails);
        return request;
    }

    private MockResponse jsonResponse() {
        return new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{}");
    }
}
