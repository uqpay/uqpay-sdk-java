package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateReportRequest {

    // Required. Allowed values: SETTLEMENT, LEDGER
    @JsonProperty("report_type")
    private String reportType;

    // Required. Earliest transaction time, ISO 8601 format (e.g., 2024-03-21T17:17:32+08:00)
    @JsonProperty("start_time")
    private String startTime;

    // Required. Latest transaction time, ISO 8601 format (e.g., 2024-03-21T17:17:32+08:00)
    @JsonProperty("end_time")
    private String endTime;

    public CreateReportRequest() {
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
