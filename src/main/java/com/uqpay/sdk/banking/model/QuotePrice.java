package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuotePrice {

    @JsonProperty("currency_pair")
    private String currencyPair;

    @JsonProperty("direct_rate")
    private String directRate;

    @JsonProperty("inverse_rate")
    private String inverseRate;

    @JsonProperty("quote_id")
    private String quoteId;

    @JsonProperty("validity")
    private QuoteValidity validity;

    public QuotePrice() {
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public String getDirectRate() {
        return directRate;
    }

    public void setDirectRate(String directRate) {
        this.directRate = directRate;
    }

    public String getInverseRate() {
        return inverseRate;
    }

    public void setInverseRate(String inverseRate) {
        this.inverseRate = inverseRate;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public QuoteValidity getValidity() {
        return validity;
    }

    public void setValidity(QuoteValidity validity) {
        this.validity = validity;
    }
}
