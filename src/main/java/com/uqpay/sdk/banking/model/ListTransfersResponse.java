package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListTransfersResponse {

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_items")
    private int totalItems;

    @JsonProperty("data")
    private List<Transfer> data;

    public ListTransfersResponse() {
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Transfer> getData() {
        return data;
    }

    public void setData(List<Transfer> data) {
        this.data = data;
    }
}
