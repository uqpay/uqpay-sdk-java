package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents business details for a company sub-account creation request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubAccountBusinessDetails {

    @JsonProperty("country_or_territory")
    private String countryOrTerritory;

    @JsonProperty("street_address")
    private String streetAddress;

    @JsonProperty("apartment_suite_or_floor")
    private String apartmentSuiteOrFloor;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("industry")
    private String industry;

    @JsonProperty("turnover_monthly")
    private String turnoverMonthly;

    @JsonProperty("number_of_employee")
    private String numberOfEmployee;

    @JsonProperty("website_url")
    private String websiteUrl;

    @JsonProperty("company_description")
    private String companyDescription;

    @JsonProperty("account_purpose")
    private List<String> accountPurpose;

    @JsonProperty("banking_currencies")
    private List<String> bankingCurrencies;

    @JsonProperty("banking_countries")
    private List<String> bankingCountries;

    @JsonProperty("issuing_countries")
    private List<String> issuingCountries;

    @JsonProperty("issuing_monthly")
    private String issuingMonthly;

    public SubAccountBusinessDetails() {
    }

    public String getCountryOrTerritory() {
        return countryOrTerritory;
    }

    public void setCountryOrTerritory(String countryOrTerritory) {
        this.countryOrTerritory = countryOrTerritory;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getApartmentSuiteOrFloor() {
        return apartmentSuiteOrFloor;
    }

    public void setApartmentSuiteOrFloor(String apartmentSuiteOrFloor) {
        this.apartmentSuiteOrFloor = apartmentSuiteOrFloor;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getTurnoverMonthly() {
        return turnoverMonthly;
    }

    public void setTurnoverMonthly(String turnoverMonthly) {
        this.turnoverMonthly = turnoverMonthly;
    }

    public String getNumberOfEmployee() {
        return numberOfEmployee;
    }

    public void setNumberOfEmployee(String numberOfEmployee) {
        this.numberOfEmployee = numberOfEmployee;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public List<String> getAccountPurpose() {
        return accountPurpose;
    }

    public void setAccountPurpose(List<String> accountPurpose) {
        this.accountPurpose = accountPurpose;
    }

    public List<String> getBankingCurrencies() {
        return bankingCurrencies;
    }

    public void setBankingCurrencies(List<String> bankingCurrencies) {
        this.bankingCurrencies = bankingCurrencies;
    }

    public List<String> getBankingCountries() {
        return bankingCountries;
    }

    public void setBankingCountries(List<String> bankingCountries) {
        this.bankingCountries = bankingCountries;
    }

    public List<String> getIssuingCountries() {
        return issuingCountries;
    }

    public void setIssuingCountries(List<String> issuingCountries) {
        this.issuingCountries = issuingCountries;
    }

    public String getIssuingMonthly() {
        return issuingMonthly;
    }

    public void setIssuingMonthly(String issuingMonthly) {
        this.issuingMonthly = issuingMonthly;
    }
}
