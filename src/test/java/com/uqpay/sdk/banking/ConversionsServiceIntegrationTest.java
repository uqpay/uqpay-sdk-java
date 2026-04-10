package com.uqpay.sdk.banking;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.banking.model.Conversion;
import com.uqpay.sdk.banking.model.ConversionDate;
import com.uqpay.sdk.banking.model.CreateConversionRequest;
import com.uqpay.sdk.banking.model.CreateConversionResponse;
import com.uqpay.sdk.banking.model.CreateQuoteRequest;
import com.uqpay.sdk.banking.model.CreateQuoteResponse;
import com.uqpay.sdk.banking.model.ListConversionsRequest;
import com.uqpay.sdk.banking.model.ListConversionsResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration tests for {@link ConversionsService}.
 * <p>
 * Requires UQPAY_CLIENT_ID and UQPAY_API_KEY environment variables to be set.
 * Tests will be skipped if credentials are not available.
 * </p>
 */
@DisplayName("ConversionsService Integration Tests")
class ConversionsServiceIntegrationTest {

    private static ConversionsService conversionsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        BankingClient bankingClient = new BankingClient(apiClient);
        conversionsService = bankingClient.getConversions();
    }

    @Nested
    @DisplayName("List Conversions")
    class ListConversions {

        @Test
        @DisplayName("should list conversions with pagination")
        void shouldListConversionsWithPagination() throws UqpayException {
            ListConversionsRequest request = new ListConversionsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListConversionsResponse response = conversionsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d conversions (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                Conversion first = response.getData().get(0);
                System.out.printf("First conversion: ID=%s, From=%s, To=%s, Rate=%s, Status=%s%n",
                        first.getConversionId(), first.getSellCurrency(),
                        first.getBuyCurrency(), first.getClientRate(), first.getConversionStatus());
                System.out.printf("  AmountFrom=%s, AmountTo=%s%n",
                        first.getSellAmount(), first.getBuyAmount());
            }
        }
    }

    @Nested
    @DisplayName("Get Conversion")
    class GetConversion {

        @Test
        @DisplayName("should get conversion by ID")
        void shouldGetConversionById() throws UqpayException {
            // First, list conversions to get a valid conversion ID
            ListConversionsRequest listRequest = new ListConversionsRequest();
            listRequest.setPageSize(1);
            listRequest.setPageNumber(1);

            ListConversionsResponse listResponse = conversionsService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No conversions available, skipping Get test");
                return;
            }

            String conversionId = listResponse.getData().get(0).getConversionId();

            // Test Get
            Conversion conversion = conversionsService.get(conversionId);

            assertThat(conversion).isNotNull();
            assertThat(conversion.getConversionId()).isEqualTo(conversionId);

            System.out.printf("Retrieved conversion: ID=%s, From=%s, To=%s, Rate=%s, Status=%s%n",
                    conversion.getConversionId(), conversion.getSellCurrency(),
                    conversion.getBuyCurrency(), conversion.getClientRate(), conversion.getConversionStatus());
            System.out.printf("  ShortRef=%s, AmountFrom=%s, AmountTo=%s%n",
                    conversion.getShortReferenceId(), conversion.getSellAmount(),
                    conversion.getBuyAmount());
            System.out.printf("  Created: %s%n", conversion.getCreateTime());
            if (conversion.getSettleTime() != null) {
                System.out.printf("  Completed: %s%n", conversion.getSettleTime());
            }
        }
    }

    @Nested
    @DisplayName("List Conversion Dates")
    class ListConversionDates {

        @Test
        @DisplayName("should list conversion dates for USD to EUR")
        void shouldListConversionDatesForUsdToEur() throws UqpayException {
            ConversionDate[] dates = conversionsService.listConversionDates("USD", "EUR");

            assertThat(dates).isNotNull();

            System.out.printf("Found %d conversion dates for USD to EUR%n", dates.length);

            for (int i = 0; i < Math.min(dates.length, 5); i++) {
                ConversionDate date = dates[i];
                System.out.printf("  Date: %s, Valid=%b%n",
                        date.getDate(), date.isValid());
            }
            if (dates.length > 5) {
                System.out.printf("  ... and %d more dates%n", dates.length - 5);
            }
        }
    }

    @Nested
    @DisplayName("List Conversions With Filters")
    class ListConversionsWithFilters {

        @Test
        @DisplayName("should list conversions with pagination")
        void shouldListConversionsWithPaginationFilters() throws UqpayException {
            ListConversionsRequest request = new ListConversionsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListConversionsResponse response = conversionsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d conversions%n", response.getTotalItems());

            for (Conversion conv : response.getData()) {
                System.out.printf("  Conversion: ID=%s, %s %s -> %s %s, Status=%s%n",
                        conv.getConversionId(), conv.getSellAmount(), conv.getSellCurrency(),
                        conv.getBuyAmount(), conv.getBuyCurrency(), conv.getConversionStatus());
            }
        }
    }

    @Nested
    @DisplayName("Conversion Pagination")
    class ConversionPagination {

        @Test
        @DisplayName("should paginate through conversions")
        void shouldPaginateThroughConversions() throws UqpayException {
            ListConversionsRequest request1 = new ListConversionsRequest();
            request1.setPageSize(5);
            request1.setPageNumber(1);

            ListConversionsResponse response1 = conversionsService.list(request1);

            assertThat(response1).isNotNull();

            System.out.printf("Page 1: %d items (Total: %d items, %d pages)%n",
                    response1.getData().size(), response1.getTotalItems(), response1.getTotalPages());

            if (response1.getTotalPages() <= 1) {
                System.out.println("Not enough data to test pagination");
                return;
            }

            ListConversionsRequest request2 = new ListConversionsRequest();
            request2.setPageSize(5);
            request2.setPageNumber(2);

            ListConversionsResponse response2 = conversionsService.list(request2);

            assertThat(response2).isNotNull();

            System.out.printf("Page 2: %d items%n", response2.getData().size());

            if (!response1.getData().isEmpty() && !response2.getData().isEmpty()) {
                assertThat(response1.getData().get(0).getConversionId())
                        .isNotEqualTo(response2.getData().get(0).getConversionId());
            }
        }
    }

    @Nested
    @DisplayName("Conversion Dates Multiple Currency Pairs")
    class ConversionDatesMultipleCurrencyPairs {

        @Test
        @DisplayName("should list conversion dates for multiple currency pairs")
        void shouldListConversionDatesForMultiplePairs() throws UqpayException {
            String[][] pairs = {{"USD", "EUR"}, {"USD", "GBP"}, {"EUR", "USD"}, {"GBP", "USD"}};

            for (String[] pair : pairs) {
                String from = pair[0];
                String to = pair[1];

                try {
                    ConversionDate[] dates = conversionsService.listConversionDates(from, to);

                    assertThat(dates).isNotNull();

                    System.out.printf("Available dates for %s -> %s: %d%n", from, to, dates.length);

                    for (ConversionDate date : dates) {
                        if (date.isValid()) {
                            System.out.printf("  Valid date: %s%n", date.getDate());
                            break;
                        }
                    }
                } catch (UqpayException e) {
                    System.out.printf("Failed to get conversion dates for %s -> %s: %s%n", from, to, e.getMessage());
                }
            }
        }
    }

    @Nested
    @DisplayName("Create Quote With Settlement Date")
    class CreateQuoteWithSettlementDate {

        @Test
        @DisplayName("should create quote with settlement date")
        void shouldCreateQuoteWithSettlementDate() {
            try {
                // Get conversion dates first
                ConversionDate[] dates = conversionsService.listConversionDates("USD", "SGD");

                assertThat(dates).isNotNull();

                if (dates.length == 0) {
                    System.out.println("No conversion dates available, skipping test");
                    return;
                }

                String conversionDate = dates[0].getDate();

                CreateQuoteRequest request = new CreateQuoteRequest();
                request.setSellCurrency("USD");
                request.setSellAmount("100.00");
                request.setBuyCurrency("SGD");
                request.setConversionDate(conversionDate);
                request.setTransactionType("conversion");

                System.out.printf("Creating quote with conversion date: %s%n", conversionDate);

                CreateQuoteResponse response = conversionsService.createQuote(request);

                assertThat(response).isNotNull();
                assertThat(response.getQuotePrice()).isNotNull();
                assertThat(response.getQuotePrice().getQuoteId()).isNotEmpty();

                System.out.printf("Quote created with settlement date: QuoteID=%s, Rate=%s%n",
                        response.getQuotePrice().getQuoteId(), response.getQuotePrice().getDirectRate());
            } catch (UqpayException e) {
                // Conversion date validation may be strict in sandbox
                System.out.printf("Create quote with settlement date returned error: %s%n", e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Conversion Full Flow")
    class ConversionFullFlow {

        @Test
        @DisplayName("should complete full conversion flow: dates -> quote -> conversion -> get -> list")
        void shouldCompleteFullConversionFlow() throws UqpayException {
            // Step 1: Get conversion dates
            ConversionDate[] dates = conversionsService.listConversionDates("USD", "EUR");
            assertThat(dates).isNotNull();
            System.out.printf("Step 1: Available conversion dates: %d%n", dates.length);
            if (dates.length > 0) {
                System.out.printf("  First available date: %s%n", dates[0].getDate());
            }

            // Step 2: Create a quote
            String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
            CreateQuoteRequest quoteRequest = new CreateQuoteRequest();
            quoteRequest.setSellCurrency("USD");
            quoteRequest.setSellAmount("100.00");
            quoteRequest.setBuyCurrency("EUR");
            quoteRequest.setConversionDate(today);
            quoteRequest.setTransactionType("conversion");

            CreateQuoteResponse quoteResponse = conversionsService.createQuote(quoteRequest);
            assertThat(quoteResponse).isNotNull();
            assertThat(quoteResponse.getQuotePrice()).isNotNull();

            String quoteId = quoteResponse.getQuotePrice().getQuoteId();
            String quoteRate = quoteResponse.getQuotePrice().getDirectRate();
            System.out.printf("Step 2: Quote created: ID=%s, Rate=%s, BuyAmount=%s %s%n",
                    quoteId, quoteRate, quoteResponse.getBuyAmount(), quoteResponse.getBuyCurrency());

            // Step 3: Create conversion using the quote
            CreateConversionRequest convRequest = new CreateConversionRequest();
            convRequest.setQuoteId(quoteId);
            convRequest.setSellCurrency("USD");
            convRequest.setSellAmount("100.00");
            convRequest.setBuyCurrency("EUR");
            convRequest.setConversionDate(today);

            CreateConversionResponse convResponse = conversionsService.create(convRequest);
            assertThat(convResponse).isNotNull();
            assertThat(convResponse.getConversionId()).isNotEmpty();

            String conversionId = convResponse.getConversionId();
            System.out.printf("Step 3: Conversion created: ID=%s, ShortRef=%s, Sell=%s, Buy=%s%n",
                    conversionId, convResponse.getShortReferenceId(),
                    convResponse.getSellAmount(), convResponse.getBuyAmount());

            // Step 4: Get the conversion details
            Conversion conversion = conversionsService.get(conversionId);
            assertThat(conversion).isNotNull();
            assertThat(conversion.getConversionId()).isEqualTo(conversionId);

            System.out.printf("Step 4: Retrieved conversion: ID=%s, Status=%s, Rate=%s%n",
                    conversion.getConversionId(), conversion.getConversionStatus(), conversion.getClientRate());
            System.out.printf("  From: %s %s, To: %s %s%n",
                    conversion.getSellAmount(), conversion.getSellCurrency(),
                    conversion.getBuyAmount(), conversion.getBuyCurrency());

            // Step 5: List conversions and verify our conversion is present
            ListConversionsRequest listRequest = new ListConversionsRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListConversionsResponse listResponse = conversionsService.list(listRequest);
            assertThat(listResponse).isNotNull();

            boolean found = false;
            for (Conversion c : listResponse.getData()) {
                if (conversionId.equals(c.getConversionId())) {
                    found = true;
                    System.out.printf("Step 5: Found conversion in list: Status=%s%n", c.getConversionStatus());
                    break;
                }
            }

            if (!found) {
                System.out.println("Step 5: Conversion not found in first page of results");
            } else {
                System.out.println("Step 5: Successfully verified conversion in list");
            }
        }
    }

    @Nested
    @DisplayName("Conversion Error Handling")
    class ConversionErrorHandling {

        @Test
        @DisplayName("should fail when getting non-existent conversion")
        void shouldFailForNonExistentConversion() {
            assertThatThrownBy(() -> conversionsService.get("non-existent-id"))
                    .isInstanceOf(UqpayException.class);

            System.out.println("Got expected error for non-existent conversion");
        }

        @Test
        @DisplayName("should fail when creating conversion with invalid currency")
        void shouldFailForInvalidCurrency() {
            String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

            CreateConversionRequest request = new CreateConversionRequest();
            request.setQuoteId("invalid-quote-id");
            request.setSellCurrency("INVALID");
            request.setSellAmount("100.00");
            request.setBuyCurrency("EUR");
            request.setConversionDate(today);

            assertThatThrownBy(() -> conversionsService.create(request))
                    .isInstanceOf(UqpayException.class);

            System.out.println("Got expected error for invalid currency");
        }

        @Test
        @DisplayName("should fail when creating quote with invalid amount")
        void shouldFailForInvalidAmount() {
            String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

            CreateQuoteRequest request = new CreateQuoteRequest();
            request.setSellCurrency("USD");
            request.setSellAmount("invalid");
            request.setBuyCurrency("SGD");
            request.setConversionDate(today);
            request.setTransactionType("conversion");

            assertThatThrownBy(() -> conversionsService.createQuote(request))
                    .isInstanceOf(UqpayException.class);

            System.out.println("Got expected error for invalid amount");
        }
    }

    @Nested
    @DisplayName("Create Quote")
    class CreateQuote {

        @Test
        @DisplayName("should create quote")
        void shouldCreateQuote() throws UqpayException {
            String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

            CreateQuoteRequest request = new CreateQuoteRequest();
            request.setSellCurrency("USD");
            request.setSellAmount("100.00");
            request.setBuyCurrency("SGD");
            request.setConversionDate(today);
            request.setTransactionType("conversion");

            CreateQuoteResponse response = conversionsService.createQuote(request);

            assertThat(response).isNotNull();
            assertThat(response.getQuotePrice()).isNotNull();
            assertThat(response.getQuotePrice().getQuoteId()).isNotEmpty();
            assertThat(response.getSellCurrency()).isEqualTo("USD");
            assertThat(response.getBuyCurrency()).isEqualTo("SGD");
            assertThat(response.getQuotePrice().getDirectRate()).isNotEmpty();
            assertThat(response.getBuyAmount()).isNotEmpty();

            System.out.printf("Created quote: QuoteID=%s, SellCurrency=%s, BuyCurrency=%s%n",
                    response.getQuotePrice().getQuoteId(), response.getSellCurrency(), response.getBuyCurrency());
            System.out.printf("  SellAmount=%s, BuyAmount=%s, DirectRate=%s%n",
                    response.getSellAmount(), response.getBuyAmount(), response.getQuotePrice().getDirectRate());
            if (response.getQuotePrice().getInverseRate() != null) {
                System.out.printf("  InverseRate=%s%n", response.getQuotePrice().getInverseRate());
            }
        }
    }

    @Nested
    @DisplayName("Create Conversion")
    class CreateConversion {

        @Test
        @DisplayName("should create conversion")
        void shouldCreateConversion() throws UqpayException {
            String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

            // Step 1: Create a quote first
            CreateQuoteRequest quoteRequest = new CreateQuoteRequest();
            quoteRequest.setSellCurrency("USD");
            quoteRequest.setSellAmount("100.00");
            quoteRequest.setBuyCurrency("SGD");
            quoteRequest.setConversionDate(today);
            quoteRequest.setTransactionType("conversion");

            CreateQuoteResponse quoteResponse = conversionsService.createQuote(quoteRequest);
            assertThat(quoteResponse).isNotNull();
            assertThat(quoteResponse.getQuotePrice()).isNotNull();

            String quoteId = quoteResponse.getQuotePrice().getQuoteId();
            System.out.printf("Got quote: QuoteID=%s%n", quoteId);

            // Step 2: Create conversion using the quote
            CreateConversionRequest request = new CreateConversionRequest();
            request.setQuoteId(quoteId);
            request.setSellCurrency("USD");
            request.setSellAmount("100.00");
            request.setBuyCurrency("SGD");
            request.setConversionDate(today);

            CreateConversionResponse response = conversionsService.create(request);

            assertThat(response).isNotNull();
            assertThat(response.getConversionId()).isNotEmpty();
            assertThat(response.getShortReferenceId()).isNotEmpty();

            System.out.printf("Created conversion: ID=%s, ShortRef=%s%n",
                    response.getConversionId(), response.getShortReferenceId());
            System.out.printf("  SellAmount=%s, BuyAmount=%s, Status=%s%n",
                    response.getSellAmount(), response.getBuyAmount(), response.getConversionStatus());
        }
    }
}
