package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateConversionRequest {

    @JsonProperty("quote_id")
    private String quoteId; // Required. UUID of the quote obtained from createQuote

    @JsonProperty("sell_currency")
    private String sellCurrency; // Required. ISO 4217 currency code of the currency being sold

    @JsonProperty("sell_amount")
    private String sellAmount; // Optional. Amount of currency to sell; specify only one of sellAmount or buyAmount

    @JsonProperty("buy_currency")
    private String buyCurrency; // Required. ISO 4217 currency code of the currency being purchased

    @JsonProperty("buy_amount")
    private String buyAmount; // Optional. Amount of currency to buy; specify only one of buyAmount or sellAmount

    @JsonProperty("conversion_date")
    private String conversionDate; // Required. Date for conversion execution, format: YYYY-MM-DD (only current date supported)

    public CreateConversionRequest() {
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
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
}
