package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListCardsResponse {

    @JsonProperty("total_pages")
    private int totalPages; // total pages available

    @JsonProperty("total_items")
    private int totalItems; // total count of available items

    @JsonProperty("data")
    private List<RetrieveCardResponse> data; // sorted by creation date descending

    public ListCardsResponse() {
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

    public List<RetrieveCardResponse> getData() {
        return data;
    }

    public void setData(List<RetrieveCardResponse> data) {
        this.data = data;
    }
}
