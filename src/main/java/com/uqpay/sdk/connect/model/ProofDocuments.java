package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents proof documents for a sub-account creation request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProofDocuments {

    @JsonProperty("proof_of_address")
    private List<String> proofOfAddress;

    @JsonProperty("source_of_funds")
    private List<String> sourceOfFunds;

    @JsonProperty("proof_of_position_and_income")
    private List<String> proofOfPositionAndIncome;

    @JsonProperty("other_proof")
    private List<OtherProof> otherProof;

    public ProofDocuments() {
    }

    public List<String> getProofOfAddress() {
        return proofOfAddress;
    }

    public void setProofOfAddress(List<String> proofOfAddress) {
        this.proofOfAddress = proofOfAddress;
    }

    public List<String> getSourceOfFunds() {
        return sourceOfFunds;
    }

    public void setSourceOfFunds(List<String> sourceOfFunds) {
        this.sourceOfFunds = sourceOfFunds;
    }

    public List<String> getProofOfPositionAndIncome() {
        return proofOfPositionAndIncome;
    }

    public void setProofOfPositionAndIncome(List<String> proofOfPositionAndIncome) {
        this.proofOfPositionAndIncome = proofOfPositionAndIncome;
    }

    public List<OtherProof> getOtherProof() {
        return otherProof;
    }

    public void setOtherProof(List<OtherProof> otherProof) {
        this.otherProof = otherProof;
    }
}
