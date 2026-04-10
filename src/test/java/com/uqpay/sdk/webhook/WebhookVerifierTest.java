package com.uqpay.sdk.webhook;

import com.uqpay.sdk.common.UqpayWebhookException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("WebhookVerifier")
class WebhookVerifierTest {

    private static final String TEST_SECRET = "test-webhook-secret-key";

    private static final String VALID_PAYLOAD = "{"
            + "\"version\":\"V1.6.0\","
            + "\"event_name\":\"DEPOSIT\","
            + "\"event_type\":\"deposit.completed\","
            + "\"event_id\":\"evt_verify_1\","
            + "\"data\":{\"deposit_id\":\"dep_001\",\"currency\":\"USD\",\"amount\":\"100.00\",\"deposit_status\":\"COMPLETED\"}"
            + "}";

    /**
     * Compute HMAC-SHA512(secret, payload + timestamp) in lowercase hex — same
     * algorithm used by {@link WebhookVerifier}.
     */
    private static String computeSignature(String payload, String timestamp, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        mac.init(keySpec);
        byte[] hash = mac.doFinal((payload + timestamp).getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /** Returns a timestamp string that is within the default 300-second tolerance. */
    private static String currentTimestamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    // =========================================================================
    // Successful verification
    // =========================================================================

    @Nested
    @DisplayName("Successful verification")
    class SuccessfulVerificationTests {

        @Test
        @DisplayName("should return parsed Event when signature and timestamp are valid")
        void shouldReturnParsedEventOnValidSignature() throws Exception {
            WebhookVerifier verifier = new WebhookVerifier(TEST_SECRET);
            String timestamp = currentTimestamp();
            String signature = computeSignature(VALID_PAYLOAD, timestamp, TEST_SECRET);

            Event event = verifier.verifyAndParse(VALID_PAYLOAD, signature, timestamp);

            assertThat(event).isNotNull();
            assertThat(event.getEventId()).isEqualTo("evt_verify_1");
            assertThat(event.getEventName()).isEqualTo("DEPOSIT");
            assertThat(event.getEventType()).isEqualTo("deposit.completed");
            assertThat(event.getVersion()).isEqualTo("V1.6.0");
        }

        @Test
        @DisplayName("should work with a custom tolerance")
        void shouldWorkWithCustomTolerance() throws Exception {
            // Use a 600-second custom tolerance — still within range for current time
            WebhookVerifier verifier = new WebhookVerifier(TEST_SECRET, 600);
            String timestamp = currentTimestamp();
            String signature = computeSignature(VALID_PAYLOAD, timestamp, TEST_SECRET);

            Event event = verifier.verifyAndParse(VALID_PAYLOAD, signature, timestamp);

            assertThat(event.getEventId()).isEqualTo("evt_verify_1");
        }
    }

    // =========================================================================
    // Invalid signature
    // =========================================================================

    @Nested
    @DisplayName("Invalid signature")
    class InvalidSignatureTests {

        @Test
        @DisplayName("should throw UqpayWebhookException when signature does not match")
        void shouldThrowOnWrongSignature() {
            WebhookVerifier verifier = new WebhookVerifier(TEST_SECRET);
            String timestamp = currentTimestamp();

            assertThatThrownBy(() ->
                    verifier.verifyAndParse(VALID_PAYLOAD, "deadbeefdeadbeef", timestamp))
                    .isInstanceOf(UqpayWebhookException.class)
                    .hasMessageContaining("signature verification failed");
        }

        @Test
        @DisplayName("should throw UqpayWebhookException when signature is computed with wrong secret")
        void shouldThrowOnSignatureWithWrongSecret() throws Exception {
            WebhookVerifier verifier = new WebhookVerifier(TEST_SECRET);
            String timestamp = currentTimestamp();
            // Compute signature using a different secret — should not match
            String wrongSignature = computeSignature(VALID_PAYLOAD, timestamp, "wrong-secret");

            assertThatThrownBy(() ->
                    verifier.verifyAndParse(VALID_PAYLOAD, wrongSignature, timestamp))
                    .isInstanceOf(UqpayWebhookException.class)
                    .hasMessageContaining("signature verification failed");
        }
    }

    // =========================================================================
    // Expired timestamp
    // =========================================================================

    @Nested
    @DisplayName("Expired timestamp")
    class ExpiredTimestampTests {

        @Test
        @DisplayName("should throw UqpayWebhookException when timestamp is beyond tolerance")
        void shouldThrowWhenTimestampExpired() throws Exception {
            WebhookVerifier verifier = new WebhookVerifier(TEST_SECRET, 300);
            // Timestamp 10 minutes (600 seconds) in the past — outside 300 s window
            String oldTimestamp = String.valueOf(System.currentTimeMillis() / 1000 - 600);
            String signature = computeSignature(VALID_PAYLOAD, oldTimestamp, TEST_SECRET);

            assertThatThrownBy(() ->
                    verifier.verifyAndParse(VALID_PAYLOAD, signature, oldTimestamp))
                    .isInstanceOf(UqpayWebhookException.class)
                    .hasMessageContaining("outside tolerance window");
        }

        @Test
        @DisplayName("should throw UqpayWebhookException when timestamp is far in the future")
        void shouldThrowWhenTimestampIsFuture() throws Exception {
            WebhookVerifier verifier = new WebhookVerifier(TEST_SECRET, 300);
            // Timestamp 10 minutes ahead — outside tolerance
            String futureTimestamp = String.valueOf(System.currentTimeMillis() / 1000 + 600);
            String signature = computeSignature(VALID_PAYLOAD, futureTimestamp, TEST_SECRET);

            assertThatThrownBy(() ->
                    verifier.verifyAndParse(VALID_PAYLOAD, signature, futureTimestamp))
                    .isInstanceOf(UqpayWebhookException.class)
                    .hasMessageContaining("outside tolerance window");
        }
    }

    // =========================================================================
    // Invalid timestamp format
    // =========================================================================

    @Nested
    @DisplayName("Invalid timestamp format")
    class InvalidTimestampFormatTests {

        @Test
        @DisplayName("should throw UqpayWebhookException for non-numeric timestamp")
        void shouldThrowOnNonNumericTimestamp() {
            WebhookVerifier verifier = new WebhookVerifier(TEST_SECRET);

            assertThatThrownBy(() ->
                    verifier.verifyAndParse(VALID_PAYLOAD, "any-sig", "not-a-number"))
                    .isInstanceOf(UqpayWebhookException.class)
                    .hasMessageContaining("Invalid webhook timestamp");
        }

        @Test
        @DisplayName("should throw UqpayWebhookException for empty timestamp")
        void shouldThrowOnEmptyTimestamp() {
            WebhookVerifier verifier = new WebhookVerifier(TEST_SECRET);

            assertThatThrownBy(() ->
                    verifier.verifyAndParse(VALID_PAYLOAD, "any-sig", ""))
                    .isInstanceOf(UqpayWebhookException.class)
                    .hasMessageContaining("Invalid webhook timestamp");
        }

        @Test
        @DisplayName("should throw UqpayWebhookException for timestamp with decimal point")
        void shouldThrowOnDecimalTimestamp() {
            WebhookVerifier verifier = new WebhookVerifier(TEST_SECRET);

            assertThatThrownBy(() ->
                    verifier.verifyAndParse(VALID_PAYLOAD, "any-sig", "1700000000.123"))
                    .isInstanceOf(UqpayWebhookException.class)
                    .hasMessageContaining("Invalid webhook timestamp");
        }
    }

    // =========================================================================
    // Invalid JSON payload
    // =========================================================================

    @Nested
    @DisplayName("Invalid JSON payload")
    class InvalidJsonPayloadTests {

        @Test
        @DisplayName("should throw UqpayWebhookException when payload is not valid JSON")
        void shouldThrowOnInvalidJsonPayload() throws Exception {
            WebhookVerifier verifier = new WebhookVerifier(TEST_SECRET);
            String invalidPayload = "this is not json";
            String timestamp = currentTimestamp();
            // Signature is computed over the invalid payload — so it passes HMAC check
            String signature = computeSignature(invalidPayload, timestamp, TEST_SECRET);

            assertThatThrownBy(() ->
                    verifier.verifyAndParse(invalidPayload, signature, timestamp))
                    .isInstanceOf(UqpayWebhookException.class)
                    .hasMessageContaining("Failed to parse webhook event");
        }

        @Test
        @DisplayName("should throw UqpayWebhookException when payload is an empty string signed correctly")
        void shouldThrowOnEmptyJsonPayload() throws Exception {
            WebhookVerifier verifier = new WebhookVerifier(TEST_SECRET);
            String emptyPayload = "";
            String timestamp = currentTimestamp();
            String signature = computeSignature(emptyPayload, timestamp, TEST_SECRET);

            assertThatThrownBy(() ->
                    verifier.verifyAndParse(emptyPayload, signature, timestamp))
                    .isInstanceOf(UqpayWebhookException.class)
                    .hasMessageContaining("Failed to parse webhook event");
        }
    }
}
