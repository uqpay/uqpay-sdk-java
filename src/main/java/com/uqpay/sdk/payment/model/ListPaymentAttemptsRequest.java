package com.uqpay.sdk.payment.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ListPaymentAttemptsRequest {

    private int pageSize; // Required. Max items per page, range: 1-100
    private int pageNumber; // Required. Page number to retrieve, min: 1
    private String paymentIntentId; // Optional. Filter by payment intent ID
    private String status; // Optional. Filter by attempt status: INITIATED, AUTHENTICATION_REDIRECTED, PENDING_AUTHORIZATION, AUTHORIZED, CAPTURE_REQUESTED, SETTLED, SUCCEEDED, CANCELLED, EXPIRED, FAILED

    public ListPaymentAttemptsRequest() {
    }

    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public int getPageNumber() { return pageNumber; }
    public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }

    public String getPaymentIntentId() { return paymentIntentId; }
    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

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
            sb.append("attempt_status=").append(encode(status)).append("&");
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
