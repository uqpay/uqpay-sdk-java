package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssuingTransfer {

    @JsonProperty("transfer_id")
    private String transferId; // Required. Unique identifier for the transfer (UUID)

    @JsonProperty("reference_id")
    private String referenceId; // Required. Short reference id for the transfer, e.g. "T250624-K0XMYZRF"

    @JsonProperty("source_account_id")
    private String sourceAccountId; // Required. UUID of the account that initiated the transfer

    @JsonProperty("destination_account_id")
    private String destinationAccountId; // Required. UUID of the account that received the transfer

    @JsonProperty("amount")
    private String amount; // Required. Transfer amount

    @JsonProperty("fee_amount")
    private String feeAmount; // Required. Transaction fee amount

    @JsonProperty("currency")
    private String currency; // Required. Transfer currency, e.g. "SGD"

    @JsonProperty("transfer_status")
    private String transferStatus; // Required. pending, failed, or completed

    @JsonProperty("create_time")
    private String createTime; // Required. Transfer create time, ISO 8601 format

    @JsonProperty("complete_time")
    private String completeTime; // Required. Transfer complete time, ISO 8601 format

    @JsonProperty("creator_id")
    private String creatorId; // Required. UUID of the account that created the transfer

    @JsonProperty("remark")
    private String remark; // Required. Transfer remark

    @JsonProperty("short_transaction_id")
    private String shortTransactionId;

    public IssuingTransfer() {
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(String destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShortTransactionId() {
        return shortTransactionId;
    }

    public void setShortTransactionId(String shortTransactionId) {
        this.shortTransactionId = shortTransactionId;
    }
}
