package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the nested documents structure within an Identification.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentificationDocuments {

    @JsonProperty("front")
    private String front; // Optional. Base64-encoded front image, prefixed with "data:image/...;base64,"

    @JsonProperty("back")
    private String back; // Optional. Base64-encoded back image; required for all types except PASSPORT

    @JsonProperty("front_file_id")
    private String frontFileId; // Optional. File ID of the front document (alternative to base64 front), UUID format

    @JsonProperty("back_file_id")
    private String backFileId; // Optional. File ID of the back document (alternative to base64 back), UUID format

    public IdentificationDocuments() {
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getFrontFileId() {
        return frontFileId;
    }

    public void setFrontFileId(String frontFileId) {
        this.frontFileId = frontFileId;
    }

    public String getBackFileId() {
        return backFileId;
    }

    public void setBackFileId(String backFileId) {
        this.backFileId = backFileId;
    }
}
