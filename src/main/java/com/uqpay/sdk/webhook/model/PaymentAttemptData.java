package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentAttemptData {

    // =========================================================================
    // Attempt status constants
    // =========================================================================

    public static final String ATTEMPT_STATUS_INITIATED = "INITIATED";
    public static final String ATTEMPT_STATUS_PENDING = "PENDING";
    public static final String ATTEMPT_STATUS_CAPTURE_REQUESTED = "CAPTURE_REQUESTED";
    public static final String ATTEMPT_STATUS_SUCCEEDED = "SUCCEEDED";
    public static final String ATTEMPT_STATUS_FAILED = "FAILED";
    public static final String ATTEMPT_STATUS_CANCELED = "CANCELED";

    // =========================================================================
    // Fields
    // =========================================================================

    @JsonProperty("payment_attempt_id")
    private String paymentAttemptId;

    @JsonProperty("payment_intent_id")
    private String paymentIntentId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("attempt_status")
    private String attemptStatus;

    @JsonProperty("merchant_order_id")
    private String merchantOrderId;

    @JsonProperty("payment_method")
    private PaymentMethodData paymentMethod;

    @JsonProperty("captured_amount")
    private String capturedAmount;

    @JsonProperty("refunded_amount")
    private String refundedAmount;

    @JsonProperty("failure_code")
    private String failureCode;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("complete_time")
    private String completeTime;

    @JsonProperty("cancel_time")
    private String cancelTime;

    @JsonProperty("cancellation_reason")
    private String cancellationReason;

    public PaymentAttemptData() {
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    public String getPaymentAttemptId() {
        return paymentAttemptId;
    }

    public void setPaymentAttemptId(String paymentAttemptId) {
        this.paymentAttemptId = paymentAttemptId;
    }

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

    public String getAttemptStatus() {
        return attemptStatus;
    }

    public void setAttemptStatus(String attemptStatus) {
        this.attemptStatus = attemptStatus;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public PaymentMethodData getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodData paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCapturedAmount() {
        return capturedAmount;
    }

    public void setCapturedAmount(String capturedAmount) {
        this.capturedAmount = capturedAmount;
    }

    public String getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(String refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
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
