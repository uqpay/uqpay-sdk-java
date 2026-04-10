package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListRatesResponse {

    @JsonProperty("data")
    private RatesData data; // Required. Contains the list of rate items

    @JsonProperty("unavailable_currency_pairs")
    private List<String> unavailableCurrencyPairs; // Optional. Currency pairs from the request that are unsupported or unavailable

    @JsonProperty("last_updated")
    private String lastUpdated; // Optional. ISO 8601 date-time of the most recent rate update

    public ListRatesResponse() {
    }

    public List<RateItem> getRates() {
        return data != null ? data.getRates() : null;
    }

    public RatesData getData() {
        return data;
    }

    public void setData(RatesData data) {
        this.data = data;
    }

    public List<String> getUnavailableCurrencyPairs() {
        return unavailableCurrencyPairs;
    }

    public void setUnavailableCurrencyPairs(List<String> unavailableCurrencyPairs) {
        this.unavailableCurrencyPairs = unavailableCurrencyPairs;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RatesData {

        @JsonProperty("rates")
        private List<RateItem> rates;

        public RatesData() {
        }

        public List<RateItem> getRates() {
            return rates;
        }

        public void setRates(List<RateItem> rates) {
            this.rates = rates;
        }
    }
}
