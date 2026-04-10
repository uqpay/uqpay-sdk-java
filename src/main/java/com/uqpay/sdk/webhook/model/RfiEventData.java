package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RfiEventData {

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("rfi_type")
    private String rfiType;

    @JsonProperty("rfi_status")
    private String rfiStatus;

    @JsonProperty("required_documents")
    private List<String> requiredDocuments;

    public RfiEventData() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRfiType() {
        return rfiType;
    }

    public void setRfiType(String rfiType) {
        this.rfiType = rfiType;
    }

    public String getRfiStatus() {
        return rfiStatus;
    }

    public void setRfiStatus(String rfiStatus) {
        this.rfiStatus = rfiStatus;
    }

    public List<String> getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(List<String> requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }
}
