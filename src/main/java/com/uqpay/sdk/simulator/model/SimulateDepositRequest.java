package com.uqpay.sdk.simulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulateDepositRequest {

    @JsonProperty("amount")
    private Double amount; // Required. The deposit amount.

    @JsonProperty("currency")
    private String currency; // Required. The deposit currency. ISO 4217 (e.g., USD).

    @JsonProperty("sender_swift_code")
    private String senderSwiftCode; // Required. The sender's SWIFT code.

    @JsonProperty("receiver_account_number")
    private String receiverAccountNumber; // Optional. The receiver's account number.

    @JsonProperty("sender_account_number")
    private String senderAccountNumber; // Optional. The sender's account number.

    @JsonProperty("sender_country")
    private String senderCountry; // Optional. The sender's country.

    @JsonProperty("sender_name")
    private String senderName; // Optional. The sender's name.

    public SimulateDepositRequest() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSenderSwiftCode() {
        return senderSwiftCode;
    }

    public void setSenderSwiftCode(String senderSwiftCode) {
        this.senderSwiftCode = senderSwiftCode;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getSenderCountry() {
        return senderCountry;
    }

    public void setSenderCountry(String senderCountry) {
        this.senderCountry = senderCountry;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
