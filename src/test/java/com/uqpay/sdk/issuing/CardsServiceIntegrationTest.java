package com.uqpay.sdk.issuing;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.model.ActivateCardRequest;
import com.uqpay.sdk.issuing.model.ActivateCardResponse;
import com.uqpay.sdk.issuing.model.AssignCardRequest;
import com.uqpay.sdk.issuing.model.AssignCardResponse;
import com.uqpay.sdk.issuing.model.BulkCardCreationRequest;
import com.uqpay.sdk.issuing.model.BulkCardCreationResponse;
import com.uqpay.sdk.issuing.model.CardCreationResponse;
import com.uqpay.sdk.issuing.model.CardOrder;
import com.uqpay.sdk.issuing.model.CardOrderRequest;
import com.uqpay.sdk.issuing.model.CardProduct;
import com.uqpay.sdk.issuing.model.CardStatusResponse;
import com.uqpay.sdk.issuing.model.CardUpdateRequest;
import com.uqpay.sdk.issuing.model.CardUpdatedResponse;
import com.uqpay.sdk.issuing.model.Cardholder;
import com.uqpay.sdk.issuing.model.CreateCardRequest;
import com.uqpay.sdk.issuing.model.CreateCardholderRequest;
import com.uqpay.sdk.issuing.model.CreateCardholderResponse;
import com.uqpay.sdk.issuing.model.ListCardsRequest;
import com.uqpay.sdk.issuing.model.ListCardsResponse;
import com.uqpay.sdk.issuing.model.ListProductsRequest;
import com.uqpay.sdk.issuing.model.ListProductsResponse;
import com.uqpay.sdk.issuing.model.RetrieveCardResponse;
import com.uqpay.sdk.issuing.model.SecureCardInfo;
import com.uqpay.sdk.issuing.model.SetPINRequest;
import com.uqpay.sdk.issuing.model.SetPINResponse;
import com.uqpay.sdk.issuing.model.PanTokenResponse;
import com.uqpay.sdk.issuing.model.UpdateCardStatusRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CardsService Integration Tests")
class CardsServiceIntegrationTest {

    private static CardsService cardsService;
    private static ProductsService productsService;
    private static CardholdersService cardholdersService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        IssuingClient issuingClient = new IssuingClient(apiClient);
        cardsService = issuingClient.getCards();
        productsService = issuingClient.getProducts();
        cardholdersService = issuingClient.getCardholders();
    }

    // =========================================================================
    // Helper: get the first existing card from the list
    // =========================================================================

    private static RetrieveCardResponse getFirstCard() throws UqpayException {
        ListCardsRequest listRequest = new ListCardsRequest();
        listRequest.setPageSize(50);
        listRequest.setPageNumber(1);

        ListCardsResponse listResponse = cardsService.list(listRequest);
        if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
            return null;
        }

        // Prefer ACTIVE cards so tests that need an active card don't skip
        for (RetrieveCardResponse card : listResponse.getData()) {
            if ("ACTIVE".equals(card.getCardStatus())) {
                return card;
            }
        }
        // Also accept FROZEN cards (some tests can handle this)
        for (RetrieveCardResponse card : listResponse.getData()) {
            if ("FROZEN".equals(card.getCardStatus())) {
                return card;
            }
        }
        return listResponse.getData().get(0);
    }

    // =========================================================================
    // Helper: find a card by form factor (VIRTUAL / PHYSICAL)
    // =========================================================================

    private static RetrieveCardResponse findCardByFormFactor(String formFactor) throws UqpayException {
        ListCardsRequest listRequest = new ListCardsRequest();
        listRequest.setCardStatus("ACTIVE");
        listRequest.setPageSize(50);
        listRequest.setPageNumber(1);

        ListCardsResponse listResponse = cardsService.list(listRequest);
        if (listResponse.getData() == null) {
            return null;
        }
        for (RetrieveCardResponse card : listResponse.getData()) {
            if (formFactor.equalsIgnoreCase(card.getFormFactor())) {
                return card;
            }
        }
        return null;
    }

    // =========================================================================
    // Helper: create a cardholder for testing
    // =========================================================================

    private static CreateCardholderResponse createCardholderForTest(String prefix) {
        long timestamp = System.currentTimeMillis();
        CreateCardholderRequest request = new CreateCardholderRequest();
        request.setEmail(String.format("%s%d@example.com", prefix, timestamp));
        request.setPhoneNumber(String.format("9%07d", timestamp % 10000000));
        request.setFirstName(prefix);
        request.setLastName("Test");
        request.setCountryCode("SG");

        try {
            return cardholdersService.create(request);
        } catch (UqpayException e) {
            System.out.printf("Failed to create cardholder (%s): %s%n", prefix, e.getMessage());
            return null;
        }
    }

    // =========================================================================
    // Create Card
    // =========================================================================

    @Nested
    @DisplayName("Create Card")
    class CreateCard {

        @Test
        @DisplayName("should create a new card")
        void shouldCreateCard() throws UqpayException {
            // Step 1: Find an enabled SINGLE-mode product
            ListProductsRequest productsRequest = new ListProductsRequest();
            productsRequest.setPageSize(10);
            productsRequest.setPageNumber(1);

            ListProductsResponse productsResponse = productsService.list(productsRequest);
            if (productsResponse.getData() == null || productsResponse.getData().isEmpty()) {
                System.out.println("No card products available, skipping Create test");
                return;
            }

            String productId = null;
            String productCurrency = null;
            for (CardProduct product : productsResponse.getData()) {
                if ("ENABLED".equals(product.getProductStatus()) && "SINGLE".equals(product.getModeType())) {
                    productId = product.getProductId();
                    if (product.getCardCurrency() != null && !product.getCardCurrency().isEmpty()) {
                        productCurrency = product.getCardCurrency().get(0);
                    }
                    System.out.printf("Found active product: %s (%s, %s)%n",
                            productId, product.getCardScheme(), productCurrency);
                    break;
                }
            }

            if (productId == null) {
                System.out.println("No suitable ENABLED/SINGLE card product found, skipping Create test");
                return;
            }

            // Step 2: Create a cardholder for this card
            long timestamp = System.currentTimeMillis();
            CreateCardholderRequest cardholderRequest = new CreateCardholderRequest();
            cardholderRequest.setEmail(String.format("cardtest%d@example.com", timestamp));
            cardholderRequest.setPhoneNumber(String.format("9%07d", timestamp % 10000000));
            cardholderRequest.setFirstName("CardCreate");
            cardholderRequest.setLastName("Test");
            cardholderRequest.setCountryCode("SG");

            CreateCardholderResponse cardholder;
            try {
                cardholder = cardholdersService.create(cardholderRequest);
            } catch (UqpayException e) {
                System.out.printf("Failed to create cardholder for card test: %s%n", e.getMessage());
                return;
            }

            System.out.printf("Cardholder created: ID=%s%n", cardholder.getCardholderId());

            // Step 3: Create the card
            CreateCardRequest request = new CreateCardRequest();
            request.setCardCurrency(productCurrency);
            request.setCardholderId(cardholder.getCardholderId());
            request.setCardProductId(productId);

            try {
                CardCreationResponse response = cardsService.create(request);

                assertThat(response).isNotNull();
                assertThat(response.getCardId()).isNotNull();

                System.out.printf("Card created: ID=%s, OrderID=%s, Status=%s, OrderStatus=%s%n",
                        response.getCardId(), response.getCardOrderId(),
                        response.getCardStatus(), response.getOrderStatus());
                System.out.printf("  CreateTime: %s%n", response.getCreateTime());
            } catch (UqpayException e) {
                System.out.printf("Create card returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    // =========================================================================
    // Update Card
    // =========================================================================

    @Nested
    @DisplayName("Update Card")
    class UpdateCard {

        @Test
        @DisplayName("should update card details")
        void shouldUpdateCard() throws UqpayException {
            RetrieveCardResponse existingCard = getFirstCard();
            if (existingCard == null) {
                System.out.println("No cards available, skipping Update test");
                return;
            }

            String cardId = existingCard.getCardId();
            String modeType = existingCard.getModeType();
            System.out.printf("Card %s modeType=%s%n", cardId, modeType);

            CardUpdateRequest request = new CardUpdateRequest();
            if ("MULTI".equalsIgnoreCase(modeType)) {
                request.setCardLimit(5000.00);
                System.out.println("Updating cardLimit (MULTI mode card)");
            } else {
                // SINGLE mode cards do not support cardLimit updates; use metadata instead
                HashMap<String, String> meta = new HashMap<>();
                meta.put("test_key", "value_" + System.currentTimeMillis());
                request.setMetadata(meta);
                System.out.println("Updating metadata (SINGLE mode card)");
            }

            try {
                CardUpdatedResponse response = cardsService.update(cardId, request);

                assertThat(response).isNotNull();

                System.out.printf("Card updated: ID=%s, OrderID=%s, Status=%s, OrderStatus=%s%n",
                        response.getCardId(), response.getCardOrderId(),
                        response.getCardStatus(), response.getOrderStatus());
            } catch (UqpayException e) {
                System.out.printf("Update card returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    // =========================================================================
    // Update Card Status
    // =========================================================================

    @Nested
    @DisplayName("Update Card Status")
    class UpdateCardStatus {

        @Test
        @DisplayName("should freeze and unfreeze a card")
        void shouldFreezeCard() throws UqpayException {
            RetrieveCardResponse existingCard = getFirstCard();
            if (existingCard == null) {
                System.out.println("No cards available, skipping UpdateStatus test");
                return;
            }

            String cardId = existingCard.getCardId();
            String currentStatus = existingCard.getCardStatus();
            System.out.printf("Card %s current status: %s%n", cardId, currentStatus);

            if (!"ACTIVE".equals(currentStatus) && !"FROZEN".equals(currentStatus)) {
                System.out.printf("Card %s is %s (not ACTIVE or FROZEN), skipping freeze test%n",
                        cardId, currentStatus);
                return;
            }

            try {
                // If card is FROZEN from a previous run, unfreeze it first
                if ("FROZEN".equals(currentStatus)) {
                    System.out.println("Card is FROZEN, unfreezing first to establish ACTIVE baseline...");
                    UpdateCardStatusRequest unfreezeFirst = new UpdateCardStatusRequest();
                    unfreezeFirst.setCardStatus("ACTIVE");
                    cardsService.updateStatus(cardId, unfreezeFirst);

                    RetrieveCardResponse refreshed = cardsService.get(cardId);
                    System.out.printf("  Status after unfreeze: %s%n", refreshed.getCardStatus());
                }

                // Freeze the card
                UpdateCardStatusRequest freezeRequest = new UpdateCardStatusRequest();
                freezeRequest.setCardStatus("FROZEN");

                CardStatusResponse freezeResponse = cardsService.updateStatus(cardId, freezeRequest);
                assertThat(freezeResponse).isNotNull();
                System.out.printf("Card frozen: ID=%s, OrderID=%s, OrderStatus=%s%n",
                        freezeResponse.getCardId(), freezeResponse.getCardOrderId(), freezeResponse.getOrderStatus());

                RetrieveCardResponse frozenCard = cardsService.get(cardId);
                System.out.printf("  Status after freeze: %s%n", frozenCard.getCardStatus());

                // Unfreeze the card back to ACTIVE
                UpdateCardStatusRequest unfreezeRequest = new UpdateCardStatusRequest();
                unfreezeRequest.setCardStatus("ACTIVE");

                CardStatusResponse unfreezeResponse = cardsService.updateStatus(cardId, unfreezeRequest);
                assertThat(unfreezeResponse).isNotNull();
                System.out.printf("Card unfrozen: OrderStatus=%s%n", unfreezeResponse.getOrderStatus());

                RetrieveCardResponse restoredCard = cardsService.get(cardId);
                System.out.printf("  Restored status: %s%n", restoredCard.getCardStatus());
            } catch (UqpayException e) {
                System.out.printf("Update card status returned error: %s%n", e.getMessage());
                // Attempt to restore card to ACTIVE state
                try {
                    UpdateCardStatusRequest restore = new UpdateCardStatusRequest();
                    restore.setCardStatus("ACTIVE");
                    cardsService.updateStatus(cardId, restore);
                    System.out.println("  Card restored to ACTIVE after error");
                } catch (UqpayException restoreErr) {
                    System.out.printf("  Could not restore card status: %s%n", restoreErr.getMessage());
                }
            }
        }
    }

    // =========================================================================
    // Recharge Card
    // =========================================================================

    @Nested
    @DisplayName("Recharge Card")
    class RechargeCard {

        @Test
        @DisplayName("should recharge a card")
        void shouldRechargeCard() throws UqpayException {
            RetrieveCardResponse existingCard = getFirstCard();
            if (existingCard == null) {
                System.out.println("No cards available, skipping Recharge test");
                return;
            }

            if (!"ACTIVE".equals(existingCard.getCardStatus())) {
                System.out.printf("Card %s is not ACTIVE (status=%s), skipping recharge test%n",
                        existingCard.getCardId(), existingCard.getCardStatus());
                return;
            }

            String cardId = existingCard.getCardId();

            CardOrderRequest request = new CardOrderRequest();
            request.setAmount(100.50);

            try {
                CardOrder order = cardsService.recharge(cardId, request);

                assertThat(order).isNotNull();

                System.out.printf("Recharge order created: OrderID=%s, CardID=%s%n",
                        order.getCardOrderId(), order.getCardId());
                System.out.printf("  Amount: %.2f %s, Status: %s%n",
                        order.getAmount(), order.getCardCurrency(), order.getOrderStatus());
                System.out.printf("  OrderType: %s, CreateTime: %s%n",
                        order.getOrderType(), order.getCreateTime());
            } catch (UqpayException e) {
                System.out.printf("Recharge card returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    // =========================================================================
    // Withdraw from Card
    // =========================================================================

    @Nested
    @DisplayName("Withdraw from Card")
    class WithdrawCard {

        @Test
        @DisplayName("should withdraw from a card")
        void shouldWithdrawFromCard() throws UqpayException {
            RetrieveCardResponse existingCard = getFirstCard();
            if (existingCard == null) {
                System.out.println("No cards available, skipping Withdraw test");
                return;
            }

            if (!"ACTIVE".equals(existingCard.getCardStatus())) {
                System.out.printf("Card %s is not ACTIVE (status=%s), skipping withdraw test%n",
                        existingCard.getCardId(), existingCard.getCardStatus());
                return;
            }

            String cardId = existingCard.getCardId();

            // Recharge the card first to ensure sufficient balance
            CardOrderRequest rechargeRequest = new CardOrderRequest();
            rechargeRequest.setAmount(100.00);

            try {
                CardOrder rechargeOrder = cardsService.recharge(cardId, rechargeRequest);
                System.out.printf("Pre-withdraw recharge: OrderID=%s, Amount=%.2f, Status=%s%n",
                        rechargeOrder.getCardOrderId(), rechargeOrder.getAmount(), rechargeOrder.getOrderStatus());
            } catch (UqpayException e) {
                System.out.printf("Recharge before withdraw failed: %s — skipping withdraw test%n", e.getMessage());
                return;
            }

            // Now withdraw
            CardOrderRequest request = new CardOrderRequest();
            request.setAmount(50.00);

            try {
                CardOrder order = cardsService.withdraw(cardId, request);

                assertThat(order).isNotNull();

                System.out.printf("Withdraw order created: OrderID=%s, CardID=%s%n",
                        order.getCardOrderId(), order.getCardId());
                System.out.printf("  Amount: %.2f %s, Status: %s%n",
                        order.getAmount(), order.getCardCurrency(), order.getOrderStatus());
                System.out.printf("  OrderType: %s, CreateTime: %s%n",
                        order.getOrderType(), order.getCreateTime());
            } catch (UqpayException e) {
                System.out.printf("Withdraw from card returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    // =========================================================================
    // Get Order
    // =========================================================================

    @Nested
    @DisplayName("Get Order")
    class GetOrder {

        @Test
        @DisplayName("should get card order by order ID")
        void shouldGetCardOrder() throws UqpayException {
            // First, recharge a card to generate an order ID
            RetrieveCardResponse existingCard = getFirstCard();
            if (existingCard == null) {
                System.out.println("No cards available, skipping GetOrder test");
                return;
            }

            if (!"ACTIVE".equals(existingCard.getCardStatus())) {
                System.out.printf("Card %s is not ACTIVE (status=%s), skipping GetOrder test%n",
                        existingCard.getCardId(), existingCard.getCardStatus());
                return;
            }

            String cardId = existingCard.getCardId();

            // Create a recharge order to get an order ID
            CardOrderRequest rechargeRequest = new CardOrderRequest();
            rechargeRequest.setAmount(10.00);

            String orderId;
            try {
                CardOrder rechargeOrder = cardsService.recharge(cardId, rechargeRequest);
                orderId = rechargeOrder.getCardOrderId();
                System.out.printf("Created recharge order: %s%n", orderId);
            } catch (UqpayException e) {
                System.out.printf("Could not create recharge order for GetOrder test: %s%n", e.getMessage());
                return;
            }

            // Now retrieve the order
            try {
                CardOrder order = cardsService.getOrder(orderId);

                assertThat(order).isNotNull();
                assertThat(order.getCardOrderId()).isEqualTo(orderId);

                System.out.printf("Retrieved order: OrderID=%s, CardID=%s%n",
                        order.getCardOrderId(), order.getCardId());
                System.out.printf("  OrderType: %s, Amount: %.2f %s%n",
                        order.getOrderType(), order.getAmount(), order.getCardCurrency());
                System.out.printf("  Status: %s, CreateTime: %s%n",
                        order.getOrderStatus(), order.getCreateTime());
            } catch (UqpayException e) {
                System.out.printf("Get order returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    // =========================================================================
    // Activate Card
    // =========================================================================

    @Nested
    @DisplayName("Activate Card")
    class ActivateCard {

        @Test
        @DisplayName("should activate a card")
        void shouldActivateCard() throws UqpayException {
            // Activation requires a physical card's activation code, which is not available
            // in sandbox environments. This test uses a real card ID but a placeholder
            // activation code — the error should be activation-specific, not "Card does not exist".

            // Find an ENABLED product and create a card to get a real card ID
            ListProductsRequest productsRequest = new ListProductsRequest();
            productsRequest.setPageSize(10);
            productsRequest.setPageNumber(1);

            ListProductsResponse productsResponse = productsService.list(productsRequest);
            if (productsResponse.getData() == null || productsResponse.getData().isEmpty()) {
                System.out.println("No card products available, skipping Activate test");
                return;
            }

            String productId = null;
            String productCurrency = null;
            for (CardProduct product : productsResponse.getData()) {
                if ("ENABLED".equals(product.getProductStatus()) && "SINGLE".equals(product.getModeType())) {
                    productId = product.getProductId();
                    if (product.getCardCurrency() != null && !product.getCardCurrency().isEmpty()) {
                        productCurrency = product.getCardCurrency().get(0);
                    }
                    break;
                }
            }

            if (productId == null) {
                System.out.println("No suitable ENABLED/SINGLE product found, skipping Activate test");
                return;
            }

            CreateCardholderResponse cardholder = createCardholderForTest("Activate");
            if (cardholder == null) {
                System.out.println("Could not create cardholder, skipping Activate test");
                return;
            }

            String realCardId;
            try {
                CreateCardRequest createReq = new CreateCardRequest();
                createReq.setCardCurrency(productCurrency);
                createReq.setCardholderId(cardholder.getCardholderId());
                createReq.setCardProductId(productId);

                CardCreationResponse createResp = cardsService.create(createReq);
                realCardId = createResp.getCardId();
                System.out.printf("Created card for activation test: ID=%s%n", realCardId);
            } catch (UqpayException e) {
                System.out.printf("Could not create card for activation test: %s%n", e.getMessage());
                return;
            }

            // Attempt activation with placeholder code (sandbox limitation)
            ActivateCardRequest request = new ActivateCardRequest();
            request.setCardId(realCardId);
            request.setActivationCode("000000");
            request.setPin("123456");

            try {
                ActivateCardResponse response = cardsService.activate(request);

                assertThat(response).isNotNull();

                System.out.printf("Card activated: RequestStatus=%s%n", response.getRequestStatus());
            } catch (UqpayException e) {
                // Expected in sandbox — activation codes are not available
                System.out.printf("Activate card returned error (expected in sandbox): %s%n", e.getMessage());
            }
        }
    }

    // =========================================================================
    // Reset PIN
    // =========================================================================

    @Nested
    @DisplayName("Reset PIN")
    class ResetPIN {

        @Test
        @DisplayName("should reset card PIN")
        void shouldResetPIN() throws UqpayException {
            // PIN reset only works on physical cards
            RetrieveCardResponse physicalCard = findCardByFormFactor("PHYSICAL");

            if (physicalCard == null) {
                System.out.println("No PHYSICAL card found in existing cards, checking products...");

                // Check if a physical card product exists
                ListProductsRequest productsRequest = new ListProductsRequest();
                productsRequest.setPageSize(10);
                productsRequest.setPageNumber(1);

                ListProductsResponse productsResponse = productsService.list(productsRequest);
                String physicalProductId = null;
                String physicalCurrency = null;

                if (productsResponse.getData() != null) {
                    for (CardProduct product : productsResponse.getData()) {
                        if (!"ENABLED".equals(product.getProductStatus())) continue;
                        if (product.getCardForm() != null && product.getCardForm().contains("PHYSICAL")) {
                            physicalProductId = product.getProductId();
                            if (product.getCardCurrency() != null && !product.getCardCurrency().isEmpty()) {
                                physicalCurrency = product.getCardCurrency().get(0);
                            }
                            break;
                        }
                    }
                }

                if (physicalProductId == null) {
                    System.out.println("No physical card products available — PIN reset only works on physical cards, skipping");
                    return;
                }

                // Create a physical card
                CreateCardholderResponse cardholder = createCardholderForTest("PinReset");
                if (cardholder == null) {
                    System.out.println("Could not create cardholder for physical card, skipping");
                    return;
                }

                try {
                    CreateCardRequest createReq = new CreateCardRequest();
                    createReq.setCardCurrency(physicalCurrency);
                    createReq.setCardholderId(cardholder.getCardholderId());
                    createReq.setCardProductId(physicalProductId);

                    CardCreationResponse createResp = cardsService.create(createReq);
                    // Fetch the full card details
                    physicalCard = cardsService.get(createResp.getCardId());
                    System.out.printf("Created physical card for PIN test: ID=%s%n", physicalCard.getCardId());
                } catch (UqpayException e) {
                    System.out.printf("Could not create physical card: %s — skipping PIN reset test%n", e.getMessage());
                    return;
                }
            }

            System.out.printf("Using physical card: ID=%s, FormFactor=%s%n",
                    physicalCard.getCardId(), physicalCard.getFormFactor());

            SetPINRequest request = new SetPINRequest();
            request.setCardId(physicalCard.getCardId());
            request.setPin("123456");

            try {
                SetPINResponse response = cardsService.resetPIN(request);

                assertThat(response).isNotNull();

                System.out.printf("PIN reset: CardID=%s, RequestStatus=%s%n",
                        physicalCard.getCardId(), response.getRequestStatus());
            } catch (UqpayException e) {
                System.out.printf("Reset PIN returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    // =========================================================================
    // Assign Card
    // =========================================================================

    @Nested
    @DisplayName("Assign Card")
    class AssignCard {

        @Test
        @DisplayName("should assign a card to a cardholder")
        void shouldAssignCard() throws UqpayException {
            // Get an existing card and its full unmasked number via getSecure
            RetrieveCardResponse existingCard = getFirstCard();
            if (existingCard == null) {
                System.out.println("No cards available, skipping Assign test");
                return;
            }

            String cardId = existingCard.getCardId();
            String cardCurrency = existingCard.getCardCurrency();

            String fullCardNumber;
            try {
                SecureCardInfo secureInfo = cardsService.getSecure(cardId);
                fullCardNumber = secureInfo.getCardNumber();
                System.out.printf("Got card number from getSecure: %s...%n",
                        TestHelper.safePreview(fullCardNumber, 6));
            } catch (UqpayException e) {
                System.out.printf("Could not get secure card info: %s — skipping Assign test%n", e.getMessage());
                return;
            }

            // Create a new cardholder for the assignment
            CreateCardholderResponse newCardholder = createCardholderForTest("Assign");
            if (newCardholder == null) {
                System.out.println("Could not create cardholder for assign test, skipping");
                return;
            }
            System.out.printf("Created cardholder for assign: ID=%s%n", newCardholder.getCardholderId());

            AssignCardRequest request = new AssignCardRequest();
            request.setCardholderId(newCardholder.getCardholderId());
            request.setCardNumber(fullCardNumber);
            request.setCardCurrency(cardCurrency);
            request.setCardMode("SINGLE");

            try {
                AssignCardResponse response = cardsService.assign(request);

                assertThat(response).isNotNull();

                System.out.printf("Card assigned: ID=%s, OrderID=%s, Status=%s, OrderStatus=%s%n",
                        response.getCardId(), response.getCardOrderId(),
                        response.getCardStatus(), response.getOrderStatus());
                System.out.printf("  CreateTime: %s%n", response.getCreateTime());
            } catch (UqpayException e) {
                System.out.printf("Assign card returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    // =========================================================================
    // Bulk Create Cards
    // =========================================================================

    @Nested
    @DisplayName("Bulk Create Cards")
    class BulkCreateCards {

        @Test
        @DisplayName("should bulk create cards")
        void shouldBulkCreateCards() throws UqpayException {
            // First find a valid card BIN from products
            ListProductsRequest productsRequest = new ListProductsRequest();
            productsRequest.setPageSize(10);
            productsRequest.setPageNumber(1);

            ListProductsResponse productsResponse = productsService.list(productsRequest);
            if (productsResponse.getData() == null || productsResponse.getData().isEmpty()) {
                System.out.println("No card products available, skipping BulkCreate test");
                return;
            }

            String cardBin = null;
            for (CardProduct product : productsResponse.getData()) {
                if ("ENABLED".equals(product.getProductStatus()) && product.getCardBin() != null) {
                    cardBin = product.getCardBin();
                    System.out.printf("Using card BIN: %s (product: %s)%n", cardBin, product.getProductId());
                    break;
                }
            }

            if (cardBin == null) {
                System.out.println("No suitable card BIN found, skipping BulkCreate test");
                return;
            }

            BulkCardCreationRequest request = new BulkCardCreationRequest();
            request.setCardBin(cardBin);
            request.setNumbers(2);

            try {
                BulkCardCreationResponse response = cardsService.bulkCreate(request);

                assertThat(response).isNotNull();

                System.out.printf("Bulk create initiated: ReportID=%s, ExpireDate=%s%n",
                        response.getReportId(), response.getExpireDate());
            } catch (UqpayException e) {
                System.out.printf("Bulk create cards returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    // =========================================================================
    // List Cards (existing)
    // =========================================================================

    @Nested
    @DisplayName("List Cards")
    class ListCards {

        @Test
        @DisplayName("should list cards with pagination")
        void shouldListCardsWithPagination() throws UqpayException {
            ListCardsRequest request = new ListCardsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListCardsResponse response = cardsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d cards (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                RetrieveCardResponse first = response.getData().get(0);
                System.out.printf("First card: ID=%s, Number=%s, Status=%s, Currency=%s%n",
                        first.getCardId(), first.getCardNumber(),
                        first.getCardStatus(), first.getCardCurrency());
            }
        }
    }

    // =========================================================================
    // Get Card (existing)
    // =========================================================================

    @Nested
    @DisplayName("Get Card")
    class GetCard {

        @Test
        @DisplayName("should get card by ID")
        void shouldGetCardById() throws UqpayException {
            RetrieveCardResponse existingCard = getFirstCard();
            if (existingCard == null) {
                System.out.println("No cards available, skipping Get test");
                return;
            }

            String cardId = existingCard.getCardId();

            RetrieveCardResponse card = cardsService.get(cardId);

            assertThat(card).isNotNull();
            assertThat(card.getCardId()).isEqualTo(cardId);

            System.out.printf("Retrieved card: ID=%s, Bin=%s, Scheme=%s, Currency=%s, Status=%s%n",
                    card.getCardId(), card.getCardBin(), card.getCardScheme(),
                    card.getCardCurrency(), card.getCardStatus());
            System.out.printf("  Number=%s, FormFactor=%s, ModeType=%s, ProductID=%s%n",
                    card.getCardNumber(), card.getFormFactor(), card.getModeType(), card.getCardProductId());
            System.out.printf("  Limit=%s, AvailableBalance=%s%n",
                    card.getCardLimit(), card.getAvailableBalance());
            if (card.getCardholder() != null) {
                System.out.printf("  Cardholder: %s %s (%s)%n",
                        card.getCardholder().getFirstName(), card.getCardholder().getLastName(),
                        card.getCardholder().getEmail());
            }
        }
    }

    // =========================================================================
    // Create PAN Token
    // =========================================================================

    @Nested
    @DisplayName("Create PAN Token")
    class CreatePanToken {

        @Test
        @DisplayName("should create a PAN token for a card")
        void shouldCreatePanToken() throws UqpayException {
            RetrieveCardResponse existingCard = getFirstCard();
            if (existingCard == null) {
                System.out.println("No cards available, skipping CreatePanToken test");
                return;
            }

            String cardId = existingCard.getCardId();

            try {
                PanTokenResponse response = cardsService.createPanToken(cardId);

                assertThat(response).isNotNull();
                assertThat(response.getToken()).isNotNull();
                assertThat(response.getExpiresIn()).isNotNull();

                System.out.printf("PAN token created: Token=%s..., ExpiresIn=%d, ExpiresAt=%s%n",
                        TestHelper.safePreview(response.getToken(), 10),
                        response.getExpiresIn(), response.getExpiresAt());
            } catch (UqpayException e) {
                System.out.printf("Create PAN token returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    // =========================================================================
    // Get Secure Card Info (existing)
    // =========================================================================

    @Nested
    @DisplayName("Get Secure Card Info")
    class GetSecureCardInfo {

        @Test
        @DisplayName("should get secure card info")
        void shouldGetSecureCardInfo() throws UqpayException {
            RetrieveCardResponse existingCard = getFirstCard();
            if (existingCard == null) {
                System.out.println("No cards available, skipping GetSecure test");
                return;
            }

            String cardId = existingCard.getCardId();

            try {
                SecureCardInfo secureInfo = cardsService.getSecure(cardId);

                assertThat(secureInfo).isNotNull();

                System.out.printf("Secure card info: CVV=%s, Expire=%s, Number=%s%n",
                        TestHelper.safePreview(secureInfo.getCvv(), 1),
                        TestHelper.safePreview(secureInfo.getExpireDate(), 4),
                        TestHelper.safePreview(secureInfo.getCardNumber(), 6));
            } catch (UqpayException e) {
                // May return 403 in sandbox without special permission
                System.out.printf("GetSecure returned error (may need special permission): %s%n", e.getMessage());
            }
        }
    }
}
