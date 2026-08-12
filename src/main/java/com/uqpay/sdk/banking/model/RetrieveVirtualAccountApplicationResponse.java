package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrieveVirtualAccountApplicationResponse {
    @JsonProperty("data") private VirtualAccountApplication data;

    public VirtualAccountApplication getData() { return data; }
    public void setData(VirtualAccountApplication data) { this.data = data; }
}
