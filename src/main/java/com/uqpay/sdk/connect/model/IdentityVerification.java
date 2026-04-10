package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents identity verification details for a sub-account creation request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentityVerification {

    @JsonProperty("identification_type")
    private String identificationType;

    @JsonProperty("identification_value")
    private String identificationValue;

    @JsonProperty("identity_docs")
    private List<String> identityDocs;

    @JsonProperty("face_docs")
    private List<String> faceDocs;

    public IdentityVerification() {
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationValue() {
        return identificationValue;
    }

    public void setIdentificationValue(String identificationValue) {
        this.identificationValue = identificationValue;
    }

    public List<String> getIdentityDocs() {
        return identityDocs;
    }

    public void setIdentityDocs(List<String> identityDocs) {
        this.identityDocs = identityDocs;
    }

    public List<String> getFaceDocs() {
        return faceDocs;
    }

    public void setFaceDocs(List<String> faceDocs) {
        this.faceDocs = faceDocs;
    }
}
