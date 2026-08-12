package com.uqpay.sdk.banking.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class ListVirtualAccountApplicationsRequest {
    private int pageNumber;
    private int pageSize;
    private VirtualAccountApplicationStatus status;
    private String country;
    private String currency;

    public int getPageNumber() { return pageNumber; }
    public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public VirtualAccountApplicationStatus getStatus() { return status; }
    public void setStatus(VirtualAccountApplicationStatus status) { this.status = status; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = normalizeCode(country); }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = normalizeCode(currency); }

    public String toQueryString() {
        if (pageNumber < 1) throw new IllegalArgumentException("pageNumber must be at least 1");
        if (pageSize < 1 || pageSize > 100) throw new IllegalArgumentException("pageSize must be between 1 and 100");
        StringBuilder query = new StringBuilder("?page_number=").append(pageNumber)
                .append("&page_size=").append(pageSize);
        if (status != null) query.append("&status=").append(status);
        if (country != null) query.append("&country=").append(encode(country));
        if (currency != null) query.append("&currency=").append(encode(currency));
        return query.toString();
    }

    private static String normalizeCode(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
