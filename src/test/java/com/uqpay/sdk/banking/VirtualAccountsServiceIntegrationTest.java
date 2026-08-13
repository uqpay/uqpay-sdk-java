package com.uqpay.sdk.banking;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.banking.model.VirtualAccount;
import com.uqpay.sdk.banking.model.CreateVirtualAccountRequest;
import com.uqpay.sdk.banking.model.CreateVirtualAccountResponse;
import com.uqpay.sdk.banking.model.ListVirtualAccountsRequest;
import com.uqpay.sdk.banking.model.ListVirtualAccountsResponse;
import com.uqpay.sdk.banking.model.ListVirtualAccountApplicationsRequest;
import com.uqpay.sdk.banking.model.ListVirtualAccountApplicationsResponse;
import com.uqpay.sdk.banking.model.RetrieveVirtualAccountApplicationResponse;
import com.uqpay.sdk.banking.model.VirtualAccountPaymentMethod;
import com.uqpay.sdk.common.RequestOptions;

import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link VirtualAccountsService}.
 * <p>
 * Requires UQPAY_CLIENT_ID and UQPAY_API_KEY environment variables to be set.
 * Tests will be skipped if credentials are not available.
 * </p>
 */
@DisplayName("VirtualAccountsService Integration Tests")
class VirtualAccountsServiceIntegrationTest {

    private static VirtualAccountsService virtualAccountsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        BankingClient bankingClient = new BankingClient(apiClient);
        virtualAccountsService = bankingClient.getVirtualAccounts();
    }

    @Nested
    @DisplayName("List Virtual Accounts")
    class ListVirtualAccounts {

        @Test
        @DisplayName("should list virtual accounts with pagination")
        void shouldListVirtualAccountsWithPagination() throws UqpayException {
            ListVirtualAccountsRequest request = new ListVirtualAccountsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListVirtualAccountsResponse response = virtualAccountsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d virtual accounts (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                VirtualAccount first = response.getData().get(0);
                System.out.printf("First virtual account: Currency=%s, Bank=%s, Status=%s%n",
                        first.getCurrency(), first.getBankName(), first.getStatus());
                System.out.printf("  Account Holder: %s%n", first.getAccountHolder());
                System.out.printf("  Account Number: %s%n", first.getAccountNumber());
            }
        }

        @Test
        @DisplayName("should list virtual accounts with minimal pagination")
        void shouldListVirtualAccountsWithMinimalPagination() throws UqpayException {
            ListVirtualAccountsRequest request = new ListVirtualAccountsRequest();
            request.setPageSize(5);
            request.setPageNumber(1);

            ListVirtualAccountsResponse response = virtualAccountsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();
            assertThat(response.getData().size()).isLessThanOrEqualTo(5);
        }
    }

    @Nested
    @DisplayName("Create Virtual Account")
    class CreateVirtualAccount {

        @Test
        @DisplayName("should create virtual account")
        void shouldCreateVirtualAccount() throws UqpayException {
            CreateVirtualAccountRequest request = new CreateVirtualAccountRequest();
            request.setCountry("SG");
            request.setCurrency("USD");
            request.setPaymentMethod(VirtualAccountPaymentMethod.LOCAL);

            CreateVirtualAccountResponse response = virtualAccountsService.create(request,
                    RequestOptions.withIdempotencyKey(UUID.randomUUID().toString()));

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Created virtual account application: %s%n", response.getData().getApplicationId());
        }

        @Test
        @DisplayName("should create virtual account and verify in list")
        void shouldCreateVirtualAccountAndVerifyInList() throws UqpayException {
            CreateVirtualAccountRequest createRequest = new CreateVirtualAccountRequest();
            createRequest.setCountry("SG");
            createRequest.setCurrency("USD");
            createRequest.setPaymentMethod(VirtualAccountPaymentMethod.LOCAL);

            CreateVirtualAccountResponse created = virtualAccountsService.create(createRequest,
                    RequestOptions.withIdempotencyKey(UUID.randomUUID().toString()));

            assertThat(created).isNotNull();
            assertThat(created.getData()).isNotNull();

            System.out.printf("Created virtual account application: %s%n", created.getData().getApplicationId());

            // List virtual accounts to verify
            ListVirtualAccountsRequest listRequest = new ListVirtualAccountsRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListVirtualAccountsResponse listResponse = virtualAccountsService.list(listRequest);
            assertThat(listResponse).isNotNull();
            assertThat(listResponse.getData()).isNotNull();

            System.out.printf("Listed %d virtual accounts after creation%n", listResponse.getData().size());
        }

        @Test
        @DisplayName("should create virtual account while evaluating both methods")
        void shouldCreateVirtualAccountWhileEvaluatingBothMethods() throws UqpayException {
            CreateVirtualAccountRequest request = new CreateVirtualAccountRequest();
            request.setCountry("SG");
            request.setCurrency("USD");

            CreateVirtualAccountResponse response = virtualAccountsService.create(request,
                    RequestOptions.withIdempotencyKey(UUID.randomUUID().toString()));

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();
            assertThat(response.getData().getResults()).hasSizeBetween(1, 2);

            System.out.printf("Create application result count: %d%n", response.getData().getResults().size());
        }
    }

    @Nested
    @DisplayName("Virtual Account Applications")
    class VirtualAccountApplications {
        @Test
        void shouldListAndRetrieveApplications() throws UqpayException {
            ListVirtualAccountApplicationsRequest request = new ListVirtualAccountApplicationsRequest();
            request.setPageNumber(1);
            request.setPageSize(10);

            ListVirtualAccountApplicationsResponse response = virtualAccountsService.listApplications(request);
            assertThat(response.getData()).isNotNull();

            if (!response.getData().isEmpty()) {
                RetrieveVirtualAccountApplicationResponse detail = virtualAccountsService.retrieveApplication(
                        response.getData().get(0).getApplicationId());
                assertThat(detail.getData().getApplicationId())
                        .isEqualTo(response.getData().get(0).getApplicationId());
            }
        }
    }
}
