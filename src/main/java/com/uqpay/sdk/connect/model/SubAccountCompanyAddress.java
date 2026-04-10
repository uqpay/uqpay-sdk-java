package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents company address for a sub-account creation request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubAccountCompanyAddress {

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

    public SubAccountCompanyAddress() {
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
