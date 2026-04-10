package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeneficiaryCheckRequest {

    @JsonProperty("entity_type")
    private String entityType; // Required. COMPANY or INDIVIDUAL

    @JsonProperty("account_number")
    private String accountNumber; // Required. Alphanumeric only, max 60 chars, no dashes

    @JsonProperty("payment_method")
    private String paymentMethod; // Required. LOCAL or SWIFT

    @JsonProperty("currency")
    private String currency; // Required. ISO 4217 currency code, e.g. "USD"

    @JsonProperty("bank_country_code")
    private String bankCountryCode; // Required. ISO 3166-1 alpha-2 country code, e.g. "SG"

    @JsonProperty("first_name")
    private String firstName; // Optional. Beneficiary given name, max 45 chars

    @JsonProperty("last_name")
    private String lastName; // Optional. Beneficiary surname, max 45 chars

    @JsonProperty("company_name")
    private String companyName; // Optional. Business entity name, max 120 chars

    @JsonProperty("clearing_system")
    private String clearingSystem; // Optional. Transaction clearing network, varies by currency (e.g. ACH, SWIFT, GIRO, FPS)

    @JsonProperty("iban")
    private String iban; // Optional. International bank account number, max 36 chars; required for specific countries

    @JsonProperty("additional_info")
    private BeneficiaryAdditionalInfo additionalInfo; // Optional. Supplementary beneficiary data; contains proxy_id for PayNow

    public BeneficiaryCheckRequest() {
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBankCountryCode() {
        return bankCountryCode;
    }

    public void setBankCountryCode(String bankCountryCode) {
        this.bankCountryCode = bankCountryCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getClearingSystem() {
        return clearingSystem;
    }

    public void setClearingSystem(String clearingSystem) {
        this.clearingSystem = clearingSystem;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BeneficiaryAdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(BeneficiaryAdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
