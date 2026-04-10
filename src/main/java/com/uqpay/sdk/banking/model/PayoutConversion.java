package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayoutConversion {

    // Exchange pair identifier, e.g., USDEUR
    @JsonProperty("currency_pair")
    private String currencyPair;

    // Applied exchange rate (decimal format)
    @JsonProperty("client_rate")
    private String clientRate;

    public PayoutConversion() {
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public String getClientRate() {
        return clientRate;
    }

    public void setClientRate(String clientRate) {
        this.clientRate = clientRate;
    }
}
