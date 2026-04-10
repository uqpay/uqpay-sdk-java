package com.uqpay.sdk.issuing.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ListCardholdersRequest {

    private int pageSize; // required; min 10, max 100, default 10
    private int pageNumber; // required; min 1, default 1
    private String cardholderStatus; // optional; PENDING | SUCCESS | INCOMPLETE | FAILED

    public ListCardholdersRequest() {
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

    public String getCardholderStatus() {
        return cardholderStatus;
    }

    public void setCardholderStatus(String cardholderStatus) {
        this.cardholderStatus = cardholderStatus;
    }

    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        if (pageSize > 0) {
            sb.append("page_size=").append(pageSize).append("&");
        }
        if (pageNumber > 0) {
            sb.append("page_number=").append(pageNumber).append("&");
        }
        if (cardholderStatus != null && !cardholderStatus.isEmpty()) {
            sb.append("cardholder_status=").append(encode(cardholderStatus)).append("&");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '&') {
            sb.setLength(sb.length() - 1);
        }
        return sb.length() > 0 ? "?" + sb : "";
    }

    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            return value;
        }
    }
}
