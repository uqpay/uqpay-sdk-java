package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CapturePaymentIntentRequest {

    @JsonProperty("amount_to_capture")
    private String amountToCapture; // optional, partial capture amount if different from original

    public CapturePaymentIntentRequest() {
    }

    public String getAmountToCapture() { return amountToCapture; }
    public void setAmountToCapture(String amountToCapture) { this.amountToCapture = amountToCapture; }
}
