package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateVirtualAccountResponse {

    // Always returns "SUCCESS"; actual status is delivered via webhooks
    @JsonProperty("message")
    private String message;

    public CreateVirtualAccountResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
