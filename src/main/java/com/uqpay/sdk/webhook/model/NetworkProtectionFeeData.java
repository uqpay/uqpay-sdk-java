package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NetworkProtectionFeeData {
    @JsonProperty("card_id")
    private String cardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String value) {
        this.cardId = value;
    }

    @JsonProperty("account_id")
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String value) {
        this.accountId = value;
    }

    @JsonProperty("billing_period")
    private String billingPeriod;

    public String getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(String value) {
        this.billingPeriod = value;
    }

    @JsonProperty("card_count")
    private Integer cardCount;

    public Integer getCardCount() {
        return cardCount;
    }

    public void setCardCount(Integer value) {
        this.cardCount = value;
    }

    @JsonProperty("action_code")
    private String actionCode;

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String value) {
        this.actionCode = value;
    }

    @JsonProperty("transaction_id")
    private String transactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String value) {
        this.transactionId = value;
    }

    @JsonProperty("transaction_type")
    private String transactionType;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String value) {
        this.transactionType = value;
    }

    @JsonProperty("remark")
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }

    @JsonProperty("transaction_amount")
    private String transactionAmount;

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String value) {
        this.transactionAmount = value;
    }

    @JsonProperty("transaction_currency")
    private String transactionCurrency;

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String value) {
        this.transactionCurrency = value;
    }

    @JsonProperty("transaction_time")
    private String transactionTime;

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String value) {
        this.transactionTime = value;
    }

    @JsonProperty("posted_time")
    private String postedTime;

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String value) {
        this.postedTime = value;
    }

    @JsonProperty("transaction_status")
    private String transactionStatus;

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String value) {
        this.transactionStatus = value;
    }

    @JsonProperty("balance_amount")
    private String balanceAmount;

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String value) {
        this.balanceAmount = value;
    }

}
