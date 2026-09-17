package com.uqpay.sdk.simulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String cardAvailableBalanceValue;

    @JsonProperty("authorization_code")
    private String authorizationCode;

    @JsonProperty("billing_amount")
    private String billingAmountValue;

    @JsonProperty("billing_currency")
    private String billingCurrency;

    @JsonProperty("transaction_currency")
    private String transactionCurrency;

    @JsonProperty("transaction_amount")
    private String transactionAmountValue;

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

    /** @deprecated Use getCardAvailableBalanceValue() to retain decimal precision. */
    @Deprecated
    @JsonIgnore
    public Double getCardAvailableBalance() {
        return cardAvailableBalanceValue == null || cardAvailableBalanceValue.isEmpty() ? null : Double.valueOf(cardAvailableBalanceValue);
    }

    @JsonIgnore
    public void setCardAvailableBalance(Double cardAvailableBalance) {
        this.cardAvailableBalanceValue = cardAvailableBalance == null ? null : cardAvailableBalance.toString();
    }

    @JsonProperty("card_available_balance")
    public String getCardAvailableBalanceValue() {
        return cardAvailableBalanceValue;
    }

    @JsonProperty("card_available_balance")
    public void setCardAvailableBalanceValue(String value) {
        this.cardAvailableBalanceValue = value;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    /** @deprecated Use getBillingAmountValue() to retain decimal precision. */
    @Deprecated
    @JsonIgnore
    public Double getBillingAmount() {
        return billingAmountValue == null || billingAmountValue.isEmpty() ? null : Double.valueOf(billingAmountValue);
    }

    @JsonIgnore
    public void setBillingAmount(Double billingAmount) {
        this.billingAmountValue = billingAmount == null ? null : billingAmount.toString();
    }

    @JsonProperty("billing_amount")
    public String getBillingAmountValue() {
        return billingAmountValue;
    }

    @JsonProperty("billing_amount")
    public void setBillingAmountValue(String value) {
        this.billingAmountValue = value;
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

    /** @deprecated Use getTransactionAmountValue() to retain decimal precision. */
    @Deprecated
    @JsonIgnore
    public Double getTransactionAmount() {
        return transactionAmountValue == null || transactionAmountValue.isEmpty() ? null : Double.valueOf(transactionAmountValue);
    }

    @JsonIgnore
    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmountValue = transactionAmount == null ? null : transactionAmount.toString();
    }

    @JsonProperty("transaction_amount")
    public String getTransactionAmountValue() {
        return transactionAmountValue;
    }

    @JsonProperty("transaction_amount")
    public void setTransactionAmountValue(String value) {
        this.transactionAmountValue = value;
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
