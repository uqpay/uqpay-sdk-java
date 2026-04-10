package com.uqpay.sdk.issuing.model;

public class ListIssuingBalancesRequest {

    // Required. Range: 10-100, default: 10
    private int pageSize;
    // Required. Min: 1, default: 1
    private int pageNumber;

    public ListIssuingBalancesRequest() {
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

    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        if (pageSize > 0) {
            sb.append("page_size=").append(pageSize).append("&");
        }
        if (pageNumber > 0) {
            sb.append("page_number=").append(pageNumber).append("&");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '&') {
            sb.setLength(sb.length() - 1);
        }
        return sb.length() > 0 ? "?" + sb : "";
    }
}
