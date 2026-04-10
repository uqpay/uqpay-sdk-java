package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepositEventData {

    @JsonProperty("deposit_id")
    private String depositId;

    @JsonProperty("short_reference_id")
    private String shortReferenceId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("deposit_status")
    private String depositStatus;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("complete_time")
    private String completeTime;

    @JsonProperty("receiver_account_number")
    private String receiverAccountNumber;

    @JsonProperty("sender_name")
    private String senderName;

    @JsonProperty("sender_country")
    private String senderCountry;

    @JsonProperty("sender_account_number")
    private String senderAccountNumber;

    @JsonProperty("sender_swift_code")
    private String senderSwiftCode;

    public DepositEventData() {
    }

    public String getDepositId() {
        return depositId;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }

    public String getShortReferenceId() {
        return shortReferenceId;
    }

    public void setShortReferenceId(String shortReferenceId) {
        this.shortReferenceId = shortReferenceId;
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

    public String getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
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

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderCountry() {
        return senderCountry;
    }

    public void setSenderCountry(String senderCountry) {
        this.senderCountry = senderCountry;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getSenderSwiftCode() {
        return senderSwiftCode;
    }

    public void setSenderSwiftCode(String senderSwiftCode) {
        this.senderSwiftCode = senderSwiftCode;
    }
}
