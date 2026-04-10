package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccount {

    @JsonProperty("id")
    private String id; // UUID

    @JsonProperty("account_number")
    private String accountNumber; // e.g., IBAN

    @JsonProperty("account_name")
    private String accountName; // Name of the account holder

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("swift_code")
    private String swiftCode; // SWIFT/BIC code

    @JsonProperty("bank_country_code")
    private String bankCountryCode; // ISO 3166-1 alpha-2 (2-letter country code)

    @JsonProperty("bank_address")
    private String bankAddress;

    @JsonProperty("currency")
    private String currency; // ISO 4217 currency code (e.g., SGD, USD, GBP)

    @JsonProperty("bank_code_type")
    private String bankCodeType; // aba, bank_code, sort_code, bsb_code, ifsc, or cnaps_number

    @JsonProperty("bank_code_value")
    private String bankCodeValue; // Format depends on bank_code_type

    @JsonProperty("bank_branch_code")
    private String bankBranchCode; // Required when currency is CAD

    @JsonProperty("account_status")
    private String accountStatus; // Valid or Invalid

    public BankAccount() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getSwiftCode() { return swiftCode; }
    public void setSwiftCode(String swiftCode) { this.swiftCode = swiftCode; }

    public String getBankCountryCode() { return bankCountryCode; }
    public void setBankCountryCode(String bankCountryCode) { this.bankCountryCode = bankCountryCode; }

    public String getBankAddress() { return bankAddress; }
    public void setBankAddress(String bankAddress) { this.bankAddress = bankAddress; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getBankCodeType() { return bankCodeType; }
    public void setBankCodeType(String bankCodeType) { this.bankCodeType = bankCodeType; }

    public String getBankCodeValue() { return bankCodeValue; }
    public void setBankCodeValue(String bankCodeValue) { this.bankCodeValue = bankCodeValue; }

    public String getBankBranchCode() { return bankBranchCode; }
    public void setBankBranchCode(String bankBranchCode) { this.bankBranchCode = bankBranchCode; }

    public String getAccountStatus() { return accountStatus; }
    public void setAccountStatus(String accountStatus) { this.accountStatus = accountStatus; }
}
