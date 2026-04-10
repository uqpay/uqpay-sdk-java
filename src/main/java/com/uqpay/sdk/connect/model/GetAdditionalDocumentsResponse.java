package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents a response containing additional required documents for an account.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAdditionalDocumentsResponse {

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("documents")
    private List<Document> documents;

    public GetAdditionalDocumentsResponse() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
