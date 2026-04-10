package com.uqpay.sdk.simulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulateAuthorizationResponse {

    @JsonProperty("card_id")
    private String cardId;

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("cardholder_id")
    private String cardholderId;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("card_available_balance")
    private Double cardAvailableBalance;

    @JsonProperty("authorization_code")
    private String authorizationCode;

    @JsonProperty("billing_amount")
    private Double billingAmount;

    @JsonProperty("billing_currency")
    private String billingCurrency;

    @JsonProperty("transaction_currency")
    private String transactionCurrency;

    @JsonProperty("transaction_amount")
    private Double transactionAmount;

    @JsonProperty("transaction_time")
    private String transactionTime;

    @JsonProperty("posted_time")
    private String postedTime;

    @JsonProperty("merchant_data")
    private SimulateMerchantData merchantData;

    @JsonProperty("failure_reason")
    private String failureReason;

    @JsonProperty("transaction_status")
    private String transactionStatus;

    public SimulateAuthorizationResponse() {
    }

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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getCardAvailableBalance() {
        return cardAvailableBalance;
    }

    public void setCardAvailableBalance(Double cardAvailableBalance) {
        this.cardAvailableBalance = cardAvailableBalance;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Double getBillingAmount() {
        return billingAmount;
    }

    public void setBillingAmount(Double billingAmount) {
        this.billingAmount = billingAmount;
    }

    public String getBillingCurrency() {
        return billingCurrency;
    }

    public void setBillingCurrency(String billingCurrency) {
        this.billingCurrency = billingCurrency;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
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

    public SimulateMerchantData getMerchantData() {
        return merchantData;
    }

    public void setMerchantData(SimulateMerchantData merchantData) {
        this.merchantData = merchantData;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
