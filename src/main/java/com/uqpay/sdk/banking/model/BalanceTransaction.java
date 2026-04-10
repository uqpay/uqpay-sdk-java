package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalanceTransaction {

    @JsonProperty("transaction_id")
    private String transactionId; // Required. Unique identifier for the transaction. UUID format.

    @JsonProperty("currency")
    private String currency; // Required. Three-letter currency code. ISO 4217 (e.g., USD).

    @JsonProperty("amount")
    private String amount; // Required. Transaction amount. Decimal string.

    @JsonProperty("transaction_type")
    private String transactionType; // Required. Classification of transaction. Allowed: DEPOSIT, PAYOUT, TRANSFER, CONVERSION, FEE, REFUND, ADJUSTMENT, INVOICE.

    @JsonProperty("transaction_status")
    private String transactionStatus; // Required. Current processing state. Allowed: FAILED, PENDING, COMPLETED, CANCELLED.

    @JsonProperty("create_time")
    private String createTime; // Required. Timestamp when the record was created. ISO 8601.

    @JsonProperty("reference_id")
    private String referenceId; // Required. The reference ID. UUID format.

    @JsonProperty("account_id")
    private String accountId; // Optional. Account identifier. UUID format.

    @JsonProperty("account_name")
    private String accountName; // Required. The account name.

    @JsonProperty("balance_id")
    private String balanceId; // Required. The account balance ID. UUID format.

    @JsonProperty("credit_debit_type")
    private String creditDebitType; // Required. Whether funds were added or removed. Allowed: C (credit), D (debit).

    @JsonProperty("complete_time")
    private String completeTime; // Required. Timestamp when the request was successfully processed. ISO 8601.

    @JsonProperty("transaction_way")
    private String transactionWay; // Optional. The transaction way. "API" for API, empty for other ways.

    public BalanceTransaction() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(String balanceId) {
        this.balanceId = balanceId;
    }

    public String getCreditDebitType() {
        return creditDebitType;
    }

    public void setCreditDebitType(String creditDebitType) {
        this.creditDebitType = creditDebitType;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getTransactionWay() {
        return transactionWay;
    }

    public void setTransactionWay(String transactionWay) {
        this.transactionWay = transactionWay;
    }
}
