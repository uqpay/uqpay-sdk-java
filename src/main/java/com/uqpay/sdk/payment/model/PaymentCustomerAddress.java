package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentCustomerAddress {

    @JsonProperty("country_code")
    private String countryCode; // Required. ISO 3166-1 alpha-2 two-letter country code

    @JsonProperty("state")
    private String state; // Optional. Required for US/CA; max 100 chars

    @JsonProperty("city")
    private String city; // Required. City name, max 100 chars

    @JsonProperty("street")
    private String street; // Required. Street address, max 100 chars

    @JsonProperty("postcode")
    private String postcode; // Required. Postal code, max 10 chars

    public PaymentCustomerAddress() {
    }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
}
