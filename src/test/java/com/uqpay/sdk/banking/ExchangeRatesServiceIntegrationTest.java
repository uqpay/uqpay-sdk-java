package com.uqpay.sdk.banking;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.banking.model.ListRatesRequest;
import com.uqpay.sdk.banking.model.ListRatesResponse;
import com.uqpay.sdk.banking.model.RateItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link ExchangeRatesService}.
 * <p>
 * Requires UQPAY_CLIENT_ID and UQPAY_API_KEY environment variables to be set.
 * Tests will be skipped if credentials are not available.
 * </p>
 */
@DisplayName("ExchangeRatesService Integration Tests")
class ExchangeRatesServiceIntegrationTest {

    private static ExchangeRatesService exchangeRatesService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        BankingClient bankingClient = new BankingClient(apiClient);
        exchangeRatesService = bankingClient.getExchangeRates();
    }

    @Nested
    @DisplayName("List Rates")
    class ListRates {

        @Test
        @DisplayName("should list all exchange rates")
        void shouldListAllExchangeRates() throws UqpayException {
            ListRatesRequest request = new ListRatesRequest();

            ListRatesResponse response = exchangeRatesService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getRates()).isNotNull();

            System.out.printf("Found %d exchange rates%n", response.getRates().size());

            if (!response.getRates().isEmpty()) {
                RateItem first = response.getRates().get(0);
                System.out.printf("First rate: Pair=%s, BuyPrice=%s, SellPrice=%s%n",
                        first.getCurrencyPair(), first.getBuyPrice(),
                        first.getSellPrice());
            }
        }

        @Test
        @DisplayName("should list exchange rates with currency pairs filter")
        void shouldListExchangeRatesWithCurrencyPairsFilter() throws UqpayException {
            ListRatesRequest request = new ListRatesRequest();
            request.setCurrencyPairs(Arrays.asList("USDEUR", "USDGBP"));

            ListRatesResponse response = exchangeRatesService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getRates()).isNotNull();

            System.out.printf("Found %d exchange rates for filtered pairs%n", response.getRates().size());

            for (RateItem rate : response.getRates()) {
                System.out.printf("  Rate: Pair=%s, BuyPrice=%s, SellPrice=%s%n",
                        rate.getCurrencyPair(), rate.getBuyPrice(),
                        rate.getSellPrice());
            }
        }
    }
}
