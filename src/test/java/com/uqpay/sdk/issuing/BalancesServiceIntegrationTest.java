package com.uqpay.sdk.issuing;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.model.IssuingBalance;
import com.uqpay.sdk.issuing.model.IssuingBalanceTransaction;
import com.uqpay.sdk.issuing.model.ListIssuingBalanceTransactionsRequest;
import com.uqpay.sdk.issuing.model.ListIssuingBalanceTransactionsResponse;
import com.uqpay.sdk.issuing.model.ListIssuingBalancesRequest;
import com.uqpay.sdk.issuing.model.ListIssuingBalancesResponse;
import com.uqpay.sdk.issuing.model.RetrieveBalanceRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BalancesService Integration Tests")
class BalancesServiceIntegrationTest {

    private static BalancesService balancesService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        IssuingClient issuingClient = new IssuingClient(apiClient);
        balancesService = issuingClient.getBalances();
    }

    @Nested
    @DisplayName("Retrieve Balance")
    class RetrieveBalance {

        @Test
        @DisplayName("should retrieve issuing balance by currency")
        void shouldRetrieveBalanceByCurrency() throws UqpayException {
            RetrieveBalanceRequest request = new RetrieveBalanceRequest();
            request.setCurrency("USD");

            IssuingBalance balance = balancesService.retrieve(request);

            assertThat(balance).isNotNull();

            System.out.printf("Issuing balance: ID=%s, Currency=%s, Available=%s, Margin=%s, Frozen=%s%n",
                    balance.getBalanceId(), balance.getCurrency(),
                    balance.getAvailableBalance(), balance.getMarginBalance(), balance.getFrozenBalance());
            System.out.printf("  Status=%s, Created=%s, LastTrade=%s%n",
                    balance.getBalanceStatus(), balance.getCreateTime(), balance.getLastTradeTime());
        }
    }

    @Nested
    @DisplayName("List Balances")
    class ListBalances {

        @Test
        @DisplayName("should list issuing balances with pagination")
        void shouldListBalancesWithPagination() throws UqpayException {
            ListIssuingBalancesRequest request = new ListIssuingBalancesRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListIssuingBalancesResponse response = balancesService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d issuing balances (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                for (int i = 0; i < response.getData().size(); i++) {
                    IssuingBalance balance = response.getData().get(i);
                    System.out.printf("Balance %d: %s - Available: %s, Margin: %s, Status: %s%n",
                            i + 1, balance.getCurrency(), balance.getAvailableBalance(),
                            balance.getMarginBalance(), balance.getBalanceStatus());
                }
            }
        }
    }

    @Nested
    @DisplayName("List Balance Transactions")
    class ListBalanceTransactions {

        @Test
        @DisplayName("should list issuing balance transactions with pagination")
        void shouldListBalanceTransactionsWithPagination() throws UqpayException {
            ListIssuingBalanceTransactionsRequest request = new ListIssuingBalanceTransactionsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListIssuingBalanceTransactionsResponse response = balancesService.listTransactions(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d issuing balance transactions (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                IssuingBalanceTransaction first = response.getData().get(0);
                System.out.printf("First transaction: ID=%s, Type=%s, Amount=%s %s, Status=%s%n",
                        first.getTransactionId(), first.getTransactionType(),
                        first.getAmount(), first.getCurrency(), first.getTransactionStatus());
                System.out.printf("  BalanceID=%s, EndingBalance=%s, Description=%s%n",
                        first.getBalanceId(), first.getEndingBalance(), first.getDescription());
            }
        }
    }
}
