package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a single additional document entry.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalDocumentEntry {

    @JsonProperty("profile_key")
    private String profileKey;

    @JsonProperty("doc_str")
    private String docStr;

    public AdditionalDocumentEntry() {
    }

    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    public String getDocStr() {
        return docStr;
    }

    public void setDocStr(String docStr) {
        this.docStr = docStr;
    }
}
