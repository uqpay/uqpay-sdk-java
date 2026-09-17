package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetPINRequest {

    @JsonProperty("card_id")
    private String cardId; // required; UUID

    @JsonProperty("pin")
    private String pin; // required; 6-digit numeric value

    public SetPINRequest() {
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @JsonProperty("type")
    private String type; // SET (default), RESET, UPDATE

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    @JsonProperty("old_pin")
    private String oldPin; // Six digits; required for UPDATE, prohibited otherwise.

    public String getOldPin() { return oldPin; }

    public void setOldPin(String oldPin) { this.oldPin = oldPin; }
}
