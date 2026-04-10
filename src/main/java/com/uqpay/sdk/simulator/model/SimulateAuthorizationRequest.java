package com.uqpay.sdk.simulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulateAuthorizationRequest {

    @JsonProperty("card_id")
    private String cardId; // Required. The card ID to simulate authorization for.

    @JsonProperty("transaction_amount")
    private Double transactionAmount; // Required. The transaction amount.

    @JsonProperty("transaction_currency")
    private String transactionCurrency; // Required. The transaction currency. ISO 4217 (e.g., USD).

    @JsonProperty("merchant_name")
    private String merchantName; // Required. The merchant name.

    @JsonProperty("merchant_category_code")
    private String merchantCategoryCode; // Required. The merchant category code (MCC).

    public SimulateAuthorizationRequest() {
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }
}
