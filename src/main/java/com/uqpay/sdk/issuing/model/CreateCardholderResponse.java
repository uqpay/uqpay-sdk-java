package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCardholderResponse {

    @JsonProperty("cardholder_id")
    private String cardholderId; // UUID

    @JsonProperty("cardholder_status")
    private String cardholderStatus; // FAILED | PENDING | SUCCESS | INCOMPLETE

    @JsonProperty("idv_verification_url")
    private String idvVerificationUrl;

    @JsonProperty("idv_url_expires_at")
    private String idvUrlExpiresAt;

    public CreateCardholderResponse() {
    }

    public String getCardholderId() {
        return cardholderId;
    }

    public void setCardholderId(String cardholderId) {
        this.cardholderId = cardholderId;
    }

    public String getCardholderStatus() {
        return cardholderStatus;
    }

    public void setCardholderStatus(String cardholderStatus) {
        this.cardholderStatus = cardholderStatus;
    }

    public String getIdvVerificationUrl() {
        return idvVerificationUrl;
    }

    public void setIdvVerificationUrl(String idvVerificationUrl) {
        this.idvVerificationUrl = idvVerificationUrl;
    }

    public String getIdvUrlExpiresAt() {
        return idvUrlExpiresAt;
    }

    public void setIdvUrlExpiresAt(String idvUrlExpiresAt) {
        this.idvUrlExpiresAt = idvUrlExpiresAt;
    }
}
