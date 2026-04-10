package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentMethod {

    @JsonProperty("country")
    private String country; // ISO 3166-1 alpha-2 country code, e.g. "SG"

    @JsonProperty("currency")
    private String currency; // ISO 4217 currency code, e.g. "USD"

    @JsonProperty("clearing_systems")
    private String clearingSystems; // available clearing system, varies by currency (e.g. ACH, SWIFT, FAST, GIRO, PayNow)

    @JsonProperty("payment_method")
    private String paymentMethod; // "LOCAL" or "SWIFT"

    public PaymentMethod() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getClearingSystems() {
        return clearingSystems;
    }

    public void setClearingSystems(String clearingSystems) {
        this.clearingSystems = clearingSystems;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
