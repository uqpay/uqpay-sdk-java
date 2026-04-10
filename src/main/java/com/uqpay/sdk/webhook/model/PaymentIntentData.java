package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentIntentData {

    // =========================================================================
    // Intent status constants
    // =========================================================================

    public static final String INTENT_STATUS_REQUIRES_PAYMENT_METHOD = "REQUIRES_PAYMENT_METHOD";
    public static final String INTENT_STATUS_REQUIRES_CONFIRMATION = "REQUIRES_CONFIRMATION";
    public static final String INTENT_STATUS_REQUIRES_ACTION = "REQUIRES_ACTION";
    public static final String INTENT_STATUS_PROCESSING = "PROCESSING";
    public static final String INTENT_STATUS_SUCCEEDED = "SUCCEEDED";
    public static final String INTENT_STATUS_CANCELED = "CANCELED";
    public static final String INTENT_STATUS_FAILED = "FAILED";

    // =========================================================================
    // Fields
    // =========================================================================

    @JsonProperty("payment_intent_id")
    private String paymentIntentId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("description")
    private String description;

    @JsonProperty("intent_status")
    private String intentStatus;

    @JsonProperty("merchant_order_id")
    private String merchantOrderId;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("payment_method")
    private PaymentMethodData paymentMethod;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("complete_time")
    private String completeTime;

    @JsonProperty("cancel_time")
    private String cancelTime;

    @JsonProperty("cancellation_reason")
    private String cancellationReason;

    public PaymentIntentData() {
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIntentStatus() {
        return intentStatus;
    }

    public void setIntentStatus(String intentStatus) {
        this.intentStatus = intentStatus;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Object getMetadata() {
        return metadata;
    }

    @SuppressWarnings("unchecked")
    public java.util.Map<String, String> getMetadataAsMap() {
        return (java.util.Map<String, String>) metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public PaymentMethodData getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodData paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
}
