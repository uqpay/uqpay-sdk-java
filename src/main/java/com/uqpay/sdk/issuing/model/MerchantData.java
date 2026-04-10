package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantData {

    @JsonProperty("category_code")
    private String categoryCode; // Required. Merchant category code (MCC), e.g. "6011"

    @JsonProperty("city")
    private String city; // Optional. City where the merchant is located

    @JsonProperty("country")
    private String country; // Optional. Country where the merchant is located, e.g. "CN"

    @JsonProperty("name")
    private String name; // Optional. Merchant name

    public MerchantData() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
