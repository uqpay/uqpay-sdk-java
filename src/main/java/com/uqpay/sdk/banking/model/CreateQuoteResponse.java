package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateQuoteResponse {

    // ISO 4217 currency code of the currency sold
    @JsonProperty("sell_currency")
    private String sellCurrency;

    // Amount of currency sold (decimal string)
    @JsonProperty("sell_amount")
    private String sellAmount;

    // ISO 4217 currency code of the currency purchased
    @JsonProperty("buy_currency")
    private String buyCurrency;

    // Calculated amount of currency purchased (decimal string)
    @JsonProperty("buy_amount")
    private String buyAmount;

    // Contains pricing details including rates, quote ID, and validity window
    @JsonProperty("quote_price")
    private QuotePrice quotePrice;

    public CreateQuoteResponse() {
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

    public QuotePrice getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(QuotePrice quotePrice) {
        this.quotePrice = quotePrice;
    }
}
