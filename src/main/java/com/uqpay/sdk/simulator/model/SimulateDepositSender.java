package com.uqpay.sdk.simulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulateDepositSender {

    @JsonProperty("sender_name")
    private String senderName;

    @JsonProperty("sender_country")
    private String senderCountry;

    @JsonProperty("sender_account_number")
    private String senderAccountNumber;

    @JsonProperty("sender_swift_code")
    private String senderSwiftCode;

    public SimulateDepositSender() {
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderCountry() {
        return senderCountry;
    }

    public void setSenderCountry(String senderCountry) {
        this.senderCountry = senderCountry;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getSenderSwiftCode() {
        return senderSwiftCode;
    }

    public void setSenderSwiftCode(String senderSwiftCode) {
        this.senderSwiftCode = senderSwiftCode;
    }
}
