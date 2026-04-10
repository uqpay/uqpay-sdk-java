package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDecisionRequest {

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("card_id")
    private String cardId;

    @JsonProperty("cardholder_id")
    private String cardholderId;

    @JsonProperty("transaction_amount")
    private Double transactionAmount;

    @JsonProperty("transaction_currency")
    private String transactionCurrency;

    @JsonProperty("billing_amount")
    private Double billingAmount;

    @JsonProperty("billing_currency")
    private String billingCurrency;

    @JsonProperty("merchant_data")
    private Map<String, Object> merchantData;

    @JsonProperty("transaction_type")
    private String transactionType;

    public AuthDecisionRequest() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardholderId() {
        return cardholderId;
    }

    public void setCardholderId(String cardholderId) {
        this.cardholderId = cardholderId;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
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

    public Map<String, Object> getMerchantData() {
        return merchantData;
    }

    public void setMerchantData(Map<String, Object> merchantData) {
        this.merchantData = merchantData;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
