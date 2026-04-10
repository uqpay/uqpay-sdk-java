package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payout {

    @JsonProperty("payout_id")
    private String payoutId; // Required. Payout UUID identifier

    @JsonProperty("payout_amount")
    private String payoutAmount; // Required. Amount to be withdrawn

    @JsonProperty("payout_currency")
    private String payoutCurrency; // Required. ISO 4217 three-letter currency code

    @JsonProperty("payout_status")
    private String payoutStatus; // Required. INITIATED, PROCESSING, COMPLETED, FAILED, FAILED_REFUNDED

    @JsonProperty("internal_note")
    private String internalNote; // Optional. Internal payout remark information

    @JsonProperty("statement_descriptor")
    private String statementDescriptor; // Optional. Reference displayed on recipient's bank statement, max 15 chars

    @JsonProperty("create_time")
    private String createTime; // Required. ISO 8601 datetime, payout creation timestamp

    @JsonProperty("completed_time")
    private String completedTime; // Optional. ISO 8601 datetime, payout completion timestamp

    public Payout() {
    }

    public String getPayoutId() { return payoutId; }
    public void setPayoutId(String payoutId) { this.payoutId = payoutId; }

    public String getPayoutAmount() { return payoutAmount; }
    public void setPayoutAmount(String payoutAmount) { this.payoutAmount = payoutAmount; }

    public String getPayoutCurrency() { return payoutCurrency; }
    public void setPayoutCurrency(String payoutCurrency) { this.payoutCurrency = payoutCurrency; }

    public String getPayoutStatus() { return payoutStatus; }
    public void setPayoutStatus(String payoutStatus) { this.payoutStatus = payoutStatus; }

    public String getInternalNote() { return internalNote; }
    public void setInternalNote(String internalNote) { this.internalNote = internalNote; }

    public String getStatementDescriptor() { return statementDescriptor; }
    public void setStatementDescriptor(String statementDescriptor) { this.statementDescriptor = statementDescriptor; }

    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getCompletedTime() { return completedTime; }
    public void setCompletedTime(String completedTime) { this.completedTime = completedTime; }
}
