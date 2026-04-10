package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateQuoteRequest {

    // Required. ISO 4217 currency code of the currency being sold
    @JsonProperty("sell_currency")
    private String sellCurrency;

    // Optional. Amount of currency to sell; specify only one of sellAmount or buyAmount
    @JsonProperty("sell_amount")
    private String sellAmount;

    // Required. ISO 4217 currency code of the currency being purchased
    @JsonProperty("buy_currency")
    private String buyCurrency;

    // Optional. Amount of currency to buy; specify only one of buyAmount or sellAmount
    @JsonProperty("buy_amount")
    private String buyAmount;

    // Required. Scheduled execution date on a valid business day, format: YYYY-MM-DD
    @JsonProperty("conversion_date")
    private String conversionDate;

    // Optional. "conversion" or "payout"
    @JsonProperty("transaction_type")
    private String transactionType;

    public CreateQuoteRequest() {
    }

    public String getSellCurrency() {
        return sellCurrency;
    }

    public void setSellCurrency(String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    public String getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(String sellAmount) {
        this.sellAmount = sellAmount;
    }

    public String getBuyCurrency() {
        return buyCurrency;
    }

    public void setBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    public String getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(String buyAmount) {
        this.buyAmount = buyAmount;
    }

    public String getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(String conversionDate) {
        this.conversionDate = conversionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
