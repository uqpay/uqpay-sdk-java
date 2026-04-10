package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RiskControl {

    @JsonProperty("allow_3ds_transactions")
    private String allow3dsTransactions;

    @JsonProperty("allow_online_transactions")
    private String allowOnlineTransactions;

    @JsonProperty("allow_atm_transactions")
    private String allowAtmTransactions;

    @JsonProperty("allow_contactless_transactions")
    private String allowContactlessTransactions;

    @JsonProperty("allow_international_transactions")
    private String allowInternationalTransactions;

    public RiskControl() {
    }

    public String getAllow3dsTransactions() {
        return allow3dsTransactions;
    }

    public void setAllow3dsTransactions(String allow3dsTransactions) {
        this.allow3dsTransactions = allow3dsTransactions;
    }

    public String getAllowOnlineTransactions() {
        return allowOnlineTransactions;
    }

    public void setAllowOnlineTransactions(String allowOnlineTransactions) {
        this.allowOnlineTransactions = allowOnlineTransactions;
    }

    public String getAllowAtmTransactions() {
        return allowAtmTransactions;
    }

    public void setAllowAtmTransactions(String allowAtmTransactions) {
        this.allowAtmTransactions = allowAtmTransactions;
    }

    public String getAllowContactlessTransactions() {
        return allowContactlessTransactions;
    }

    public void setAllowContactlessTransactions(String allowContactlessTransactions) {
        this.allowContactlessTransactions = allowContactlessTransactions;
    }

    public String getAllowInternationalTransactions() {
        return allowInternationalTransactions;
    }

    public void setAllowInternationalTransactions(String allowInternationalTransactions) {
        this.allowInternationalTransactions = allowInternationalTransactions;
    }
}
