package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VirtualAccountApplicationBankDetail {
    @JsonProperty("account_bank_id") private String accountBankId;
    @JsonProperty("account_holder") private String accountHolder;
    @JsonProperty("account_number") private String accountNumber;
    @JsonProperty("country_code") private String countryCode;
    @JsonProperty("currency") private String currency;
    @JsonProperty("bank_name") private String bankName;
    @JsonProperty("bank_address") private String bankAddress;
    @JsonProperty("clearing_system") private VirtualAccountClearingSystem clearingSystem;
    @JsonProperty("status") private VirtualAccountBankDetailStatus status;
    @JsonProperty("close_reason") private String closeReason;

    public String getAccountBankId() { return accountBankId; }
    public void setAccountBankId(String accountBankId) { this.accountBankId = accountBankId; }
    public String getAccountHolder() { return accountHolder; }
    public void setAccountHolder(String accountHolder) { this.accountHolder = accountHolder; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public String getBankAddress() { return bankAddress; }
    public void setBankAddress(String bankAddress) { this.bankAddress = bankAddress; }
    public VirtualAccountClearingSystem getClearingSystem() { return clearingSystem; }
    public void setClearingSystem(VirtualAccountClearingSystem clearingSystem) { this.clearingSystem = clearingSystem; }
    public VirtualAccountBankDetailStatus getStatus() { return status; }
    public void setStatus(VirtualAccountBankDetailStatus status) { this.status = status; }
    public String getCloseReason() { return closeReason; }
    public void setCloseReason(String closeReason) { this.closeReason = closeReason; }
}
