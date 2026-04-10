package com.uqpay.sdk.issuing.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ListIssuingBalanceTransactionsRequest {

    // Required. Range: 10-100, default: 10
    private int pageSize;
    // Required. Min: 1, default: 1
    private int pageNumber;
    // Optional. ISO 8601 format. Max interval with endTime: 90 days
    private String startTime;
    // Optional. ISO 8601 format. Max interval with startTime: 90 days
    private String endTime;
    // Optional. e.g., DEPOSIT, TRANSFER_IN, TRANSFER_OUT, ISSUING_AUTHORIZATION, ISSUING_REVERSAL, ISSUING_REFUND,
    // CARD_RECHARGE, CARD_WITHDRAW, SETTLEMENT_DEBIT, SETTLEMENT_CREDIT, SETTLEMENT_REVERSAL, FEE, REFUND,
    // ADJUSTMENT, FUNDS_TRANSFER_IN, FUNDS_TRANSFER_OUT, FEE_REFUND, FEE_DEDUCTION, MARGIN_PAYMENT, MARGIN_REFUND, OTHER
    private String transactionType;
    // Optional. COMPLETED, PENDING, or FAILED
    private String transactionStatus;
    // Optional. ISO 4217 currency code
    private String currency;
    // Optional. UUID filter for a specific transaction
    private String transactionId;

    public ListIssuingBalanceTransactionsRequest() {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
        if (transactionType != null && !transactionType.isEmpty()) {
            sb.append("transaction_type=").append(encodeParam(transactionType)).append("&");
        }
        if (transactionStatus != null && !transactionStatus.isEmpty()) {
            sb.append("transaction_status=").append(encodeParam(transactionStatus)).append("&");
        }
        if (currency != null && !currency.isEmpty()) {
            sb.append("currency=").append(encodeParam(currency)).append("&");
        }
        if (transactionId != null && !transactionId.isEmpty()) {
            sb.append("transaction_id=").append(encodeParam(transactionId)).append("&");
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
