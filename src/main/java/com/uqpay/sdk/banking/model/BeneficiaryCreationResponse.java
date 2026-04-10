package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeneficiaryCreationResponse {

    @JsonProperty("beneficiary_id")
    private String beneficiaryId; // UUID v4 of the created beneficiary

    @JsonProperty("short_reference_id")
    private String shortReferenceId; // system-generated reference, e.g. "P220406-LLCVLRM"

    public BeneficiaryCreationResponse() {
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getShortReferenceId() {
        return shortReferenceId;
    }

    public void setShortReferenceId(String shortReferenceId) {
        this.shortReferenceId = shortReferenceId;
    }
}
