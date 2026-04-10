package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents individual info for a sub-account creation request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubAccountIndividualInfo {

    @JsonProperty("first_name_english")
    private String firstNameEnglish;

    @JsonProperty("last_name_english")
    private String lastNameEnglish;

    @JsonProperty("name_in_other_language")
    private String nameInOtherLanguage;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("tax_number")
    private String taxNumber;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

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

    public SubAccountIndividualInfo() {
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

    public String getNameInOtherLanguage() {
        return nameInOtherLanguage;
    }

    public void setNameInOtherLanguage(String nameInOtherLanguage) {
        this.nameInOtherLanguage = nameInOtherLanguage;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
}
