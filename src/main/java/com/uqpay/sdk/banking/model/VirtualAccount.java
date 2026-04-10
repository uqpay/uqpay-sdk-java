package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VirtualAccount {

    @JsonProperty("account_holder")
    private String accountHolder; // Required. Name of the account holder

    @JsonProperty("account_number")
    private String accountNumber; // Required. Account number or IBAN

    @JsonProperty("capability")
    private Map<String, Object> capability; // Required. Contains "payment_method" key with value SWIFT or LOCAL

    @JsonProperty("country_code")
    private String countryCode; // Required. ISO 3166-1 alpha-2 country code

    @JsonProperty("currency")
    private String currency; // Required. ISO 4217 currency code, lowercase 3-letter

    @JsonProperty("account_bank_id")
    private String accountBankId; // Required. Unique bank identifier, UUID format

    @JsonProperty("bank_name")
    private String bankName; // Required. Name of the financial institution

    @JsonProperty("bank_address")
    private String bankAddress; // Required. Physical address of the bank

    @JsonProperty("routing_codes")
    private Object routingCodes; // Optional. Routing codes for the account

    @JsonProperty("clearing_system")
    private Map<String, String> clearingSystem; // Optional. Contains "type" (e.g. "bic_swift") and "value" keys

    @JsonProperty("status")
    private String status; // Optional. ACTIVE, INACTIVE, or CLOSED

    @JsonProperty("close_reason")
    private String closeReason; // Optional. Reason for account closure, present only when status is CLOSED

    public VirtualAccount() {
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Map<String, Object> getCapability() {
        return capability;
    }

    public void setCapability(Map<String, Object> capability) {
        this.capability = capability;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountBankId() {
        return accountBankId;
    }

    public void setAccountBankId(String accountBankId) {
        this.accountBankId = accountBankId;
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

    public Object getRoutingCodes() {
        return routingCodes;
    }

    public void setRoutingCodes(Object routingCodes) {
        this.routingCodes = routingCodes;
    }

    public Map<String, String> getClearingSystem() {
        return clearingSystem;
    }

    public void setClearingSystem(Map<String, String> clearingSystem) {
        this.clearingSystem = clearingSystem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }
}
