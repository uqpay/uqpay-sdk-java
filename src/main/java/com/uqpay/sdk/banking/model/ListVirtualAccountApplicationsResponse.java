package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListVirtualAccountApplicationsResponse {
    @JsonProperty("total_pages") private long totalPages;
    @JsonProperty("total_items") private long totalItems;
    @JsonProperty("data") private List<VirtualAccountApplicationSummary> data;

    public long getTotalPages() { return totalPages; }
    public void setTotalPages(long totalPages) { this.totalPages = totalPages; }
    public long getTotalItems() { return totalItems; }
    public void setTotalItems(long totalItems) { this.totalItems = totalItems; }
    public List<VirtualAccountApplicationSummary> getData() { return data; }
    public void setData(List<VirtualAccountApplicationSummary> data) { this.data = data; }
}
