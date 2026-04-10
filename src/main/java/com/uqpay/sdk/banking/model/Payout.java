package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payout {

    @JsonProperty("payout_id")
    private String payoutId; // Required. UUID of the payout

    @JsonProperty("short_reference_id")
    private String shortReferenceId; // Required. System-generated reference, e.g. "P220406-LLCVLRM"

    @JsonProperty("payout_currency")
    private String payoutCurrency; // Required. ISO 4217 currency code the beneficiary receives

    @JsonProperty("payout_amount")
    private String payoutAmount; // Required. Amount sent in payout currency

    @JsonProperty("fee_paid_by")
    private String feePaidBy; // Required. Fee responsibility: OURS

    @JsonProperty("fee_currency")
    private String feeCurrency; // Required. ISO 4217 currency code for the fee

    @JsonProperty("fee_amount")
    private String feeAmount; // Required. Fee amount charged for the payout

    @JsonProperty("purpose_code")
    private String purposeCode; // Required. Standardized purpose category, e.g. WAGES_SALARY, GOODS_PURCHASED

    @JsonProperty("payout_reference")
    private String payoutReference; // Required. Bank payment reference shown in beneficiary's bank records, max 100 chars

    @JsonProperty("payout_date")
    private String payoutDate; // Required. Payment submission date, format: YYYY-MM-DD

    @JsonProperty("payout_status")
    private String payoutStatus; // Required. READY_TO_SEND, PENDING, REJECTED, FAILED, or COMPLETED

    @JsonProperty("create_time")
    private String createTime; // Required. Record creation timestamp, ISO 8601

    @JsonProperty("complete_time")
    private String completeTime; // Optional. Completion timestamp, ISO 8601; populated when payout_status is COMPLETED

    @JsonProperty("failure_reason")
    private String failureReason; // Optional. Explanation of failure; populated when payout fails

    @JsonProperty("unique_request_id")
    private String uniqueRequestId; // Required. Client-provided unique identifier (UUID) from the POST request

    @JsonProperty("payout_reason")
    private String payoutReason; // Optional. Custom payout description, max 200 chars

    @JsonProperty("update_time")
    private String updateTime; // Required. Last modification timestamp, ISO 8601

    @JsonProperty("failure_returned_amount")
    private String failureReturnedAmount; // Optional. Amount returned for failed payouts

    @JsonProperty("quote_id")
    private String quoteId; // Optional. UUID of a pre-created cross-currency quote

    @JsonProperty("conversion")
    private PayoutConversion conversion; // Optional. Currency conversion details (currency pair and rate)

    public Payout() {
    }

    public String getPayoutId() {
        return payoutId;
    }

    public void setPayoutId(String payoutId) {
        this.payoutId = payoutId;
    }

    public String getShortReferenceId() {
        return shortReferenceId;
    }

    public void setShortReferenceId(String shortReferenceId) {
        this.shortReferenceId = shortReferenceId;
    }

    public String getPayoutCurrency() {
        return payoutCurrency;
    }

    public void setPayoutCurrency(String payoutCurrency) {
        this.payoutCurrency = payoutCurrency;
    }

    public String getPayoutAmount() {
        return payoutAmount;
    }

    public void setPayoutAmount(String payoutAmount) {
        this.payoutAmount = payoutAmount;
    }

    public String getFeePaidBy() {
        return feePaidBy;
    }

    public void setFeePaidBy(String feePaidBy) {
        this.feePaidBy = feePaidBy;
    }

    public String getFeeCurrency() {
        return feeCurrency;
    }

    public void setFeeCurrency(String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getPayoutReference() {
        return payoutReference;
    }

    public void setPayoutReference(String payoutReference) {
        this.payoutReference = payoutReference;
    }

    public String getPayoutDate() {
        return payoutDate;
    }

    public void setPayoutDate(String payoutDate) {
        this.payoutDate = payoutDate;
    }

    public String getPayoutStatus() {
        return payoutStatus;
    }

    public void setPayoutStatus(String payoutStatus) {
        this.payoutStatus = payoutStatus;
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

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getUniqueRequestId() {
        return uniqueRequestId;
    }

    public void setUniqueRequestId(String uniqueRequestId) {
        this.uniqueRequestId = uniqueRequestId;
    }

    public String getPayoutReason() {
        return payoutReason;
    }

    public void setPayoutReason(String payoutReason) {
        this.payoutReason = payoutReason;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFailureReturnedAmount() {
        return failureReturnedAmount;
    }

    public void setFailureReturnedAmount(String failureReturnedAmount) {
        this.failureReturnedAmount = failureReturnedAmount;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public PayoutConversion getConversion() {
        return conversion;
    }

    public void setConversion(PayoutConversion conversion) {
        this.conversion = conversion;
    }
}
