package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateRefundRequest {

    @JsonProperty("payment_intent_id")
    private String paymentIntentId; // Required. The ID of the payment intent to refund

    @JsonProperty("amount")
    private String amount; // Required. The amount to refund

    @JsonProperty("reason")
    private String reason; // Required. Explanation for the refund, max 100 chars

    @JsonProperty("metadata")
    private Object metadata; // Optional. Custom key-value pairs

    @JsonProperty("payment_attempt_id")
    private String paymentAttemptId; // Optional. The ID of the payment attempt to refund

    public CreateRefundRequest() {
    }

    public String getPaymentIntentId() { return paymentIntentId; }
    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    @SuppressWarnings("unchecked")
    public Map<String, String> getMetadata() {
        if (metadata instanceof Map) {
            return (Map<String, String>) metadata;
        }
        return Collections.emptyMap();
    }

    public void setMetadata(Object metadata) { this.metadata = metadata; }

    public String getPaymentAttemptId() { return paymentAttemptId; }
    public void setPaymentAttemptId(String paymentAttemptId) { this.paymentAttemptId = paymentAttemptId; }
}
