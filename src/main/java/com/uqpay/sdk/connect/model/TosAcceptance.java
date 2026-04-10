package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents terms of service acceptance.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TosAcceptance {

    @JsonProperty("date")
    private String date;

    @JsonProperty("ip")
    private String ip;

    @JsonProperty("user_agent")
    private String userAgent;

    @JsonProperty("tos_agreement")
    private Integer tosAgreement;

    public TosAcceptance() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Integer getTosAgreement() {
        return tosAgreement;
    }

    public void setTosAgreement(Integer tosAgreement) {
        this.tosAgreement = tosAgreement;
    }
}
