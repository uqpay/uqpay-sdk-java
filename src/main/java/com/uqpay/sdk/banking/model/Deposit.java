package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Deposit {

    @JsonProperty("deposit_id")
    private String depositId; // Required. Unique identifier for the deposit. UUID format.

    @JsonProperty("short_reference_id")
    private String shortReferenceId; // Required. System-generated reference code (e.g., P220406-LLCVLRM).

    @JsonProperty("currency")
    private String currency; // Required. Three-letter currency code. ISO 4217 (e.g., USD).

    @JsonProperty("amount")
    private String amount; // Required. Gross amount before any fees are deducted.

    @JsonProperty("deposit_status")
    private String depositStatus; // Required. Processing state of the deposit. Allowed: PENDING, COMPLETED, FAILED.

    @JsonProperty("create_time")
    private String createTime; // Optional. Timestamp when the record was created. ISO 8601.

    @JsonProperty("complete_time")
    private String completeTime; // Required. Timestamp when processed and marked COMPLETED. ISO 8601.

    @JsonProperty("deposit_fee")
    private String depositFee; // Required. Fee amount charged for the deposit.

    @JsonProperty("deposit_reference")
    private String depositReference; // Optional. Depositor-provided reference identifier.

    @JsonProperty("receiver_account_number")
    private String receiverAccountNumber; // Required. Destination account number. Alphanumeric, max 60 chars.

    @JsonProperty("sender")
    private DepositSender sender; // Optional. Sender details (name, country, account number, SWIFT code).

    public Deposit() {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getDepositFee() {
        return depositFee;
    }

    public void setDepositFee(String depositFee) {
        this.depositFee = depositFee;
    }

    public String getDepositReference() {
        return depositReference;
    }

    public void setDepositReference(String depositReference) {
        this.depositReference = depositReference;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public DepositSender getSender() {
        return sender;
    }

    public void setSender(DepositSender sender) {
        this.sender = sender;
    }
}
