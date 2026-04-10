package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RiskControls {

    @JsonProperty("allow_3ds_transactions")
    private String allow3dsTransactions; // Y | N; defaults to Y on create; controls 3DS challenge flow

    @JsonProperty("allowed_mcc")
    private List<String> allowedMcc; // MCC whitelist; mutually exclusive with blocked_mcc

    @JsonProperty("blocked_mcc")
    private List<String> blockedMcc; // MCC blacklist; mutually exclusive with allowed_mcc

    public RiskControls() {
    }

    public String getAllow3dsTransactions() {
        return allow3dsTransactions;
    }

    public void setAllow3dsTransactions(String allow3dsTransactions) {
        this.allow3dsTransactions = allow3dsTransactions;
    }

    public List<String> getAllowedMcc() {
        return allowedMcc;
    }

    public void setAllowedMcc(List<String> allowedMcc) {
        this.allowedMcc = allowedMcc;
    }

    public List<String> getBlockedMcc() {
        return blockedMcc;
    }

    public void setBlockedMcc(List<String> blockedMcc) {
        this.blockedMcc = blockedMcc;
    }
}
