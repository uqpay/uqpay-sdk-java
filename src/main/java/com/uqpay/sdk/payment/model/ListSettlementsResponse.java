package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListSettlementsResponse {

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_items")
    private int totalItems;

    @JsonProperty("data")
    private List<Settlement> data;

    public ListSettlementsResponse() {
    }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public int getTotalItems() { return totalItems; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }

    public List<Settlement> getData() { return data; }
    public void setData(List<Settlement> data) { this.data = data; }
}
