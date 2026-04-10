package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepositSender {

    @JsonProperty("sender_name")
    private String senderName; // Optional. Customer-facing business name of the sender.

    @JsonProperty("sender_country")
    private String senderCountry; // Optional. Two-letter country code. ISO 3166-1 alpha-2.

    @JsonProperty("sender_account_number")
    private String senderAccountNumber; // Optional. Source account number, max 60 chars. Alphanumeric only.

    @JsonProperty("sender_swift_code")
    private String senderSwiftCode; // Optional. SWIFT/BIC code of sender's bank, max 30 characters.

    public DepositSender() {
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
