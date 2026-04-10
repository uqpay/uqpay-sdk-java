package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents company info for a sub-account creation request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubAccountCompanyInfo {

    @JsonProperty("legal_business_name")
    private String legalBusinessName;

    @JsonProperty("legal_business_name_english")
    private String legalBusinessNameEnglish;

    @JsonProperty("country_of_incorporation")
    private String countryOfIncorporation;

    @JsonProperty("company_type")
    private String companyType;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("company_registration_number")
    private String companyRegistrationNumber;

    @JsonProperty("tax_type")
    private String taxType;

    @JsonProperty("tax_number")
    private String taxNumber;

    @JsonProperty("incorparate_date")
    private String incorporateDate;

    @JsonProperty("certification_of_incorporation")
    private List<String> certificationOfIncorporation;

    public SubAccountCompanyInfo() {
    }

    public String getLegalBusinessName() {
        return legalBusinessName;
    }

    public void setLegalBusinessName(String legalBusinessName) {
        this.legalBusinessName = legalBusinessName;
    }

    public String getLegalBusinessNameEnglish() {
        return legalBusinessNameEnglish;
    }

    public void setLegalBusinessNameEnglish(String legalBusinessNameEnglish) {
        this.legalBusinessNameEnglish = legalBusinessNameEnglish;
    }

    public String getCountryOfIncorporation() {
        return countryOfIncorporation;
    }

    public void setCountryOfIncorporation(String countryOfIncorporation) {
        this.countryOfIncorporation = countryOfIncorporation;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCompanyRegistrationNumber() {
        return companyRegistrationNumber;
    }

    public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getIncorporateDate() {
        return incorporateDate;
    }

    public void setIncorporateDate(String incorporateDate) {
        this.incorporateDate = incorporateDate;
    }

    public List<String> getCertificationOfIncorporation() {
        return certificationOfIncorporation;
    }

    public void setCertificationOfIncorporation(List<String> certificationOfIncorporation) {
        this.certificationOfIncorporation = certificationOfIncorporation;
    }
}
