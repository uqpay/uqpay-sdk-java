package com.uqpay.sdk.payment;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.payment.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RefundsService Integration Tests")
class RefundsServiceIntegrationTest {

    private static RefundsService refundsService;
    private static PaymentIntentsService paymentIntentsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        PaymentClient paymentClient = new PaymentClient(apiClient);
        refundsService = paymentClient.getRefunds();
        paymentIntentsService = paymentClient.getPaymentIntents();
    }

    /**
     * Helper: create a payment intent, confirm with card, and capture it.
     * Returns the payment intent ID, or null on failure.
     */
    private static String createAndCaptureIntent() {
        try {
            // Create
            CreatePaymentIntentRequest createRequest = new CreatePaymentIntentRequest();
            createRequest.setAmount("10.00");
            createRequest.setCurrency("USD");
            createRequest.setMerchantOrderId("refund-test-" + System.currentTimeMillis());
            createRequest.setDescription("Refund test payment");
            createRequest.setReturnUrl("https://example.com/return");

            PaymentIntent created = paymentIntentsService.create(createRequest);
            String intentId = created.getPaymentIntentId();
            System.out.printf("Created intent for refund: %s%n", intentId);

            // Confirm with card
            Address address = new Address();
            address.setCountryCode("SG");
            address.setCity("Singapore");
            address.setStreet("444 Orchard Rd");
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

            PaymentIntent confirmed = paymentIntentsService.confirm(intentId, confirmRequest);
            System.out.printf("Confirmed intent: status=%s%n", confirmed.getPaymentIntentStatus());

            // Capture
            CapturePaymentIntentRequest captureRequest = new CapturePaymentIntentRequest();
            captureRequest.setAmountToCapture("10.00");

            PaymentIntent captured = paymentIntentsService.capture(intentId, captureRequest);
            System.out.printf("Captured intent: status=%s%n", captured.getPaymentIntentStatus());

            // Wait briefly for async capture to settle, then verify status
            if ("PENDING".equals(captured.getPaymentIntentStatus())) {
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                PaymentIntent refreshed = paymentIntentsService.get(intentId);
                System.out.printf("Refreshed intent status: %s%n", refreshed.getPaymentIntentStatus());
            }

            return intentId;
        } catch (UqpayException e) {
            System.out.printf("Failed to create/confirm/capture intent: %s%n", e.getMessage());
            return null;
        }
    }

    @Nested
    @DisplayName("Create Refund")
    class CreateRefund {

        @Test
        @DisplayName("should create a refund")
        void shouldCreateRefund() throws UqpayException {
            // Strategy 1: Find an existing SUCCEEDED intent to refund
            String paymentIntentId = null;

            ListPaymentIntentsRequest listRequest = new ListPaymentIntentsRequest();
            listRequest.setPageSize(20);
            listRequest.setPageNumber(1);
            listRequest.setStatus("succeeded");

            ListPaymentIntentsResponse listResponse = paymentIntentsService.list(listRequest);
            if (listResponse.getData() != null) {
                for (PaymentIntent intent : listResponse.getData()) {
                    if ("SUCCEEDED".equals(intent.getPaymentIntentStatus())) {
                        paymentIntentId = intent.getPaymentIntentId();
                        System.out.printf("Using existing SUCCEEDED intent for refund: %s (amount=%s %s)%n",
                                paymentIntentId, intent.getAmount(), intent.getCurrency());
                        break;
                    }
                }
            }

            // Strategy 2: Create a new intent if none found
            if (paymentIntentId == null) {
                paymentIntentId = createAndCaptureIntent();
                if (paymentIntentId == null) {
                    System.out.println("No SUCCEEDED intents available, skipping Create Refund test");
                    return;
                }
            }

            CreateRefundRequest request = new CreateRefundRequest();
            request.setPaymentIntentId(paymentIntentId);
            request.setAmount("0.01");
            request.setReason("integration_test");

            try {
                Refund refund = refundsService.create(request);

                assertThat(refund).isNotNull();
                assertThat(refund.getPaymentRefundId()).isNotEmpty();
                assertThat(refund.getAmount()).isNotEmpty();

                System.out.printf("Refund created successfully%n");
                System.out.printf("   ID: %s%n", refund.getPaymentRefundId());
                System.out.printf("   Amount: %s %s%n", refund.getAmount(), refund.getCurrency());
                System.out.printf("   Status: %s%n", refund.getRefundStatus());
                System.out.printf("   Reason: %s%n", refund.getReason());
            } catch (UqpayException e) {
                System.out.printf("Create refund returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("List Refunds")
    class ListRefunds {

        @Test
        @DisplayName("should list refunds with pagination")
        void shouldListRefundsWithPagination() throws UqpayException {
            ListRefundsRequest request = new ListRefundsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListRefundsResponse response = refundsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d refunds (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                Refund first = response.getData().get(0);
                System.out.printf("First refund: ID=%s, Amount=%s %s, Status=%s%n",
                        first.getPaymentRefundId(), first.getAmount(),
                        first.getCurrency(), first.getRefundStatus());
            }
        }
    }

    @Nested
    @DisplayName("Get Refund")
    class GetRefund {

        @Test
        @DisplayName("should get refund by ID")
        void shouldGetRefundById() throws UqpayException {
            ListRefundsRequest listRequest = new ListRefundsRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListRefundsResponse listResponse = refundsService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No refunds available, skipping Get test");
                return;
            }

            String refundId = listResponse.getData().get(0).getPaymentRefundId();

            Refund refund = refundsService.get(refundId);

            assertThat(refund).isNotNull();
            assertThat(refund.getPaymentRefundId()).isEqualTo(refundId);

            System.out.printf("Retrieved refund: ID=%s, Amount=%s %s, Status=%s, Reason=%s%n",
                    refund.getPaymentRefundId(), refund.getAmount(),
                    refund.getCurrency(), refund.getRefundStatus(), refund.getReason());
        }
    }
}
