package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignCardRequest {

    @JsonProperty("cardholder_id")
    private String cardholderId; // required; UUID

    @JsonProperty("card_number")
    private String cardNumber; // required; full card number

    @JsonProperty("card_currency")
    private String cardCurrency; // required; SGD or USD

    @JsonProperty("card_mode")
    private String cardMode; // required; SINGLE | SHARE

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
