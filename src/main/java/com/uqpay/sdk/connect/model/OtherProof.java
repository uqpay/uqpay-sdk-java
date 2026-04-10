package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an other proof document entry.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OtherProof {

    @JsonProperty("type")
    private String type;

    @JsonProperty("doc_str")
    private String docStr;

    public OtherProof() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocStr() {
        return docStr;
    }

    public void setDocStr(String docStr) {
        this.docStr = docStr;
    }
}
