package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transfer {

    @JsonProperty("transfer_id")
    private String transferId; // Required. Unique identifier for the transfer. UUID format.

    @JsonProperty("short_reference_id")
    private String shortReferenceId; // Required. Abbreviated system-generated reference (e.g., P220406-LLCVLRM).

    @JsonProperty("reference_id")
    private String referenceId; // Required. System-generated reference for entity identification.

    @JsonProperty("source_account_name")
    private String sourceAccountName; // Required. Name of the account from which funds are transferred.

    @JsonProperty("destination_account_name")
    private String destinationAccountName; // Required. Name of the account receiving the transferred funds.

    @JsonProperty("transfer_currency")
    private String transferCurrency; // Required. Currency of the transfer. ISO 4217 (e.g., USD).

    @JsonProperty("transfer_amount")
    private String transferAmount; // Required. The amount of money transferred.

    @JsonProperty("transfer_status")
    private String transferStatus; // Required. Current status of the transfer. Allowed: completed, failed.

    @JsonProperty("create_time")
    private String createTime; // Required. Timestamp when the request was initiated. ISO 8601.

    @JsonProperty("complete_time")
    private String completeTime; // Required. Timestamp when the request was successfully processed. ISO 8601.

    @JsonProperty("created_by")
    private String createdBy; // Required. Identifier of the user or system that initiated the transfer.

    public Transfer() {
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getShortReferenceId() {
        return shortReferenceId;
    }

    public void setShortReferenceId(String shortReferenceId) {
        this.shortReferenceId = shortReferenceId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getSourceAccountName() {
        return sourceAccountName;
    }

    public void setSourceAccountName(String sourceAccountName) {
        this.sourceAccountName = sourceAccountName;
    }

    public String getDestinationAccountName() {
        return destinationAccountName;
    }

    public void setDestinationAccountName(String destinationAccountName) {
        this.destinationAccountName = destinationAccountName;
    }

    public String getTransferCurrency() {
        return transferCurrency;
    }

    public void setTransferCurrency(String transferCurrency) {
        this.transferCurrency = transferCurrency;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
