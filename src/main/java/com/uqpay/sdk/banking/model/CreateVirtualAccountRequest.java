package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateVirtualAccountRequest {

    // Required. ISO 4217 currency codes, comma-separated for multiple (e.g. "USD,SGD")
    @JsonProperty("currency")
    private String currency;

    // Optional. LOCAL (domestic networks) or SWIFT (cross-border)
    @JsonProperty("payment_method")
    private String paymentMethod;

    public CreateVirtualAccountRequest() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
