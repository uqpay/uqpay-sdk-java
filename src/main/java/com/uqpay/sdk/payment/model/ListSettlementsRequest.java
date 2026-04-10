package com.uqpay.sdk.payment.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ListSettlementsRequest {

    private String settledStartTime; // Optional. Inclusive start date, format: YYYY-MM-DD (UTC+8)
    private String settledEndTime; // Optional. Inclusive end date, format: YYYY-MM-DD (UTC+8)
    private int pageSize; // Required. Max items per page, range: 1-100
    private int pageNumber; // Required. Page number to retrieve, min: 1
    private String paymentIntentId; // Optional. Filter by payment intent ID
    private String settlementBatchId; // Optional. Filter by settlement batch ID

    public ListSettlementsRequest() {
    }

    public String getSettledStartTime() { return settledStartTime; }
    public void setSettledStartTime(String settledStartTime) { this.settledStartTime = settledStartTime; }

    public String getSettledEndTime() { return settledEndTime; }
    public void setSettledEndTime(String settledEndTime) { this.settledEndTime = settledEndTime; }

    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public int getPageNumber() { return pageNumber; }
    public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }

    public String getPaymentIntentId() { return paymentIntentId; }
    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }

    public String getSettlementBatchId() { return settlementBatchId; }
    public void setSettlementBatchId(String settlementBatchId) { this.settlementBatchId = settlementBatchId; }

    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        if (settledStartTime != null && !settledStartTime.isEmpty()) {
            sb.append("settled_start_time=").append(encode(settledStartTime)).append("&");
        }
        if (settledEndTime != null && !settledEndTime.isEmpty()) {
            sb.append("settled_end_time=").append(encode(settledEndTime)).append("&");
        }
        if (pageSize > 0) {
            sb.append("page_size=").append(pageSize).append("&");
        }
        if (pageNumber > 0) {
            sb.append("page_number=").append(pageNumber).append("&");
        }
        if (paymentIntentId != null && !paymentIntentId.isEmpty()) {
            sb.append("payment_intent_id=").append(encode(paymentIntentId)).append("&");
        }
        if (settlementBatchId != null && !settlementBatchId.isEmpty()) {
            sb.append("settlement_batch_id=").append(encode(settlementBatchId)).append("&");
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
