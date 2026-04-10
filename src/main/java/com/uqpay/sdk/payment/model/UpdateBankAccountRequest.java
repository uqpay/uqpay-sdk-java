package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateBankAccountRequest {

    @JsonProperty("account_number")
    private String accountNumber; // Required. Bank account number, e.g. IBAN

    @JsonProperty("bank_name")
    private String bankName; // Required. Name of the bank

    @JsonProperty("swift_code")
    private String swiftCode; // Required. SWIFT/BIC code of the bank

    @JsonProperty("bank_country_code")
    private String bankCountryCode; // Required. ISO 3166-1 alpha-2 two-letter country code

    @JsonProperty("bank_address")
    private String bankAddress; // Required. Physical address of the bank

    @JsonProperty("bank_code_type")
    private String bankCodeType; // Optional. Routing code type: aba, bank_code, sort_code, bsb_code, ifsc, cnaps_number

    @JsonProperty("bank_code_value")
    private String bankCodeValue; // Optional. Bank identifier value, format varies by bank_code_type

    @JsonProperty("bank_branch_code")
    private String bankBranchCode; // Optional. Branch code; required for CAD currency

    public UpdateBankAccountRequest() {
    }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getSwiftCode() { return swiftCode; }
    public void setSwiftCode(String swiftCode) { this.swiftCode = swiftCode; }

    public String getBankCountryCode() { return bankCountryCode; }
    public void setBankCountryCode(String bankCountryCode) { this.bankCountryCode = bankCountryCode; }

    public String getBankAddress() { return bankAddress; }
    public void setBankAddress(String bankAddress) { this.bankAddress = bankAddress; }

    public String getBankCodeType() { return bankCodeType; }
    public void setBankCodeType(String bankCodeType) { this.bankCodeType = bankCodeType; }

    public String getBankCodeValue() { return bankCodeValue; }
    public void setBankCodeValue(String bankCodeValue) { this.bankCodeValue = bankCodeValue; }

    public String getBankBranchCode() { return bankBranchCode; }
    public void setBankBranchCode(String bankBranchCode) { this.bankBranchCode = bankBranchCode; }

}
