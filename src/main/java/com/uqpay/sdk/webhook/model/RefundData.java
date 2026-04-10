package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefundData {

    // =========================================================================
    // Refund status constants
    // =========================================================================

    public static final String REFUND_STATUS_INITIATED = "INITIATED";
    public static final String REFUND_STATUS_PENDING = "PENDING";
    public static final String REFUND_STATUS_SUCCEEDED = "SUCCEEDED";
    public static final String REFUND_STATUS_FAILED = "FAILED";

    // =========================================================================
    // Refund reason constants
    // =========================================================================

    public static final String REFUND_REASON_REQUESTED_BY_CUSTOMER = "requested_by_customer";
    public static final String REFUND_REASON_DUPLICATE = "duplicate";
    public static final String REFUND_REASON_FRAUDULENT = "fraudulent";

    // =========================================================================
    // Fields
    // =========================================================================

    @JsonProperty("payment_refund_id")
    private String paymentRefundId;

    @JsonProperty("payment_intent_id")
    private String paymentIntentId;

    @JsonProperty("payment_attempt_id")
    private String paymentAttemptId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("refund_status")
    private String refundStatus;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("complete_time")
    private String completeTime;

    public RefundData() {
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    public String getPaymentRefundId() {
        return paymentRefundId;
    }

    public void setPaymentRefundId(String paymentRefundId) {
        this.paymentRefundId = paymentRefundId;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    public String getPaymentAttemptId() {
        return paymentAttemptId;
    }

    public void setPaymentAttemptId(String paymentAttemptId) {
        this.paymentAttemptId = paymentAttemptId;
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

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
}
