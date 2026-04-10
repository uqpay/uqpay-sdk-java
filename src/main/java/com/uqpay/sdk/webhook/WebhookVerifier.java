package com.uqpay.sdk.webhook;

import com.uqpay.sdk.common.UqpayWebhookException;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Objects;

public final class WebhookVerifier {

    private static final String HMAC_SHA512 = "HmacSHA512";
    private static final long DEFAULT_TOLERANCE_SECONDS = 300;

    private final String secret;
    private final long toleranceSeconds;

    public WebhookVerifier(@NotNull String secret) {
        this(secret, DEFAULT_TOLERANCE_SECONDS);
    }

    public WebhookVerifier(@NotNull String secret, long toleranceSeconds) {
        this.secret = Objects.requireNonNull(secret, "secret must not be null");
        this.toleranceSeconds = toleranceSeconds;
    }

    @NotNull
    public Event verifyAndParse(@NotNull String payload, @NotNull String signatureHeader,
                                 @NotNull String timestampHeader) throws UqpayWebhookException {
        Objects.requireNonNull(payload, "payload must not be null");
        Objects.requireNonNull(signatureHeader, "signatureHeader must not be null");
        Objects.requireNonNull(timestampHeader, "timestampHeader must not be null");

        long timestamp;
        try {
            timestamp = Long.parseLong(timestampHeader);
        } catch (NumberFormatException e) {
            throw new UqpayWebhookException("Invalid webhook timestamp: " + timestampHeader);
        }

        long currentTime = System.currentTimeMillis() / 1000;
        if (Math.abs(currentTime - timestamp) > toleranceSeconds) {
            throw new UqpayWebhookException("Webhook timestamp is outside tolerance window");
        }

        String expectedSignature;
        try {
            Mac mac = Mac.getInstance(HMAC_SHA512);
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA512);
            mac.init(keySpec);
            byte[] hash = mac.doFinal((payload + timestampHeader).getBytes(StandardCharsets.UTF_8));
            expectedSignature = bytesToHex(hash);
        } catch (Exception e) {
            throw new UqpayWebhookException("Failed to compute webhook signature", e);
        }

        if (!MessageDigest.isEqual(expectedSignature.getBytes(StandardCharsets.UTF_8),
                signatureHeader.getBytes(StandardCharsets.UTF_8))) {
            throw new UqpayWebhookException("Webhook signature verification failed");
        }

        try {
            return Event.fromJson(payload);
        } catch (Exception e) {
            throw new UqpayWebhookException("Failed to parse webhook event", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
