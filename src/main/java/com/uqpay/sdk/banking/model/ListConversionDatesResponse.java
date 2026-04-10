package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListConversionDatesResponse {

    @JsonProperty("data")
    private List<ConversionDate> data;

    public ListConversionDatesResponse() {
    }

    public List<ConversionDate> getData() {
        return data;
    }

    public void setData(List<ConversionDate> data) {
        this.data = data;
    }
}
