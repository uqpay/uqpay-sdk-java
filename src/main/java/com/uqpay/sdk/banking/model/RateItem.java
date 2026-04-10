package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateItem {

    @JsonProperty("currency_pair")
    private String currencyPair; // Required. 6-char uppercase currency pair, e.g. "USDEUR"

    @JsonProperty("buy_price")
    private String buyPrice; // Required. Buy price, rounded to 4 decimal places

    @JsonProperty("sell_price")
    private String sellPrice; // Required. Sell price, rounded to 4 decimal places

    public RateItem() {
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

}
