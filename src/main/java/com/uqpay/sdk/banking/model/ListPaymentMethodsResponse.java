package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListPaymentMethodsResponse {

    @JsonProperty("data")
    private List<PaymentMethod> data;

    public ListPaymentMethodsResponse() {
    }

    public List<PaymentMethod> getData() {
        return data;
    }

    public void setData(List<PaymentMethod> data) {
        this.data = data;
    }
}
