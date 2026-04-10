package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Identity {

    @JsonProperty("type")
    private String type;

    @JsonProperty("number")
    private String number;

    @JsonProperty("front_file")
    private String frontFile;

    @JsonProperty("back_file")
    private String backFile;

    @JsonProperty("hand_file")
    private String handFile;

    public Identity() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFrontFile() {
        return frontFile;
    }

    public void setFrontFile(String frontFile) {
        this.frontFile = frontFile;
    }

    public String getBackFile() {
        return backFile;
    }

    public void setBackFile(String backFile) {
        this.backFile = backFile;
    }

    public String getHandFile() {
        return handFile;
    }

    public void setHandFile(String handFile) {
        this.handFile = handFile;
    }
}
