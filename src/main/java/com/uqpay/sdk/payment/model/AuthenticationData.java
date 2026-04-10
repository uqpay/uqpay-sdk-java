package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationData {

    @JsonProperty("cvv_result")
    private String cvvResult;

    @JsonProperty("avs_result")
    private String avsResult;

    @JsonProperty("three_ds")
    private Map<String, Object> threeDs;

    public AuthenticationData() {}

    public String getCvvResult() { return cvvResult; }
    public void setCvvResult(String cvvResult) { this.cvvResult = cvvResult; }

    public String getAvsResult() { return avsResult; }
    public void setAvsResult(String avsResult) { this.avsResult = avsResult; }

    public Map<String, Object> getThreeDs() { return threeDs; }
    public void setThreeDs(Map<String, Object> threeDs) { this.threeDs = threeDs; }
}
