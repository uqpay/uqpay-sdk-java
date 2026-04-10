package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayoutPayer {

    @JsonProperty("payer_id")
    private String payerId; // Required. UUID v4 of the payer

    @JsonProperty("entity_type")
    private String entityType; // Required. COMPANY or INDIVIDUAL

    @JsonProperty("country")
    private String country; // Optional. ISO 3166-1 alpha-2 country code (2 chars)

    @JsonProperty("company_name")
    private String companyName; // Optional. Company name; applicable when entity_type is COMPANY

    @JsonProperty("first_name")
    private String firstName; // Optional. Payer's first name

    @JsonProperty("last_name")
    private String lastName; // Optional. Payer's last name

    @JsonProperty("city")
    private String city; // Optional. City name of the payer

    @JsonProperty("address")
    private String address; // Optional. Street address of the payer

    @JsonProperty("state")
    private String state; // Optional. State or province of the payer

    @JsonProperty("postal_code")
    private String postalCode; // Optional. Postal code of the payer

    @JsonProperty("date_birth")
    private String dateBirth; // Optional. Birth date of the payer, format: YYYY-MM-DD

    @JsonProperty("identification_type")
    private String identificationType; // Optional. PASSPORT, DRIVERS_LICENSE, NATIONAL_ID, NRIC, or CERTIFICATE_OF_INCORPORATION

    @JsonProperty("identification_value")
    private String identificationValue; // Optional. ID document number, e.g. passport number

    public PayoutPayer() {
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationValue() {
        return identificationValue;
    }

    public void setIdentificationValue(String identificationValue) {
        this.identificationValue = identificationValue;
    }
}
