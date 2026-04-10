package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryAddress {

    @JsonProperty("line1")
    private String line1; // required; street, PO Box, or company name; max 255

    @JsonProperty("line2")
    private String line2; // optional; apartment, suite, unit; max 255

    @JsonProperty("city")
    private String city; // required; max 128

    @JsonProperty("state")
    private String state; // optional; state, county, province; max 128

    @JsonProperty("postal_code")
    private String postalCode; // required; max 16

    @JsonProperty("country")
    private String country; // required; ISO 3166-1 alpha-2, e.g. "SG"

    public DeliveryAddress() {
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
