package com.uqpay.sdk.supporting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DownloadLinksResponse {

    @JsonProperty("files")
    private List<FileDownloadInfo> files; // Optional. List of file objects with download information

    @JsonProperty("absent_files")
    private List<String> absentFiles; // Optional. List of requested file IDs (UUID) that could not be found

    public DownloadLinksResponse() {
    }

    public List<FileDownloadInfo> getFiles() {
        return files;
    }

    public void setFiles(List<FileDownloadInfo> files) {
        this.files = files;
    }

    public List<String> getAbsentFiles() {
        return absentFiles;
    }

    public void setAbsentFiles(List<String> absentFiles) {
        this.absentFiles = absentFiles;
    }
}
