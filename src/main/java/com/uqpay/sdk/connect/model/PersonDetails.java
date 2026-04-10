package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents person details for an individual account creation request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDetails {

    @JsonProperty("first_name_english")
    private String firstNameEnglish;

    @JsonProperty("last_name_english")
    private String lastNameEnglish;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("local_name")
    private String localName;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("tax_number")
    private String taxNumber;

    @JsonProperty("internationally")
    private Integer internationally;

    @JsonProperty("banking_currencies")
    private List<String> bankingCurrencies;

    @JsonProperty("banking_countries")
    private List<String> bankingCountries;

    @JsonProperty("monthly_estimated_revenue")
    private MonthlyEstimatedRevenue monthlyEstimatedRevenue;

    @JsonProperty("account_purpose")
    private List<String> accountPurpose;

    @JsonProperty("other_purpose")
    private String otherPurpose;

    @JsonProperty("identification")
    private Identification identification;

    public PersonDetails() {
    }

    public String getFirstNameEnglish() {
        return firstNameEnglish;
    }

    public void setFirstNameEnglish(String firstNameEnglish) {
        this.firstNameEnglish = firstNameEnglish;
    }

    public String getLastNameEnglish() {
        return lastNameEnglish;
    }

    public void setLastNameEnglish(String lastNameEnglish) {
        this.lastNameEnglish = lastNameEnglish;
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

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public Integer getInternationally() {
        return internationally;
    }

    public void setInternationally(Integer internationally) {
        this.internationally = internationally;
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

    public MonthlyEstimatedRevenue getMonthlyEstimatedRevenue() {
        return monthlyEstimatedRevenue;
    }

    public void setMonthlyEstimatedRevenue(MonthlyEstimatedRevenue monthlyEstimatedRevenue) {
        this.monthlyEstimatedRevenue = monthlyEstimatedRevenue;
    }

    public List<String> getAccountPurpose() {
        return accountPurpose;
    }

    public void setAccountPurpose(List<String> accountPurpose) {
        this.accountPurpose = accountPurpose;
    }

    public String getOtherPurpose() {
        return otherPurpose;
    }

    public void setOtherPurpose(String otherPurpose) {
        this.otherPurpose = otherPurpose;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }
}
