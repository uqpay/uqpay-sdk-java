package com.uqpay.sdk.issuing.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ListTransactionsRequest {

    private int pageSize; // Required. Range: 10-100
    private int pageNumber; // Required. Starts from 1
    private String cardId; // Optional. UUID v4 of the card
    private String startTime; // Optional. ISO 8601 format, max 90-day interval with endTime
    private String endTime; // Optional. ISO 8601 format, max 90-day interval with startTime

    public ListTransactionsRequest() {
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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
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

    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        if (pageSize > 0) {
            sb.append("page_size=").append(pageSize).append("&");
        }
        if (pageNumber > 0) {
            sb.append("page_number=").append(pageNumber).append("&");
        }
        if (cardId != null && !cardId.isEmpty()) {
            sb.append("card_id=").append(cardId).append("&");
        }
        if (startTime != null && !startTime.isEmpty()) {
            sb.append("start_time=").append(encodeParam(startTime)).append("&");
        }
        if (endTime != null && !endTime.isEmpty()) {
            sb.append("end_time=").append(encodeParam(endTime)).append("&");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '&') {
            sb.setLength(sb.length() - 1);
        }
        return sb.length() > 0 ? "?" + sb : "";
    }

    private static String encodeParam(String value) {
        try {
            return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
