package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardTransactionData {

    // =========================================================================
    // Transaction status constants
    // =========================================================================

    public static final String TRANSACTION_STATUS_APPROVED = "APPROVED";
    public static final String TRANSACTION_STATUS_DECLINED = "DECLINED";
    public static final String TRANSACTION_STATUS_PENDING = "PENDING";
    public static final String TRANSACTION_STATUS_REVERSED = "REVERSED";

    // =========================================================================
    // Transaction type constants
    // =========================================================================

    public static final String TRANSACTION_TYPE_FEE = "FEE";
    public static final String TRANSACTION_TYPE_PURCHASE = "PURCHASE";
    public static final String TRANSACTION_TYPE_REFUND = "REFUND";
    public static final String TRANSACTION_TYPE_WITHDRAWAL = "WITHDRAWAL";
    public static final String TRANSACTION_TYPE_TOPUP = "TOPUP";

    // =========================================================================
    // Fields
    // =========================================================================

    @JsonProperty("card_id")
    private String cardId;

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("cardholder_id")
    private String cardholderId;

    @JsonProperty("card_available_balance")
    private String cardAvailableBalance;

    @JsonProperty("transaction_amount")
    private String transactionAmount;

    @JsonProperty("transaction_currency")
    private String transactionCurrency;

    @JsonProperty("billing_amount")
    private String billingAmount;

    @JsonProperty("billing_currency")
    private String billingCurrency;

    @JsonProperty("transaction_status")
    private String transactionStatus;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("transaction_time")
    private String transactionTime;

    @JsonProperty("posted_time")
    private String postedTime;

    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("short_reference_id")
    private String shortReferenceId;

    @JsonProperty("remark")
    private String remark;

    public CardTransactionData() {
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardholderId() {
        return cardholderId;
    }

    public void setCardholderId(String cardholderId) {
        this.cardholderId = cardholderId;
    }

    public String getCardAvailableBalance() {
        return cardAvailableBalance;
    }

    public void setCardAvailableBalance(String cardAvailableBalance) {
        this.cardAvailableBalance = cardAvailableBalance;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getBillingAmount() {
        return billingAmount;
    }

    public void setBillingAmount(String billingAmount) {
        this.billingAmount = billingAmount;
    }

    public String getBillingCurrency() {
        return billingCurrency;
    }

    public void setBillingCurrency(String billingCurrency) {
        this.billingCurrency = billingCurrency;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getShortReferenceId() {
        return shortReferenceId;
    }

    public void setShortReferenceId(String shortReferenceId) {
        this.shortReferenceId = shortReferenceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
