package com.uqpay.sdk.banking.model;

public class ListVirtualAccountsRequest {

    // Required. Number of items per page, range: 10-100
    private int pageSize;
    // Required. Page number to retrieve, starts at 1
    private int pageNumber;
    // Optional. ISO 4217 currency codes, comma-separated (e.g. "USD,SGD")
    private String currency;

    public ListVirtualAccountsRequest() {
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        if (pageSize > 0) {
            sb.append("page_size=").append(pageSize).append("&");
        }
        if (pageNumber > 0) {
            sb.append("page_number=").append(pageNumber).append("&");
        }
        if (currency != null && !currency.isEmpty()) {
            sb.append("currency=").append(currency).append("&");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '&') {
            sb.setLength(sb.length() - 1);
        }
        return sb.length() > 0 ? "?" + sb : "";
    }
}
