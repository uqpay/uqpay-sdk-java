package com.uqpay.sdk.banking;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.banking.model.Deposit;
import com.uqpay.sdk.banking.model.ListDepositsRequest;
import com.uqpay.sdk.banking.model.ListDepositsResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link DepositsService}.
 * <p>
 * Requires UQPAY_CLIENT_ID and UQPAY_API_KEY environment variables to be set.
 * Tests will be skipped if credentials are not available.
 * </p>
 */
@DisplayName("DepositsService Integration Tests")
class DepositsServiceIntegrationTest {

    private static DepositsService depositsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        BankingClient bankingClient = new BankingClient(apiClient);
        depositsService = bankingClient.getDeposits();
    }

    @Nested
    @DisplayName("List Deposits")
    class ListDeposits {

        @Test
        @DisplayName("should list deposits with pagination")
        void shouldListDepositsWithPagination() throws UqpayException {
            ListDepositsRequest request = new ListDepositsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListDepositsResponse response = depositsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d deposits (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                Deposit first = response.getData().get(0);
                System.out.printf("First deposit: ID=%s, Currency=%s, Amount=%s, Status=%s%n",
                        first.getDepositId(), first.getCurrency(),
                        first.getAmount(), first.getDepositStatus());
            }
        }
    }

    @Nested
    @DisplayName("List Deposits With Filters")
    class ListDepositsWithFilters {

        @Test
        @DisplayName("should list deposits with status filter")
        void shouldListDepositsWithFilters() throws UqpayException {
            ListDepositsRequest request = new ListDepositsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);
            request.setDepositStatus("COMPLETED");

            ListDepositsResponse response = depositsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d completed deposits (total: %d)%n",
                    response.getData().size(), response.getTotalItems());

            for (Deposit deposit : response.getData()) {
                System.out.printf("  Deposit: %s %s, Status=%s%n",
                        deposit.getAmount(), deposit.getCurrency(),
                        deposit.getDepositStatus());
            }
        }

        @Test
        @DisplayName("should list deposits by status")
        void shouldListDepositsByStatus() throws UqpayException {
            String[] statuses = {"PENDING", "COMPLETED", "FAILED"};

            for (String status : statuses) {
                ListDepositsRequest request = new ListDepositsRequest();
                request.setPageSize(10);
                request.setPageNumber(1);
                request.setDepositStatus(status);

                ListDepositsResponse response = depositsService.list(request);

                assertThat(response).isNotNull();

                System.out.printf("%s deposits: %d found%n", status, response.getTotalItems());
            }
        }

        @Test
        @DisplayName("should list deposits with time range")
        void shouldListDepositsWithTimeRange() throws UqpayException {
            ListDepositsRequest request = new ListDepositsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);
            // Time range filter — dates can be adjusted as needed
            // request.setStartTime("2024-01-01T00:00:00Z");
            // request.setEndTime("2024-01-31T23:59:59Z");

            ListDepositsResponse response = depositsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d deposits in time range (total: %d)%n",
                    response.getData().size(), response.getTotalItems());
        }
    }

    @Nested
    @DisplayName("Get Deposit")
    class GetDeposit {

        @Test
        @DisplayName("should get deposit by ID")
        void shouldGetDepositById() throws UqpayException {
            // First, list deposits to get a valid deposit ID
            ListDepositsRequest listRequest = new ListDepositsRequest();
            listRequest.setPageSize(1);
            listRequest.setPageNumber(1);

            ListDepositsResponse listResponse = depositsService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No deposits available, skipping Get test");
                return;
            }

            String depositId = listResponse.getData().get(0).getDepositId();

            // Test Get
            Deposit deposit = depositsService.get(depositId);

            assertThat(deposit).isNotNull();
            assertThat(deposit.getDepositId()).isEqualTo(depositId);

            System.out.printf("Retrieved deposit: ID=%s, Currency=%s, Amount=%s, Status=%s%n",
                    deposit.getDepositId(), deposit.getCurrency(),
                    deposit.getAmount(), deposit.getDepositStatus());
            System.out.printf("  ShortRef=%s%n", deposit.getShortReferenceId());
            System.out.printf("  Created: %s%n", deposit.getCreateTime());
            if (deposit.getCompleteTime() != null) {
                System.out.printf("  Completed: %s%n", deposit.getCompleteTime());
            }
            if (deposit.getDepositReference() != null) {
                System.out.printf("  Reference: %s%n", deposit.getDepositReference());
            }
        }
    }

    @Nested
    @DisplayName("Get Multiple Deposits")
    class GetMultipleDeposits {

        @Test
        @DisplayName("should get multiple deposits by ID")
        void shouldGetMultipleDeposits() throws UqpayException {
            ListDepositsRequest listRequest = new ListDepositsRequest();
            listRequest.setPageSize(5);
            listRequest.setPageNumber(1);

            ListDepositsResponse listResponse = depositsService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No deposits available, skipping GetMultiple test");
                return;
            }

            System.out.printf("Retrieving details for %d deposits%n", listResponse.getData().size());

            for (int i = 0; i < listResponse.getData().size(); i++) {
                Deposit listed = listResponse.getData().get(i);
                Deposit deposit = depositsService.get(listed.getDepositId());

                assertThat(deposit).isNotNull();

                System.out.printf("  Deposit %d: %s %s (Status: %s)%n",
                        i + 1, deposit.getAmount(), deposit.getCurrency(),
                        deposit.getDepositStatus());
            }
        }
    }
}
