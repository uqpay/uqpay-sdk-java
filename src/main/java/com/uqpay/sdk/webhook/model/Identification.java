package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Identification {

    @JsonProperty("type")
    private String type;

    @JsonProperty("id_number")
    private String idNumber;

    @JsonProperty("documents")
    private IdentificationDocuments documents;

    @JsonProperty("identification_expiry_date")
    private String identificationExpiryDate;

    @JsonProperty("identification_issue_date")
    private String identificationIssueDate;

    public Identification() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public IdentificationDocuments getDocuments() {
        return documents;
    }

    public void setDocuments(IdentificationDocuments documents) {
        this.documents = documents;
    }

    public String getIdentificationExpiryDate() {
        return identificationExpiryDate;
    }

    public void setIdentificationExpiryDate(String identificationExpiryDate) {
        this.identificationExpiryDate = identificationExpiryDate;
    }

    public String getIdentificationIssueDate() {
        return identificationIssueDate;
    }

    public void setIdentificationIssueDate(String identificationIssueDate) {
        this.identificationIssueDate = identificationIssueDate;
    }
}
