package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListPaymentBalancesResponse {

    @JsonProperty("total_pages")
    private int totalPages; // Required. Total number of available pages

    @JsonProperty("total_items")
    private int totalItems; // Required. Total count of available items

    @JsonProperty("data")
    private List<PaymentBalance> data; // Required. List of PaymentBalance objects

    public ListPaymentBalancesResponse() {
    }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public int getTotalItems() { return totalItems; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }

    public List<PaymentBalance> getData() { return data; }
    public void setData(List<PaymentBalance> data) { this.data = data; }
}
