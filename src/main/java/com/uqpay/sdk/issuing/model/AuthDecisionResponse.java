package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDecisionResponse {

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("response_code")
    private String responseCode;

    @JsonProperty("partner_reference_id")
    private String partnerReferenceId;

    public AuthDecisionResponse() {
    }

    public AuthDecisionResponse(String transactionId, String responseCode) {
        this.transactionId = transactionId;
        this.responseCode = responseCode;
    }

    /**
     * Creates an approved response with response code "00".
     *
     * @param transactionId the transaction ID to approve
     * @return an approved AuthDecisionResponse
     */
    public static AuthDecisionResponse approve(String transactionId) {
        return new AuthDecisionResponse(transactionId, "00");
    }

    /**
     * Creates a declined response with the given response code.
     *
     * @param transactionId the transaction ID to decline
     * @param responseCode  the decline response code
     * @return a declined AuthDecisionResponse
     */
    public static AuthDecisionResponse decline(String transactionId, String responseCode) {
        return new AuthDecisionResponse(transactionId, responseCode);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getPartnerReferenceId() {
        return partnerReferenceId;
    }

    public void setPartnerReferenceId(String partnerReferenceId) {
        this.partnerReferenceId = partnerReferenceId;
    }
}
