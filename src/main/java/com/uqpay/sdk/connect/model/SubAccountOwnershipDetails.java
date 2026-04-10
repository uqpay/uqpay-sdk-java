package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents ownership details for a company sub-account creation request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubAccountOwnershipDetails {

    @JsonProperty("representatives")
    private List<SubAccountRepresentative> representatives;

    @JsonProperty("shareholder_docs")
    private List<String> shareholderDocs;

    public SubAccountOwnershipDetails() {
    }

    public List<SubAccountRepresentative> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(List<SubAccountRepresentative> representatives) {
        this.representatives = representatives;
    }

    public List<String> getShareholderDocs() {
        return shareholderDocs;
    }

    public void setShareholderDocs(List<String> shareholderDocs) {
        this.shareholderDocs = shareholderDocs;
    }
}
