package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KycVerification {

    @JsonProperty("method")
    private String method;

    @JsonProperty("kyc_proof")
    private KycProof kycProof;

    public KycVerification() {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public KycProof getKycProof() {
        return kycProof;
    }

    public void setKycProof(KycProof kycProof) {
        this.kycProof = kycProof;
    }
}
