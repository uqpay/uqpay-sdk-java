package com.uqpay.sdk.simulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulateMerchantData {

    @JsonProperty("name")
    private String name; // Merchant name.

    @JsonProperty("category_code")
    private String categoryCode; // Merchant category code (MCC).

    @JsonProperty("city")
    private String city; // Merchant city.

    @JsonProperty("country")
    private String country; // Merchant country.

    public SimulateMerchantData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
