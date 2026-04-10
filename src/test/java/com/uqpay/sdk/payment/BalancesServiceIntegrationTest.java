package com.uqpay.sdk.payment;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.payment.model.ListPaymentBalancesRequest;
import com.uqpay.sdk.payment.model.ListPaymentBalancesResponse;
import com.uqpay.sdk.payment.model.PaymentBalance;
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
        PaymentClient paymentClient = new PaymentClient(apiClient);
        balancesService = paymentClient.getBalances();
    }

    @Nested
    @DisplayName("List Balances")
    class ListBalances {

        @Test
        @DisplayName("should list balances with pagination")
        void shouldListAllBalances() throws UqpayException {
            ListPaymentBalancesRequest request = new ListPaymentBalancesRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListPaymentBalancesResponse response = balancesService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d payment balances (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            for (PaymentBalance balance : response.getData()) {
                System.out.printf("  Balance: ID=%s, Currency=%s, Available=%s, Payable=%s%n",
                        balance.getBalanceId(), balance.getCurrency(),
                        balance.getAvailableBalance(), balance.getPayableBalance());
            }
        }
    }

    @Nested
    @DisplayName("Get Balance")
    class GetBalance {

        @Test
        @DisplayName("should get balance by currency")
        void shouldGetBalanceByCurrency() throws UqpayException {
            // First list to find an available currency
            ListPaymentBalancesRequest listRequest = new ListPaymentBalancesRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListPaymentBalancesResponse listResponse = balancesService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No balances available, skipping Get test");
                return;
            }

            String currency = listResponse.getData().get(0).getCurrency();

            PaymentBalance balance = balancesService.get(currency);

            assertThat(balance).isNotNull();
            assertThat(balance.getCurrency()).isEqualTo(currency);

            System.out.printf("Retrieved balance: Currency=%s, Available=%s, Payable=%s, Pending=%s%n",
                    balance.getCurrency(), balance.getAvailableBalance(),
                    balance.getPayableBalance(), balance.getPendingBalance());
        }
    }
}
