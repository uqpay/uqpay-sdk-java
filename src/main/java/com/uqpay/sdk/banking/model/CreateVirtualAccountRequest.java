package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateVirtualAccountRequest {

    // Required. ISO 3166-1 alpha-2 country code.
    @JsonProperty("country")
    private String country;

    // Required. A single ISO 4217 alpha-3 currency code.
    @JsonProperty("currency")
    private String currency;

    // Optional. Omit to evaluate both LOCAL and SWIFT.
    @JsonProperty("payment_method")
    private VirtualAccountPaymentMethod paymentMethod;

    // Optional display name, maximum 255 characters.
    @JsonProperty("nickname")
    private String nickname;

    public CreateVirtualAccountRequest() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = normalizeCode(country);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = normalizeCode(currency);
    }

    public VirtualAccountPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(VirtualAccountPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = normalizeOptional(nickname);
    }

    public void validate() {
        if (country == null) {
            throw new IllegalArgumentException("country must not be blank");
        }
        if (currency == null) {
            throw new IllegalArgumentException("currency must not be blank");
        }
        if (currency.indexOf(',') >= 0) {
            throw new IllegalArgumentException("currency must contain one ISO-3 code");
        }
        if (nickname != null && nickname.length() > 255) {
            throw new IllegalArgumentException("nickname must not exceed 255 characters");
        }
    }

    private static String normalizeCode(String value) {
        String normalized = normalizeOptional(value);
        return normalized == null ? null : normalized.toUpperCase(Locale.ROOT);
    }

    private static String normalizeOptional(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
