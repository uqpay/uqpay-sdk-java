package com.uqpay.sdk.banking.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ListBalanceTransactionsRequest {

    // Required. Range: 1-100
    private int pageSize;
    // Required. Min: 1
    private int pageNumber;
    // Optional. ISO 8601 format - filters by create_time
    private String startTime;
    // Optional. ISO 8601 format - filters by create_time
    private String endTime;
    // Optional. ISO 4217 currency code (e.g., USD)
    private String currency;
    // Optional. ALL | PAYIN | DEPOSIT | PAYOUT | TRANSFER | CONVERSION | FEE | REFUND | ADJUSTMENT | INVOICE
    private String transactionType;
    // Optional. ALL | COMPLETED | PENDING | FAILED
    private String transactionStatus;

    public ListBalanceTransactionsRequest() {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
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
        if (currency != null && !currency.isEmpty()) {
            sb.append("currency=").append(currency).append("&");
        }
        if (transactionType != null && !transactionType.isEmpty()) {
            sb.append("transaction_type=").append(transactionType).append("&");
        }
        if (transactionStatus != null && !transactionStatus.isEmpty()) {
            sb.append("transaction_status=").append(transactionStatus).append("&");
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
