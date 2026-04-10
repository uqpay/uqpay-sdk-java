package com.uqpay.sdk.connect.model;

/**
 * Represents a request to list accounts with pagination.
 */
public class ListAccountsRequest {

    private int pageSize; // Required. Number of items per page, range: 10-100, default 10
    private int pageNumber; // Required. Page number to retrieve, starts at 1

    public ListAccountsRequest() {
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

    /**
     * Builds the query string for this request.
     *
     * @return the query string, or empty string if no filters set
     */
    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        if (pageSize > 0) {
            sb.append("page_size=").append(pageSize).append("&");
        }
        if (pageNumber > 0) {
            sb.append("page_number=").append(pageNumber).append("&");
        }
        // Remove trailing '&'
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '&') {
            sb.setLength(sb.length() - 1);
        }
        return sb.length() > 0 ? "?" + sb : "";
    }
}
