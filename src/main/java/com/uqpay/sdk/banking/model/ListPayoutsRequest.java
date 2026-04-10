package com.uqpay.sdk.banking.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ListPayoutsRequest {

    private int pageSize; // Required. Items per page, range: 10-100
    private int pageNumber; // Required. Page number to retrieve, must be >= 1
    private String startTime; // Optional. Filter start time (inclusive), ISO 8601 format. Defaults to 30 days ago if omitted
    private String endTime; // Optional. Filter end time (inclusive), ISO 8601 format. Defaults to now if omitted
    private String payoutStatus; // Optional. Filter by status: READY_TO_SEND, PENDING, REJECTED, FAILED, or COMPLETED

    public ListPayoutsRequest() {
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

    public String getPayoutStatus() {
        return payoutStatus;
    }

    public void setPayoutStatus(String payoutStatus) {
        this.payoutStatus = payoutStatus;
    }

    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        if (pageSize > 0) {
            sb.append("page_size=").append(pageSize).append("&");
        }
        if (pageNumber > 0) {
            sb.append("page_number=").append(pageNumber).append("&");
        }
        if (startTime != null && !startTime.isEmpty()) {
            sb.append("start_time=").append(encodeParam(startTime)).append("&");
        }
        if (endTime != null && !endTime.isEmpty()) {
            sb.append("end_time=").append(encodeParam(endTime)).append("&");
        }
        if (payoutStatus != null && !payoutStatus.isEmpty()) {
            sb.append("payout_status=").append(payoutStatus).append("&");
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
