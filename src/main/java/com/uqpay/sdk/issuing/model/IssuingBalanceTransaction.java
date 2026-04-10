package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssuingBalanceTransaction {

    @JsonProperty("transaction_id")
    private String transactionId; // Required. Unique identifier for the transaction (UUID)

    @JsonProperty("short_transaction_id")
    private String shortTransactionId; // Required. Short unique identifier for the transaction (UUID)

    @JsonProperty("account_id")
    private String accountId; // Required. The account ID associated with this transaction (UUID)

    @JsonProperty("balance_id")
    private String balanceId; // Required. The account balance ID associated with this transaction (UUID)

    @JsonProperty("transaction_type")
    private String transactionType; // Required. E.g. DEPOSIT, TRANSFER_IN, TRANSFER_OUT, ISSUING_AUTHORIZATION, ISSUING_REVERSAL, FEE, REFUND, ADJUSTMENT, etc.

    @JsonProperty("currency")
    private String currency; // Required. Three-letter ISO 4217 currency code

    @JsonProperty("amount")
    private String amount; // Required. Transaction amount

    @JsonProperty("create_time")
    private String createTime; // Required. ISO 8601 timestamp

    @JsonProperty("complete_time")
    private String completeTime; // Required. ISO 8601 timestamp of completion

    @JsonProperty("transaction_status")
    private String transactionStatus; // Required. COMPLETED, PENDING, or FAILED

    @JsonProperty("ending_balance")
    private String endingBalance; // Required. Balance after the transaction

    @JsonProperty("description")
    private String description; // Required. Description of the transaction

    @JsonProperty("account_name")
    private String accountName; // Required. Name of the account associated with this transaction

    public IssuingBalanceTransaction() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getShortTransactionId() {
        return shortTransactionId;
    }

    public void setShortTransactionId(String shortTransactionId) {
        this.shortTransactionId = shortTransactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(String balanceId) {
        this.balanceId = balanceId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getEndingBalance() {
        return endingBalance;
    }

    public void setEndingBalance(String endingBalance) {
        this.endingBalance = endingBalance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
