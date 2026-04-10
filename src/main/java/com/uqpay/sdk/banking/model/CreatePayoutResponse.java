package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePayoutResponse {

    @JsonProperty("payout_id")
    private String payoutId; // Required. UUID of the created payout

    @JsonProperty("short_reference_id")
    private String shortReferenceId; // Required. System-generated reference, e.g. "P220406-LLCVLRM"

    @JsonProperty("payout_status")
    private String payoutStatus; // Required. READY_TO_SEND, PENDING, REJECTED, FAILED, or COMPLETED

    public CreatePayoutResponse() {
    }

    public String getPayoutId() {
        return payoutId;
    }

    public void setPayoutId(String payoutId) {
        this.payoutId = payoutId;
    }

    public String getShortReferenceId() {
        return shortReferenceId;
    }

    public void setShortReferenceId(String shortReferenceId) {
        this.shortReferenceId = shortReferenceId;
    }

    public String getPayoutStatus() {
        return payoutStatus;
    }

    public void setPayoutStatus(String payoutStatus) {
        this.payoutStatus = payoutStatus;
    }

}
