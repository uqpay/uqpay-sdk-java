package com.uqpay.sdk.banking;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.banking.model.Balance;
import com.uqpay.sdk.banking.model.BalanceTransaction;
import com.uqpay.sdk.banking.model.ListBalancesRequest;
import com.uqpay.sdk.banking.model.ListBalancesResponse;
import com.uqpay.sdk.banking.model.ListBalanceTransactionsRequest;
import com.uqpay.sdk.banking.model.ListBalanceTransactionsResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link BalancesService}.
 * <p>
 * Requires UQPAY_CLIENT_ID and UQPAY_API_KEY environment variables to be set.
 * Tests will be skipped if credentials are not available.
 * </p>
 */
@DisplayName("BalancesService Integration Tests")
class BalancesServiceIntegrationTest {

    private static BalancesService balancesService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        BankingClient bankingClient = new BankingClient(apiClient);
        balancesService = bankingClient.getBalances();
    }

    @Nested
    @DisplayName("List Balances")
    class ListBalances {

        @Test
        @DisplayName("should list balances with pagination")
        void shouldListBalancesWithPagination() throws UqpayException {
            ListBalancesRequest request = new ListBalancesRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListBalancesResponse response = balancesService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d balances (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                Balance first = response.getData().get(0);
                System.out.printf("First balance: ID=%s, Currency=%s, Available=%s, Prepaid=%s%n",
                        first.getBalanceId(), first.getCurrency(),
                        first.getAvailableBalance(), first.getPrepaidBalance());
            }
        }
    }

    @Nested
    @DisplayName("Get Balance")
    class GetBalance {

        @Test
        @DisplayName("should get balance by currency")
        void shouldGetBalanceByCurrency() throws UqpayException {
            // First, list balances to get a valid currency
            ListBalancesRequest listRequest = new ListBalancesRequest();
            listRequest.setPageSize(1);
            listRequest.setPageNumber(1);

            ListBalancesResponse listResponse = balancesService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No balances available, skipping Get test");
                return;
            }

            String currency = listResponse.getData().get(0).getCurrency();

            // Test Get
            Balance balance = balancesService.get(currency);

            assertThat(balance).isNotNull();
            assertThat(balance.getCurrency()).isEqualTo(currency);

            System.out.printf("Retrieved balance: ID=%s, Currency=%s, Available=%s, Prepaid=%s, Margin=%s, Frozen=%s, Status=%s%n",
                    balance.getBalanceId(), balance.getCurrency(),
                    balance.getAvailableBalance(), balance.getPrepaidBalance(),
                    balance.getMarginBalance(), balance.getFrozenBalance(),
                    balance.getBalanceStatus());
        }
    }

    @Nested
    @DisplayName("List Balance Transactions")
    class ListBalanceTransactions {

        @Test
        @DisplayName("should list balance transactions with pagination")
        void shouldListBalanceTransactionsWithPagination() throws UqpayException {
            ListBalanceTransactionsRequest request = new ListBalanceTransactionsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListBalanceTransactionsResponse response = balancesService.listTransactions(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d balance transactions (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                BalanceTransaction first = response.getData().get(0);
                System.out.printf("First transaction: ID=%s, Currency=%s, Amount=%s, Type=%s, Status=%s%n",
                        first.getTransactionId(), first.getCurrency(),
                        first.getAmount(), first.getTransactionType(),
                        first.getTransactionStatus());
                System.out.printf("  CreditDebit=%s, ReferenceId=%s%n",
                        first.getCreditDebitType(), first.getReferenceId());
            }
        }

        @Test
        @DisplayName("should list balance transactions with filters")
        void shouldListBalanceTransactionsWithFilters() throws UqpayException {
            ListBalanceTransactionsRequest request = new ListBalanceTransactionsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);
            request.setCurrency("USD");
            request.setTransactionType("DEPOSIT");
            request.setTransactionStatus("COMPLETED");

            ListBalanceTransactionsResponse response = balancesService.listTransactions(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d completed USD deposit transactions (total: %d)%n",
                    response.getData().size(), response.getTotalItems());

            for (BalanceTransaction txn : response.getData()) {
                System.out.printf("  Transaction: %s %s, Type=%s, Status=%s%n",
                        txn.getAmount(), txn.getCurrency(), txn.getTransactionType(),
                        txn.getTransactionStatus());
            }
        }

        @Test
        @DisplayName("should list balance transactions by type")
        void shouldListBalanceTransactionsByType() throws UqpayException {
            String[] transactionTypes = {"PAYIN", "DEPOSIT", "PAYOUT", "TRANSFER", "CONVERSION", "FEE"};

            for (String txnType : transactionTypes) {
                ListBalanceTransactionsRequest request = new ListBalanceTransactionsRequest();
                request.setPageSize(10);
                request.setPageNumber(1);
                request.setTransactionType(txnType);

                ListBalanceTransactionsResponse response = balancesService.listTransactions(request);

                assertThat(response).isNotNull();

                System.out.printf("%s transactions: %d found%n", txnType, response.getTotalItems());
            }
        }
    }
}
