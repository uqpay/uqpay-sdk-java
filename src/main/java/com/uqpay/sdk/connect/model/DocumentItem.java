package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a document item for account verification.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentItem {

    @JsonProperty("type")
    private String type;

    @JsonProperty("front")
    private String front;

    @JsonProperty("back")
    private String back;

    @JsonProperty("front_file_id")
    private String frontFileId;

    @JsonProperty("back_file_id")
    private String backFileId;

    public DocumentItem() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
