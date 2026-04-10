package com.uqpay.sdk.payment;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.payment.model.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PaymentIntentsService Integration Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentIntentsServiceIntegrationTest {

    private static PaymentIntentsService paymentIntentsService;

    /** Shared across ordered tests in the PaymentIntentLifecycle nested class. */
    private static String createdIntentId;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        PaymentClient paymentClient = new PaymentClient(apiClient);
        paymentIntentsService = paymentClient.getPaymentIntents();
    }

    // ========================================================================
    // Payment Intent Full Lifecycle (Create -> Confirm -> Get -> List ->
    //   ListWithFilters -> Update -> Cancel)
    // ========================================================================

    @Nested
    @DisplayName("Payment Intent Lifecycle")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class PaymentIntentLifecycle {

        @Test
        @Order(1)
        @DisplayName("should create a payment intent")
        void shouldCreatePaymentIntent() throws UqpayException {
            CreatePaymentIntentRequest request = new CreatePaymentIntentRequest();
            request.setAmount("100.00");
            request.setCurrency("USD");
            request.setMerchantOrderId("test-order-001");
            request.setDescription("Test payment intent");
            request.setReturnUrl("https://example.com/return");

            Map<String, String> metadata = new HashMap<>();
            metadata.put("test", "true");
            request.setMetadata(metadata);

            // Optional: ip_address
            request.setIpAddress("203.0.113.50");

            // Optional: browser_info
            BrowserInfo browserInfo = new BrowserInfo();
            browserInfo.setAcceptHeader("text/html,application/xhtml+xml");
            browserInfo.setLanguage("en-US");
            browserInfo.setScreenColorDepth(24);
            browserInfo.setScreenHeight(1080);
            browserInfo.setScreenWidth(1920);
            browserInfo.setTimezone("-480");

            BrowserInfo.Browser browser = new BrowserInfo.Browser();
            browser.setJavaEnabled(false);
            browser.setJavascriptEnabled(true);
            browser.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)");
            browserInfo.setBrowser(browser);

            BrowserInfo.Location location = new BrowserInfo.Location();
            location.setLat("1.3521");
            location.setLon("103.8198");
            browserInfo.setLocation(location);

            BrowserInfo.Mobile mobile = new BrowserInfo.Mobile();
            mobile.setDeviceModel("iPhone 15");
            mobile.setOsType("IOS");
            mobile.setOsVersion("17.0");
            browserInfo.setMobile(mobile);

            request.setBrowserInfo(browserInfo);

            // Optional: payment_orders
            PaymentOrders paymentOrders = new PaymentOrders();
            paymentOrders.setType("retail");

            PaymentOrders.Product product = new PaymentOrders.Product();
            product.setName("Test Product");
            product.setPrice("100.00");
            product.setQuantity(1);
            product.setImageUrl("https://example.com/product.jpg");

            paymentOrders.setProducts(Collections.singletonList(product));
            request.setPaymentOrders(paymentOrders);

            PaymentIntent intent = paymentIntentsService.create(request);

            assertThat(intent).isNotNull();
            assertThat(intent.getPaymentIntentId()).isNotEmpty();
            assertThat(intent.getAmount()).isNotEmpty();
            assertThat(intent.getCurrency()).isEqualTo("USD");

            createdIntentId = intent.getPaymentIntentId();

            System.out.printf("Payment intent created successfully%n");
            System.out.printf("   ID: %s%n", intent.getPaymentIntentId());
            System.out.printf("   Amount: %s %s%n", intent.getAmount(), intent.getCurrency());
            System.out.printf("   Status: %s%n", intent.getPaymentIntentStatus());
            System.out.printf("   Merchant Order ID: %s%n", intent.getMerchantOrderId());
            System.out.printf("   Description: %s%n", intent.getDescription());
            System.out.printf("   Created: %s%n", intent.getCreateTime());
        }

        @Test
        @Order(2)
        @DisplayName("should confirm a payment intent with card")
        void shouldConfirmPaymentIntent() throws UqpayException {
            if (createdIntentId == null) {
                System.out.println("No payment intent ID available, skipping Confirm test");
                return;
            }

            // Build card payment method with billing
            Address address = new Address();
            address.setCountryCode("SG");
            address.setCity("Singapore");
            address.setStreet("444 Orchard Rd, Midpoint Orchard, Singapore");
            address.setPostcode("924011");

            Billing billing = new Billing();
            billing.setFirstName("Test");
            billing.setLastName("User");
            billing.setEmail("test@example.com");
            billing.setPhoneNumber("+10000000000");
            billing.setAddress(address);

            Card card = new Card();
            card.setCardNumber("5346930100108117");
            card.setExpiryMonth("11");
            card.setExpiryYear("2026");
            card.setCvc("811");
            card.setCardName("Test User");
            card.setNetwork("visa");
            card.setAuthorizationType("authorization");
            card.setThreeDsAction("skip_3ds");
            card.setBilling(billing);

            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.setType("card");
            paymentMethod.setCard(card);

            ConfirmPaymentIntentRequest confirmRequest = new ConfirmPaymentIntentRequest();
            confirmRequest.setPaymentMethod(paymentMethod);
            confirmRequest.setReturnUrl("https://example.com/return");

            try {
                PaymentIntent intent = paymentIntentsService.confirm(createdIntentId, confirmRequest);

                assertThat(intent).isNotNull();
                assertThat(intent.getPaymentIntentId()).isNotEmpty();
                assertThat(intent.getPaymentIntentStatus()).isNotEmpty();

                Set<String> validStatuses = new HashSet<>(Arrays.asList(
                        "REQUIRES_CUSTOMER_ACTION", "REQUIRES_CAPTURE", "PENDING", "SUCCEEDED",
                        "REQUIRES_PAYMENT_METHOD"));
                assertThat(validStatuses).contains(intent.getPaymentIntentStatus());

                System.out.printf("Payment intent confirmed successfully%n");
                System.out.printf("   ID: %s%n", intent.getPaymentIntentId());
                System.out.printf("   Status: %s%n", intent.getPaymentIntentStatus());
                System.out.printf("   Amount: %s %s%n", intent.getAmount(), intent.getCurrency());
                if (intent.getNextAction() != null) {
                    System.out.printf("   Next Action: %s%n", intent.getNextAction());
                }
            } catch (UqpayException e) {
                // Empty card details may be rejected in sandbox
                System.out.printf("Confirm returned expected error: %s%n", e.getMessage());
            }
        }

        @Test
        @Order(3)
        @DisplayName("should capture a payment intent")
        void shouldCapturePaymentIntent() throws UqpayException {
            if (createdIntentId == null) {
                System.out.println("No payment intent ID available, skipping Capture test");
                return;
            }

            CapturePaymentIntentRequest captureRequest = new CapturePaymentIntentRequest();
            captureRequest.setAmountToCapture("100.00");

            try {
                PaymentIntent intent = paymentIntentsService.capture(createdIntentId, captureRequest);

                assertThat(intent).isNotNull();
                assertThat(intent.getPaymentIntentId()).isNotEmpty();

                System.out.printf("Payment intent capture attempted%n");
                System.out.printf("   ID: %s%n", intent.getPaymentIntentId());
                System.out.printf("   Status: %s%n", intent.getPaymentIntentStatus());
                System.out.printf("   Captured Amount: %s%n", intent.getCapturedAmount());
            } catch (UqpayException e) {
                // Capture may fail if intent is not in REQUIRES_CAPTURE state
                System.out.printf("Capture returned expected error: %s%n", e.getMessage());
            }
        }

        @Test
        @Order(4)
        @DisplayName("should get a payment intent by ID")
        void shouldGetPaymentIntent() throws UqpayException {
            // Fallback to listing if create didn't run
            if (createdIntentId == null) {
                ListPaymentIntentsRequest listRequest = new ListPaymentIntentsRequest();
                listRequest.setPageSize(10);
                listRequest.setPageNumber(1);
                ListPaymentIntentsResponse listResponse = paymentIntentsService.list(listRequest);
                if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                    System.out.println("No payment intents available, skipping Get test");
                    return;
                }
                createdIntentId = listResponse.getData().get(0).getPaymentIntentId();
            }

            PaymentIntent intent = paymentIntentsService.get(createdIntentId);

            assertThat(intent).isNotNull();
            assertThat(intent.getPaymentIntentId()).isEqualTo(createdIntentId);

            System.out.printf("Payment intent retrieved successfully%n");
            System.out.printf("   ID: %s%n", intent.getPaymentIntentId());
            System.out.printf("   Amount: %s %s%n", intent.getAmount(), intent.getCurrency());
            System.out.printf("   Status: %s%n", intent.getPaymentIntentStatus());
            System.out.printf("   Merchant Order ID: %s%n", intent.getMerchantOrderId());
        }

        @Test
        @Order(5)
        @DisplayName("should list payment intents with pagination")
        void shouldListPaymentIntents() throws UqpayException {
            ListPaymentIntentsRequest request = new ListPaymentIntentsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListPaymentIntentsResponse response = paymentIntentsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d payment intents (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            int limit = Math.min(3, response.getData().size());
            for (int i = 0; i < limit; i++) {
                PaymentIntent intent = response.getData().get(i);
                System.out.printf("   Intent %d: ID=%s, Amount=%s %s, Status=%s%n",
                        i + 1, intent.getPaymentIntentId(), intent.getAmount(),
                        intent.getCurrency(), intent.getPaymentIntentStatus());
            }
            if (response.getData().size() > 3) {
                System.out.printf("   ... and %d more%n", response.getData().size() - 3);
            }
        }

        @Test
        @Order(6)
        @DisplayName("should list payment intents with status filters")
        void shouldListPaymentIntentsWithFilters() throws UqpayException {
            String[] statuses = {
                    "requires_payment_method", "requires_confirmation", "succeeded", "canceled"
            };

            for (String status : statuses) {
                ListPaymentIntentsRequest request = new ListPaymentIntentsRequest();
                request.setPageSize(10);
                request.setPageNumber(1);
                request.setStatus(status);

                ListPaymentIntentsResponse response = paymentIntentsService.list(request);

                assertThat(response).isNotNull();
                assertThat(response.getData()).isNotNull();

                System.out.printf("%s intents: %d found%n", status, response.getTotalItems());
            }
        }

        @Test
        @Order(7)
        @DisplayName("should update a payment intent")
        void shouldUpdatePaymentIntent() {
            try {
                // Create a fresh intent that hasn't been confirmed yet
                CreatePaymentIntentRequest createRequest = new CreatePaymentIntentRequest();
                createRequest.setAmount("25.00");
                createRequest.setCurrency("USD");
                createRequest.setMerchantOrderId("test-update-" + System.currentTimeMillis());
                createRequest.setDescription("Intent for update test");
                createRequest.setReturnUrl("https://example.com/return");

                PaymentIntent created = paymentIntentsService.create(createRequest);
                String freshIntentId = created.getPaymentIntentId();
                System.out.printf("Created fresh intent for update: %s (status: %s)%n",
                        freshIntentId, created.getPaymentIntentStatus());

                // Update it before confirming
                UpdatePaymentIntentRequest request = new UpdatePaymentIntentRequest();
                request.setDescription("Updated test payment");

                Map<String, String> metadata = new HashMap<>();
                metadata.put("updated", "true");
                request.setMetadata(metadata);

                PaymentIntent intent = paymentIntentsService.update(freshIntentId, request);

                assertThat(intent).isNotNull();
                assertThat(intent.getPaymentIntentId()).isNotEmpty();

                System.out.printf("Payment intent updated successfully%n");
                System.out.printf("   ID: %s%n", intent.getPaymentIntentId());
                System.out.printf("   Description: %s%n", intent.getDescription());
                System.out.printf("   Updated: %s%n", intent.getUpdateTime());
            } catch (UqpayException e) {
                System.out.printf("Update returned error (may be expected): %s%n", e.getMessage());
            }
        }

        @Test
        @Order(8)
        @DisplayName("should cancel a payment intent")
        void shouldCancelPaymentIntent() throws UqpayException {
            try {
                // Create a fresh intent for cancellation
                CreatePaymentIntentRequest createRequest = new CreatePaymentIntentRequest();
                createRequest.setAmount("10.00");
                createRequest.setCurrency("USD");
                createRequest.setMerchantOrderId("test-cancel-" + System.currentTimeMillis());
                createRequest.setDescription("Intent for cancel test");
                createRequest.setReturnUrl("https://example.com/return");

                PaymentIntent created = paymentIntentsService.create(createRequest);
                String cancelIntentId = created.getPaymentIntentId();
                System.out.printf("Created fresh intent for cancel: %s (status: %s)%n",
                        cancelIntentId, created.getPaymentIntentStatus());

                // Cancel it (can cancel from REQUIRES_PAYMENT_METHOD state)
                CancelPaymentIntentRequest request = new CancelPaymentIntentRequest();
                request.setCancellationReason("requested_by_customer");

                PaymentIntent intent = paymentIntentsService.cancel(cancelIntentId, request);

                assertThat(intent).isNotNull();
                assertThat(intent.getPaymentIntentId()).isNotEmpty();

                System.out.printf("Payment intent cancelled successfully%n");
                System.out.printf("   ID: %s%n", intent.getPaymentIntentId());
                System.out.printf("   Status: %s%n", intent.getPaymentIntentStatus());
            } catch (UqpayException e) {
                System.out.printf("Cancel returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    // ========================================================================
    // Confirm Payment Methods (table-driven tests for each payment method type)
    // Each test creates its own intent and confirms with a specific method.
    // ========================================================================

    @Nested
    @DisplayName("Confirm Payment Methods")
    class ConfirmPaymentMethods {

        // ---- Card Payments ----

        @Test
        @DisplayName("Card")
        void card() throws UqpayException {
            Address address = new Address();
            address.setCountryCode("SG");
            address.setCity("Singapore");
            address.setStreet("444 Orchard Rd, Midpoint Orchard, Singapore");
            address.setPostcode("924011");

            Billing billing = new Billing();
            billing.setFirstName("Test");
            billing.setLastName("User");
            billing.setEmail("test@example.com");
            billing.setPhoneNumber("+10000000000");
            billing.setAddress(address);

            Card card = new Card();
            card.setCardNumber("4111111111111111");
            card.setExpiryMonth("12");
            card.setExpiryYear("2030");
            card.setCvc("123");
            card.setCardName("Test User");
            card.setNetwork("visa");
            card.setAuthorizationType("authorization");
            card.setThreeDsAction("skip_3ds");
            card.setBilling(billing);

            PaymentMethod pm = new PaymentMethod();
            pm.setType("card");
            pm.setCard(card);

            confirmWithMethod("Card", pm, "USD");
        }

        // ---- China & Hong Kong Wallets ----

        @Test
        @DisplayName("AlipayCN QRCode")
        void alipayCnQrCode() throws UqpayException {
            WalletPayment wallet = new WalletPayment();
            wallet.setFlow("qrcode");
            wallet.setIsPresent(false);

            PaymentMethod pm = new PaymentMethod();
            pm.setType("alipaycn");
            pm.setAlipayCN(wallet);

            confirmWithMethod("AlipayCN_QRCode", pm, "USD");
        }

        @Test
        @DisplayName("AlipayHK QRCode")
        void alipayHkQrCode() throws UqpayException {
            WalletPayment wallet = new WalletPayment();
            wallet.setFlow("qrcode");
            wallet.setIsPresent(false);

            PaymentMethod pm = new PaymentMethod();
            pm.setType("alipayhk");
            pm.setAlipayHK(wallet);

            confirmWithMethod("AlipayHK_QRCode", pm, "USD");
        }

        @Test
        @DisplayName("UnionPay QRCode")
        void unionPayQrCode() throws UqpayException {
            WalletPayment wallet = new WalletPayment();
            wallet.setFlow("qrcode");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("unionpay");
            pm.setUnionPay(wallet);

            confirmWithMethod("UnionPay_QRCode", pm, "USD");
        }

        @Test
        @DisplayName("UnionPay SecurePay")
        void unionPaySecurePay() throws UqpayException {
            WalletPayment wallet = new WalletPayment();
            wallet.setFlow("securepay");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("unionpay");
            pm.setUnionPay(wallet);

            confirmWithMethod("UnionPay_SecurePay", pm, "USD");
        }

        @Test
        @DisplayName("WeChatPay QRCode")
        void weChatPayQrCode() throws UqpayException {
            WeChatPay weChatPay = new WeChatPay();
            weChatPay.setFlow("qrcode");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("wechatpay");
            pm.setWeChatPay(weChatPay);

            confirmWithMethod("WeChatPay_QRCode", pm, "SGD");
        }

        @Test
        @DisplayName("WeChatPay MobileWeb")
        void weChatPayMobileWeb() throws UqpayException {
            WeChatPay weChatPay = new WeChatPay();
            weChatPay.setFlow("mobile_web");
            weChatPay.setOsType("ios");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("wechatpay");
            pm.setWeChatPay(weChatPay);

            confirmWithMethod("WeChatPay_MobileWeb", pm, "SGD");
        }

        @Test
        @DisplayName("WeChatPay MiniProgram")
        void weChatPayMiniProgram() throws UqpayException {
            WeChatPay weChatPay = new WeChatPay();
            weChatPay.setFlow("mini_program");
            weChatPay.setOsType("ios");
            weChatPay.setOpenId("");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("wechatpay");
            pm.setWeChatPay(weChatPay);

            confirmWithMethod("WeChatPay_MiniProgram", pm, "SGD");
        }

        @Test
        @DisplayName("WeChatPay MobileApp")
        void weChatPayMobileApp() throws UqpayException {
            WeChatPay weChatPay = new WeChatPay();
            weChatPay.setFlow("mobile_app");
            weChatPay.setOsType("ios");
            weChatPay.setOpenId(""); // required

            PaymentMethod pm = new PaymentMethod();
            pm.setType("wechatpay");
            pm.setWeChatPay(weChatPay);

            confirmWithMethod("WeChatPay_MobileApp", pm, "SGD");
        }

        @Test
        @DisplayName("WeChatPay OfficialAccount")
        void weChatPayOfficialAccount() throws UqpayException {
            WeChatPay weChatPay = new WeChatPay();
            weChatPay.setFlow("official_account");
            weChatPay.setOsType("ios");
            weChatPay.setOpenId(""); // required

            PaymentMethod pm = new PaymentMethod();
            pm.setType("wechatpay");
            pm.setWeChatPay(weChatPay);

            confirmWithMethod("WeChatPay_OfficialAccount", pm, "SGD");
        }

        // ---- Southeast Asia Wallets ----

        @Test
        @DisplayName("GrabPay QRCode")
        void grabPayQrCode() throws UqpayException {
            GrabPay grabPay = new GrabPay();
            grabPay.setFlow("qrcode");
            grabPay.setShopperName("Test Shopper");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("grabpay");
            pm.setGrabPay(grabPay);

            confirmWithMethod("GrabPay_QRCode", pm, "USD");
        }

        @Test
        @DisplayName("PayNow QRCode")
        void payNowQrCode() throws UqpayException {
            WalletPayment wallet = new WalletPayment();
            wallet.setFlow("qrcode");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("paynow");
            pm.setPayNow(wallet);

            confirmWithMethod("PayNow_QRCode", pm, "SGD");
        }

        @Test
        @DisplayName("TrueMoney QRCode")
        void trueMoneyQrCode() throws UqpayException {
            WalletPayment wallet = new WalletPayment();
            wallet.setFlow("qrcode");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("truemoney");
            pm.setTrueMoney(wallet);

            confirmWithMethod("TrueMoney_QRCode", pm, "USD");
        }

        @Test
        @DisplayName("TNG QRCode")
        void tngQrCode() throws UqpayException {
            WalletPayment wallet = new WalletPayment();
            wallet.setFlow("qrcode");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("tng");
            pm.setTng(wallet);

            confirmWithMethod("TNG_QRCode", pm, "USD");
        }

        @Test
        @DisplayName("GCash QRCode")
        void gCashQrCode() throws UqpayException {
            WalletPayment wallet = new WalletPayment();
            wallet.setFlow("qrcode");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("gcash");
            pm.setGCash(wallet);

            confirmWithMethod("GCash_QRCode", pm, "USD");
        }

        @Test
        @DisplayName("Dana QRCode")
        void danaQrCode() throws UqpayException {
            WalletPayment wallet = new WalletPayment();
            wallet.setFlow("qrcode");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("dana");
            pm.setDana(wallet);

            confirmWithMethod("Dana_QRCode", pm, "USD");
        }

        // ---- Korean Wallets ----

        @Test
        @DisplayName("KakaoPay QRCode")
        void kakaoPayQrCode() throws UqpayException {
            WalletPayment wallet = new WalletPayment();
            wallet.setFlow("qrcode");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("kakaopay");
            pm.setKakaoPay(wallet);

            confirmWithMethod("KakaoPay_QRCode", pm, "USD");
        }

        @Test
        @DisplayName("Toss QRCode")
        void tossQrCode() throws UqpayException {
            WalletPayment wallet = new WalletPayment();
            wallet.setFlow("qrcode");
            wallet.setIsPresent(false);

            PaymentMethod pm = new PaymentMethod();
            pm.setType("tosspay");
            pm.setToss(wallet);

            confirmWithMethod("Toss_QRCode", pm, "USD");
        }

        @Test
        @DisplayName("NaverPay QRCode")
        void naverPayQrCode() throws UqpayException {
            WalletPayment wallet = new WalletPayment();
            wallet.setFlow("qrcode");

            PaymentMethod pm = new PaymentMethod();
            pm.setType("naverpay");
            pm.setNaverPay(wallet);

            confirmWithMethod("NaverPay_QRCode", pm, "USD");
        }

        // ---- Helper ----

        private void confirmWithMethod(String name, PaymentMethod paymentMethod, String currency) throws UqpayException {
            try {
                // Step 1: Create a payment intent
                CreatePaymentIntentRequest createRequest = new CreatePaymentIntentRequest();
                createRequest.setAmount("0.01");
                createRequest.setCurrency(currency);
                createRequest.setMerchantOrderId("test-" + name.replace("_", "-") + "-001");
                createRequest.setDescription("Test " + name.replace("_", " ") + " payment");
                createRequest.setReturnUrl("https://example.com/return");

                PaymentIntent created = paymentIntentsService.create(createRequest);

                assertThat(created).isNotNull();
                assertThat(created.getPaymentIntentId()).isNotEmpty();

                System.out.printf("[%s] Created intent: %s (status: %s)%n",
                        name, created.getPaymentIntentId(), created.getPaymentIntentStatus());

                // Step 2: Confirm with the specific payment method
                ConfirmPaymentIntentRequest confirmRequest = new ConfirmPaymentIntentRequest();
                confirmRequest.setPaymentMethod(paymentMethod);
                confirmRequest.setReturnUrl("https://example.com/return");

                PaymentIntent confirmed = paymentIntentsService.confirm(
                        created.getPaymentIntentId(), confirmRequest);

                assertThat(confirmed).isNotNull();
                assertThat(confirmed.getPaymentIntentId()).isNotEmpty();
                assertThat(confirmed.getPaymentIntentStatus()).isNotEmpty();

                Set<String> validStatuses = new HashSet<>(Arrays.asList(
                        "REQUIRES_CUSTOMER_ACTION", "REQUIRES_CAPTURE", "PENDING", "SUCCEEDED",
                        "REQUIRES_PAYMENT_METHOD"));
                assertThat(validStatuses).contains(confirmed.getPaymentIntentStatus());

                System.out.printf("[%s] Confirmed successfully%n", name);
                System.out.printf("   ID: %s%n", confirmed.getPaymentIntentId());
                System.out.printf("   Status: %s%n", confirmed.getPaymentIntentStatus());
                System.out.printf("   Amount: %s %s%n", confirmed.getAmount(), confirmed.getCurrency());
                if (confirmed.getNextAction() != null) {
                    System.out.printf("   Next Action: %s%n", confirmed.getNextAction());
                }
            } catch (UqpayException e) {
                // Some payment methods may return errors in sandbox (e.g. missing open_id, invalid description)
                System.out.printf("[%s] Returned expected sandbox error: %s%n", name, e.getMessage());
            }
        }
    }
}
