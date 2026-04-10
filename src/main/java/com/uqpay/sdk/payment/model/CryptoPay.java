package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptoPay {

    @JsonProperty("flow")
    private String flow;

    @JsonProperty("network")
    private String network;

    @JsonProperty("is_present")
    private Boolean isPresent;

    public CryptoPay() {
    }

    public String getFlow() { return flow; }
    public void setFlow(String flow) { this.flow = flow; }

    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }

    public Boolean getIsPresent() { return isPresent; }
    public void setIsPresent(Boolean isPresent) { this.isPresent = isPresent; }
}
