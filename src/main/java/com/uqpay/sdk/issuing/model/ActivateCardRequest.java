package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivateCardRequest {

    @JsonProperty("card_id")
    private String cardId; // required; UUID

    @JsonProperty("activation_code")
    private String activationCode; // required; physical card activation code

    @JsonProperty("pin")
    private String pin; // required; 6-digit numeric value

    @JsonProperty("no_pin_payment_amount")
    private Double noPinPaymentAmount; // optional; >= 0, defaults to 200 SGD; set 0 to disable

    public ActivateCardRequest() {
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Double getNoPinPaymentAmount() {
        return noPinPaymentAmount;
    }

    public void setNoPinPaymentAmount(Double noPinPaymentAmount) {
        this.noPinPaymentAmount = noPinPaymentAmount;
    }
}
