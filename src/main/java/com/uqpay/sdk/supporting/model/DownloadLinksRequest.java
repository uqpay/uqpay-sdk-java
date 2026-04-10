package com.uqpay.sdk.supporting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DownloadLinksRequest {

    @JsonProperty("file_ids")
    private List<String> fileIds;

    public DownloadLinksRequest() {
    }

    public DownloadLinksRequest(List<String> fileIds) {
        this.fileIds = fileIds;
    }

    public List<String> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }
}
