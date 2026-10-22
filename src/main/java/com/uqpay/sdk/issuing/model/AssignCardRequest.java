package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignCardRequest {

    @JsonProperty("cardholder_id")
    private String cardholderId; // Optional for Business Visa: defaults to the company authorized person.

    @JsonProperty("card_number")
    private String cardNumber; // required; full card number

    @JsonProperty("card_currency")
    private String cardCurrency; // required; SGD or USD

    @JsonProperty("card_mode")
    private String cardMode; // required; SINGLE | SHARE

    /** Personal Visa requires card_sku_code (exactly 12 characters); omit for other products. */
    @JsonProperty("metadata")
    private Map<String, String> metadata;

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public AssignCardRequest() {
    }

    public String getCardholderId() {
        return cardholderId;
    }

    public void setCardholderId(String cardholderId) {
        this.cardholderId = cardholderId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCurrency() {
        return cardCurrency;
    }

    public void setCardCurrency(String cardCurrency) {
        this.cardCurrency = cardCurrency;
    }

    public String getCardMode() {
        return cardMode;
    }

    public void setCardMode(String cardMode) {
        this.cardMode = cardMode;
    }
}
