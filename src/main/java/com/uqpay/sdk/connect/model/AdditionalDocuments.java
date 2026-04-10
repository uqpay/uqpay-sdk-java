package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents additional documents for a company sub-account creation request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalDocuments {

    @JsonProperty("required_docs")
    private List<AdditionalDocumentEntry> requiredDocs;

    @JsonProperty("option_docs")
    private List<AdditionalDocumentEntry> optionDocs;

    public AdditionalDocuments() {
    }

    public List<AdditionalDocumentEntry> getRequiredDocs() {
        return requiredDocs;
    }

    public void setRequiredDocs(List<AdditionalDocumentEntry> requiredDocs) {
        this.requiredDocs = requiredDocs;
    }

    public List<AdditionalDocumentEntry> getOptionDocs() {
        return optionDocs;
    }

    public void setOptionDocs(List<AdditionalDocumentEntry> optionDocs) {
        this.optionDocs = optionDocs;
    }
}
