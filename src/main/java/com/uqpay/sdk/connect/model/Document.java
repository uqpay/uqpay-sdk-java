package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a required document for account verification.
 * Returned by the Get Additional Documents endpoint.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Document {

    @JsonProperty("profile_key")
    private String profileKey; // Required. Unique key representing the profile or document type

    @JsonProperty("profile_name")
    private String profileName; // Required. Description of the file

    @JsonProperty("profile_option")
    private Integer profileOption; // Required. 1 = required document, 0 = optional document

    public Document() {
    }

    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Integer getProfileOption() {
        return profileOption;
    }

    public void setProfileOption(Integer profileOption) {
        this.profileOption = profileOption;
    }
}
