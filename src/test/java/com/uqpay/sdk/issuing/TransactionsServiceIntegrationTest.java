package com.uqpay.sdk.issuing;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.model.ListTransactionsRequest;
import com.uqpay.sdk.issuing.model.ListTransactionsResponse;
import com.uqpay.sdk.issuing.model.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TransactionsService Integration Tests")
class TransactionsServiceIntegrationTest {

    private static TransactionsService transactionsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        IssuingClient issuingClient = new IssuingClient(apiClient);
        transactionsService = issuingClient.getTransactions();
    }

    @Nested
    @DisplayName("List Transactions")
    class ListTransactions {

        @Test
        @DisplayName("should list transactions with pagination")
        void shouldListTransactionsWithPagination() throws UqpayException {
            ListTransactionsRequest request = new ListTransactionsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListTransactionsResponse response = transactionsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d transactions (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                Transaction first = response.getData().get(0);
                System.out.printf("First transaction: ID=%s, CardID=%s, Type=%s, Amount=%s %s, Status=%s%n",
                        first.getTransactionId(), first.getCardId(),
                        first.getTransactionType(), first.getTransactionAmount(),
                        first.getTransactionCurrency(), first.getTransactionStatus());
                System.out.printf("  Billing: %s %s, Merchant: %s, Time: %s%n",
                        first.getBillingAmount(), first.getBillingCurrency(),
                        first.getMerchantData(), first.getTransactionTime());
            }
        }
    }

    @Nested
    @DisplayName("Get Transaction")
    class GetTransaction {

        @Test
        @DisplayName("should get transaction by ID")
        void shouldGetTransactionById() throws UqpayException {
            // First, list transactions to get a valid ID
            ListTransactionsRequest listRequest = new ListTransactionsRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListTransactionsResponse listResponse = transactionsService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No transactions available, skipping Get test");
                return;
            }

            String transactionId = listResponse.getData().get(0).getTransactionId();

            Transaction transaction = transactionsService.get(transactionId);

            assertThat(transaction).isNotNull();
            assertThat(transaction.getTransactionId()).isEqualTo(transactionId);

            System.out.printf("Retrieved transaction: ID=%s, Type=%s, Amount=%s %s, Status=%s%n",
                    transaction.getTransactionId(), transaction.getTransactionType(),
                    transaction.getTransactionAmount(), transaction.getTransactionCurrency(),
                    transaction.getTransactionStatus());
        }
    }
}
