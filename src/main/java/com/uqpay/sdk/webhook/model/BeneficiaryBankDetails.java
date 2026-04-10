package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeneficiaryBankDetails {

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("bank_address")
    private String bankAddress;

    @JsonProperty("bank_country_code")
    private String bankCountryCode;

    @JsonProperty("account_holder")
    private String accountHolder;

    @JsonProperty("account_currency_code")
    private String accountCurrencyCode;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("iban")
    private String iban;

    @JsonProperty("swift_code")
    private String swiftCode;

    @JsonProperty("clearing_system")
    private String clearingSystem;

    @JsonProperty("routing_code_type1")
    private String routingCodeType1;

    @JsonProperty("routing_code_value1")
    private String routingCodeValue1;

    @JsonProperty("routing_code_type2")
    private String routingCodeType2;

    @JsonProperty("routing_code_value2")
    private String routingCodeValue2;

    public BeneficiaryBankDetails() {
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankCountryCode() {
        return bankCountryCode;
    }

    public void setBankCountryCode(String bankCountryCode) {
        this.bankCountryCode = bankCountryCode;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getAccountCurrencyCode() {
        return accountCurrencyCode;
    }

    public void setAccountCurrencyCode(String accountCurrencyCode) {
        this.accountCurrencyCode = accountCurrencyCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getClearingSystem() {
        return clearingSystem;
    }

    public void setClearingSystem(String clearingSystem) {
        this.clearingSystem = clearingSystem;
    }

    public String getRoutingCodeType1() {
        return routingCodeType1;
    }

    public void setRoutingCodeType1(String routingCodeType1) {
        this.routingCodeType1 = routingCodeType1;
    }

    public String getRoutingCodeValue1() {
        return routingCodeValue1;
    }

    public void setRoutingCodeValue1(String routingCodeValue1) {
        this.routingCodeValue1 = routingCodeValue1;
    }

    public String getRoutingCodeType2() {
        return routingCodeType2;
    }

    public void setRoutingCodeType2(String routingCodeType2) {
        this.routingCodeType2 = routingCodeType2;
    }

    public String getRoutingCodeValue2() {
        return routingCodeValue2;
    }

    public void setRoutingCodeValue2(String routingCodeValue2) {
        this.routingCodeValue2 = routingCodeValue2;
    }
}
