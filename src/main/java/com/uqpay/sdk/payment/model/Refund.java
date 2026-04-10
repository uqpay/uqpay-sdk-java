package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Refund {

    @JsonProperty("payment_refund_id")
    private String paymentRefundId; // Required. Unique identifier for the refund

    @JsonProperty("payment_attempt_id")
    private String paymentAttemptId; // Required. The ID of the payment attempt that was refunded

    @JsonProperty("amount")
    private String amount; // Required. The refunded amount

    @JsonProperty("currency")
    private String currency; // Required. ISO 4217 three-letter currency code

    @JsonProperty("refund_status")
    private String refundStatus; // Required. INITIATED, PROCESSING, SUCCEEDED, FAILED, REVERSAL_INITIATED, REVERSAL_PROCESSING, REVERSAL_SUCCEEDED

    @JsonProperty("reason")
    private String reason; // Optional. The reason for the refund

    @JsonProperty("metadata")
    private Object metadata; // Optional. Custom key-value pairs

    @JsonProperty("create_time")
    private String createTime; // Required. ISO 8601 datetime, when refund was initiated

    @JsonProperty("update_time")
    private String updateTime; // Required. ISO 8601 datetime, last modification timestamp

    public Refund() {
    }

    public String getPaymentRefundId() { return paymentRefundId; }
    public void setPaymentRefundId(String paymentRefundId) { this.paymentRefundId = paymentRefundId; }

    public String getPaymentAttemptId() { return paymentAttemptId; }
    public void setPaymentAttemptId(String paymentAttemptId) { this.paymentAttemptId = paymentAttemptId; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getRefundStatus() { return refundStatus; }
    public void setRefundStatus(String refundStatus) { this.refundStatus = refundStatus; }

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

    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getUpdateTime() { return updateTime; }
    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }
}
