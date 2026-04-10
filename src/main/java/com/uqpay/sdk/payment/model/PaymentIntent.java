package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentIntent {

    @JsonProperty("payment_intent_id")
    private String paymentIntentId;

    @JsonProperty("amount")
    private String amount; // order amount to charge

    @JsonProperty("currency")
    private String currency; // ISO 4217 three-letter code

    @JsonProperty("intent_status")
    private String paymentIntentStatus; // REQUIRES_PAYMENT_METHOD | REQUIRES_CUSTOMER_ACTION | REQUIRES_CAPTURE | PENDING | SUCCEEDED | CANCELLED | FAILED

    @JsonProperty("merchant_order_id")
    private String merchantOrderId;

    @JsonProperty("description")
    private String description; // max 32 chars

    @JsonProperty("return_url")
    private String returnUrl; // redirect URL or app scheme URI after authentication

    @JsonProperty("metadata")
    private Object metadata; // key-value JSON object, max 512 bytes

    @JsonProperty("available_payment_method_types")
    private List<String> availablePaymentMethodTypes;

    @JsonProperty("captured_amount")
    private String capturedAmount;

    @JsonProperty("client_secret")
    private String clientSecret; // for browser or app use, valid for 60 minutes

    @JsonProperty("cancellation_reason")
    private String cancellationReason; // duplicate, fraudulent, requested_by_customer, or abandoned

    @JsonProperty("latest_payment_attempt")
    private Object latestPaymentAttempt;

    @JsonProperty("next_action")
    private Object nextAction; // actions required to fulfill payment

    @JsonProperty("create_time")
    private String createTime; // ISO 8601

    @JsonProperty("update_time")
    private String updateTime; // ISO 8601

    @JsonProperty("cancel_time")
    private String cancelTime; // ISO 8601, present only when status is CANCELLED

    @JsonProperty("complete_time")
    private String completeTime; // ISO 8601, when intent reached final state

    public PaymentIntent() {
    }

    public String getPaymentIntentId() { return paymentIntentId; }
    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getPaymentIntentStatus() { return paymentIntentStatus; }
    public void setPaymentIntentStatus(String paymentIntentStatus) { this.paymentIntentStatus = paymentIntentStatus; }

    public String getMerchantOrderId() { return merchantOrderId; }
    public void setMerchantOrderId(String merchantOrderId) { this.merchantOrderId = merchantOrderId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReturnUrl() { return returnUrl; }
    public void setReturnUrl(String returnUrl) { this.returnUrl = returnUrl; }

    @SuppressWarnings("unchecked")
    public Map<String, String> getMetadata() {
        if (metadata instanceof Map) {
            return (Map<String, String>) metadata;
        }
        return Collections.emptyMap();
    }

    public void setMetadata(Object metadata) { this.metadata = metadata; }

    public List<String> getAvailablePaymentMethodTypes() { return availablePaymentMethodTypes; }
    public void setAvailablePaymentMethodTypes(List<String> availablePaymentMethodTypes) { this.availablePaymentMethodTypes = availablePaymentMethodTypes; }

    public String getCapturedAmount() { return capturedAmount; }
    public void setCapturedAmount(String capturedAmount) { this.capturedAmount = capturedAmount; }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }

    public Object getLatestPaymentAttempt() { return latestPaymentAttempt; }
    public void setLatestPaymentAttempt(Object latestPaymentAttempt) { this.latestPaymentAttempt = latestPaymentAttempt; }

    public Object getNextAction() { return nextAction; }
    public void setNextAction(Object nextAction) { this.nextAction = nextAction; }

    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getUpdateTime() { return updateTime; }
    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }

    public String getCancelTime() { return cancelTime; }
    public void setCancelTime(String cancelTime) { this.cancelTime = cancelTime; }

    public String getCompleteTime() { return completeTime; }
    public void setCompleteTime(String completeTime) { this.completeTime = completeTime; }
}
