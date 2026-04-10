package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {

    @JsonProperty("street_address")
    private String streetAddress; // required, max 255 chars

    @JsonProperty("city")
    private String city; // required, max 36 chars

    @JsonProperty("postal_code")
    private String postalCode; // required, max 12 chars

    @JsonProperty("country")
    private String country; // required, ISO 3166-1 alpha-2, e.g. "SG"

    @JsonProperty("state")
    private String state; // required, state or province, max 96 chars

    @JsonProperty("nationality")
    private String nationality; // optional, ISO 3166-1 alpha-2, 2 chars

    public Address() {
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
