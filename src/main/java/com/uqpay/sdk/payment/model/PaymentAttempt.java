package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentAttempt {

    @JsonProperty("attempt_id")
    private String attemptId; // Required. UUID, unique identifier for the attempt

    @JsonProperty("amount")
    private String amount; // Required. Amount of the PaymentAttempt

    @JsonProperty("currency")
    private String currency; // Required. ISO 4217 three-letter currency code

    @JsonProperty("captured_amount")
    private String capturedAmount; // Optional. Funds successfully captured from authorization

    @JsonProperty("refunded_amount")
    private String refundedAmount; // Optional. Funds returned to customer

    @JsonProperty("attempt_status")
    private String attemptStatus; // Required. INITIATED, AUTHENTICATION_REDIRECTED, PENDING_AUTHORIZATION, AUTHORIZED, CAPTURE_REQUESTED, SETTLED, SUCCEEDED, CANCELLED, EXPIRED, FAILED

    @JsonProperty("cancellation_reason")
    private String cancellationReason; // Optional. Reason if transaction was cancelled

    @JsonProperty("failure_code")
    private String failureCode; // Optional. Error code for failed attempts

    @JsonProperty("payment_method")
    private Object paymentMethod; // changed from String to Object

    @JsonProperty("create_time")
    private String createTime; // Optional. ISO 8601 datetime, when attempt was created

    @JsonProperty("update_time")
    private String updateTime; // Optional. ISO 8601 datetime, last update time

    @JsonProperty("complete_time")
    private String completeTime; // Optional. ISO 8601 datetime, when attempt reached final state

    @JsonProperty("auth_code")
    private String authCode;

    @JsonProperty("arn")
    private String arn;

    @JsonProperty("rrn")
    private String rrn;

    @JsonProperty("advice_code")
    private String adviceCode;

    @JsonProperty("authentication_data")
    private AuthenticationData authenticationData;

    public PaymentAttempt() {
    }

    public String getAttemptId() { return attemptId; }
    public void setAttemptId(String attemptId) { this.attemptId = attemptId; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getCapturedAmount() { return capturedAmount; }
    public void setCapturedAmount(String capturedAmount) { this.capturedAmount = capturedAmount; }

    public String getRefundedAmount() { return refundedAmount; }
    public void setRefundedAmount(String refundedAmount) { this.refundedAmount = refundedAmount; }

    public String getAttemptStatus() { return attemptStatus; }
    public void setAttemptStatus(String attemptStatus) { this.attemptStatus = attemptStatus; }

    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }

    public String getFailureCode() { return failureCode; }
    public void setFailureCode(String failureCode) { this.failureCode = failureCode; }

    public Object getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(Object paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getUpdateTime() { return updateTime; }
    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }

    public String getCompleteTime() { return completeTime; }
    public void setCompleteTime(String completeTime) { this.completeTime = completeTime; }

    public String getAuthCode() { return authCode; }
    public void setAuthCode(String authCode) { this.authCode = authCode; }

    public String getArn() { return arn; }
    public void setArn(String arn) { this.arn = arn; }

    public String getRrn() { return rrn; }
    public void setRrn(String rrn) { this.rrn = rrn; }

    public String getAdviceCode() { return adviceCode; }
    public void setAdviceCode(String adviceCode) { this.adviceCode = adviceCode; }

    public AuthenticationData getAuthenticationData() { return authenticationData; }
    public void setAuthenticationData(AuthenticationData authenticationData) { this.authenticationData = authenticationData; }
}
