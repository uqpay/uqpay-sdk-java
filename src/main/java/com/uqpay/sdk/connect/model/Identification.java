package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents identification details for KYC verification.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Identification {

    @JsonProperty("type")
    private String type; // Required. PASSPORT, NATIONAL_ID, or DRIVERS_LICENSE

    @JsonProperty("id_number")
    private String idNumber; // Required. The ID number on the document

    @JsonProperty("front")
    private String front; // Optional. Base64-encoded front image, prefixed with "data:image/...;base64,"

    @JsonProperty("back")
    private String back; // Optional. Base64-encoded back image; required for all types except PASSPORT

    @JsonProperty("front_file_id")
    private String frontFileId; // Optional. File ID of the front document (alternative to base64 front)

    @JsonProperty("back_file_id")
    private String backFileId; // Optional. File ID of the back document (alternative to base64 back)

    @JsonProperty("remark")
    private String remark; // Optional. Additional remarks, e.g. "Valid until 2030"

    @JsonProperty("citizenship_status")
    private String citizenshipStatus; // Optional. Citizenship status of the individual

    @JsonProperty("documents")
    private IdentificationDocuments documents; // Optional. Nested document images (alternative structure for front/back)

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCitizenshipStatus() {
        return citizenshipStatus;
    }

    public void setCitizenshipStatus(String citizenshipStatus) {
        this.citizenshipStatus = citizenshipStatus;
    }

    public IdentificationDocuments getDocuments() {
        return documents;
    }

    public void setDocuments(IdentificationDocuments documents) {
        this.documents = documents;
    }
}
