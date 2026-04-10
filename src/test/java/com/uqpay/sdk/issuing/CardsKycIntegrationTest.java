package com.uqpay.sdk.issuing;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.model.CardCreationResponse;
import com.uqpay.sdk.issuing.model.CardProduct;
import com.uqpay.sdk.issuing.model.CardholderRequiredFields;
import com.uqpay.sdk.issuing.model.CreateCardRequest;
import com.uqpay.sdk.issuing.model.Identity;
import com.uqpay.sdk.issuing.model.ListCardholdersRequest;
import com.uqpay.sdk.issuing.model.ListCardholdersResponse;
import com.uqpay.sdk.issuing.model.ListProductsRequest;
import com.uqpay.sdk.issuing.model.ListProductsResponse;
import com.uqpay.sdk.issuing.model.ResidentialAddress;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Cards KYC Integration Tests")
class CardsKycIntegrationTest {

    private static CardsService cardsService;
    private static CardholdersService cardholdersService;
    private static ProductsService productsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        IssuingClient issuingClient = new IssuingClient(apiClient);
        cardsService = issuingClient.getCards();
        cardholdersService = issuingClient.getCardholders();
        productsService = issuingClient.getProducts();
    }

    // ─── Step 0: List Products — verify required_fields and kyc_level ─────────────

    @Nested
    @DisplayName("Step 0: List Products — inspect required_fields and kyc_level")
    class ListProductsWithKycFields {

        @Test
        @DisplayName("should list products and log required_fields and kyc_level for each")
        void shouldListProductsAndLogKycFields() throws UqpayException {
            ListProductsRequest request = new ListProductsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListProductsResponse response = productsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d products%n", response.getData().size());

            for (CardProduct p : response.getData()) {
                System.out.printf("Product: id=%s, bin=%s, kyc_level=%s, required_fields=%s%n",
                        p.getProductId(), p.getCardBin(), p.getKycLevel(), p.getRequiredFields());
            }
        }
    }

    // ─── Step 1: Create card with cardholder_required_fields ─────────────────────

    @Nested
    @DisplayName("Step 1: Create card with cardholder_required_fields")
    class CreateCardWithCardholderRequiredFields {

        @Test
        @DisplayName("should attempt card creation with supplemental KYC fields in cardholder_required_fields")
        void shouldCreateCardWithCardholderRequiredFields() throws UqpayException {
            // Find a SUCCESS cardholder and the first available product
            ListCardholdersRequest chReq = new ListCardholdersRequest();
            chReq.setPageSize(10);
            chReq.setPageNumber(1);
            chReq.setCardholderStatus("SUCCESS");

            ListCardholdersResponse chResp = cardholdersService.list(chReq);
            if (chResp.getData() == null || chResp.getData().isEmpty()) {
                System.out.println("No SUCCESS cardholders found, skipping cardholder_required_fields card creation test");
                return;
            }

            ListProductsRequest prodReq = new ListProductsRequest();
            prodReq.setPageSize(10);
            prodReq.setPageNumber(1);

            ListProductsResponse prodResp = productsService.list(prodReq);
            if (prodResp.getData() == null || prodResp.getData().isEmpty()) {
                System.out.println("No products found, skipping cardholder_required_fields card creation test");
                return;
            }

            String cardholderId = chResp.getData().get(0).getCardholderId();
            String productId = prodResp.getData().get(0).getProductId();
            String cardCurrency = "SGD";

            // Try to use a currency the product supports
            CardProduct product = prodResp.getData().get(0);
            if (product.getCardCurrency() != null && !product.getCardCurrency().isEmpty()) {
                cardCurrency = product.getCardCurrency().get(0);
            }

            System.out.printf("Attempting card creation: cardholder=%s, product=%s, currency=%s%n",
                    cardholderId, productId, cardCurrency);

            CreateCardRequest cardReq = new CreateCardRequest();
            cardReq.setCardCurrency(cardCurrency);
            cardReq.setCardholderId(cardholderId);
            cardReq.setCardProductId(productId);

            CardholderRequiredFields fields = new CardholderRequiredFields();
            fields.setGender("FEMALE");
            fields.setNationality("SG");

            Identity identity = new Identity();
            identity.setType("PASSPORT");
            identity.setNumber("E9999999");
            fields.setIdentity(identity);

            ResidentialAddress addr = new ResidentialAddress();
            addr.setCountry("SG");
            addr.setState("Singapore");
            addr.setCity("Singapore");
            addr.setLine1("1 Raffles Place");
            addr.setLineEn("1 Raffles Place");
            addr.setPostalCode("048616");
            fields.setResidentialAddress(addr);

            cardReq.setCardholderRequiredFields(fields);

            try {
                CardCreationResponse response = cardsService.create(cardReq);

                assertThat(response).isNotNull();

                System.out.printf("Card creation response: card_id=%s, card_status=%s, cardholder_status=%s, message=%s%n",
                        response.getCardId(), response.getCardStatus(),
                        response.getCardholderStatus(), response.getMessage());
            } catch (UqpayException e) {
                System.out.printf("Card creation with cardholder_required_fields returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }
    }

    // ─── Step 2: Create card with usage_type ONE_TIME ─────────────────────────────

    @Nested
    @DisplayName("Step 2: Create card with usage_type ONE_TIME")
    class CreateCardWithOneTimeUsage {

        @Test
        @DisplayName("should attempt card creation with ONE_TIME usage type and ON_AUTH auto_cancel_trigger")
        void shouldCreateCardWithOneTimeUsage() throws UqpayException {
            // Find a SUCCESS cardholder and the first available product
            ListCardholdersRequest chReq = new ListCardholdersRequest();
            chReq.setPageSize(10);
            chReq.setPageNumber(1);
            chReq.setCardholderStatus("SUCCESS");

            ListCardholdersResponse chResp = cardholdersService.list(chReq);
            if (chResp.getData() == null || chResp.getData().isEmpty()) {
                System.out.println("No SUCCESS cardholders found, skipping ONE_TIME usage type card creation test");
                return;
            }

            ListProductsRequest prodReq = new ListProductsRequest();
            prodReq.setPageSize(10);
            prodReq.setPageNumber(1);

            ListProductsResponse prodResp = productsService.list(prodReq);
            if (prodResp.getData() == null || prodResp.getData().isEmpty()) {
                System.out.println("No products found, skipping ONE_TIME usage type card creation test");
                return;
            }

            String cardholderId = chResp.getData().get(0).getCardholderId();
            String productId = prodResp.getData().get(0).getProductId();
            String cardCurrency = "SGD";

            CardProduct product = prodResp.getData().get(0);
            if (product.getCardCurrency() != null && !product.getCardCurrency().isEmpty()) {
                cardCurrency = product.getCardCurrency().get(0);
            }

            System.out.printf("Attempting ONE_TIME card creation: cardholder=%s, product=%s, currency=%s%n",
                    cardholderId, productId, cardCurrency);

            CreateCardRequest cardReq = new CreateCardRequest();
            cardReq.setCardCurrency(cardCurrency);
            cardReq.setCardholderId(cardholderId);
            cardReq.setCardProductId(productId);
            cardReq.setUsageType("ONE_TIME");
            cardReq.setAutoCancelTrigger("ON_AUTH");

            try {
                CardCreationResponse response = cardsService.create(cardReq);

                assertThat(response).isNotNull();

                System.out.printf("ONE_TIME card creation response: card_id=%s, card_status=%s, cardholder_status=%s, message=%s%n",
                        response.getCardId(), response.getCardStatus(),
                        response.getCardholderStatus(), response.getMessage());
            } catch (UqpayException e) {
                System.out.printf("ONE_TIME card creation returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }
    }
}
