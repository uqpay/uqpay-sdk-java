package com.uqpay.sdk.payment.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ListRefundsRequest {

    private int pageSize; // Required. Max items per page, range: 1-100
    private int pageNumber; // Required. Page number to retrieve, min: 1
    private String paymentIntentId; // Optional. Filter by payment intent ID
    private String status; // Optional. Filter by refund status: INITIATED, PROCESSING, SUCCEEDED, FAILED, REVERSAL_INITIATED, REVERSAL_PROCESSING, REVERSAL_SUCCEEDED
    private String startTime; // Optional. ISO 8601 datetime, default range is 1 month
    private String endTime; // Optional. ISO 8601 datetime, max range of 3 months
    private String merchantOrderId; // Optional. Merchant reference ID from merchant's system

    public ListRefundsRequest() {
    }

    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public int getPageNumber() { return pageNumber; }
    public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }

    public String getPaymentIntentId() { return paymentIntentId; }
    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getMerchantOrderId() { return merchantOrderId; }
    public void setMerchantOrderId(String merchantOrderId) { this.merchantOrderId = merchantOrderId; }

    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        if (pageSize > 0) {
            sb.append("page_size=").append(pageSize).append("&");
        }
        if (pageNumber > 0) {
            sb.append("page_number=").append(pageNumber).append("&");
        }
        if (paymentIntentId != null && !paymentIntentId.isEmpty()) {
            sb.append("payment_intent_id=").append(encode(paymentIntentId)).append("&");
        }
        if (status != null && !status.isEmpty()) {
            sb.append("status=").append(encode(status)).append("&");
        }
        if (startTime != null && !startTime.isEmpty()) {
            sb.append("start_time=").append(encode(startTime)).append("&");
        }
        if (endTime != null && !endTime.isEmpty()) {
            sb.append("end_time=").append(encode(endTime)).append("&");
        }
        if (merchantOrderId != null && !merchantOrderId.isEmpty()) {
            sb.append("merchant_order_id=").append(encode(merchantOrderId)).append("&");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '&') {
            sb.setLength(sb.length() - 1);
        }
        return sb.length() > 0 ? "?" + sb : "";
    }

    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
