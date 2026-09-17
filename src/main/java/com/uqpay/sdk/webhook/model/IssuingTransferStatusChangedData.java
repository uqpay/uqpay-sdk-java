package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssuingTransferStatusChangedData {
    @JsonProperty("transfer_id")
    private String transferId;

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String value) {
        this.transferId = value;
    }

    @JsonProperty("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    @JsonProperty("previous_status")
    private String previousStatus;

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String value) {
        this.previousStatus = value;
    }

    @JsonProperty("source_account_id")
    private String sourceAccountId;

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String value) {
        this.sourceAccountId = value;
    }

    @JsonProperty("destination_account_id")
    private String destinationAccountId;

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(String value) {
        this.destinationAccountId = value;
    }

    @JsonProperty("currency")
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String value) {
        this.currency = value;
    }

    @JsonProperty("amount")
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String value) {
        this.amount = value;
    }

    @JsonProperty("remark")
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }

    @JsonProperty("succeeded_at")
    private String succeededAt;

    public String getSucceededAt() {
        return succeededAt;
    }

    public void setSucceededAt(String value) {
        this.succeededAt = value;
    }

    @JsonProperty("create_time")
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String value) {
        this.createTime = value;
    }

}
