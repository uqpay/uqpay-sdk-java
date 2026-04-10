package com.uqpay.sdk.banking.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ListRatesRequest {

    // Optional. List of 6-char uppercase currency pairs, e.g. "USDEUR", "USDJPY". Max 100 pairs.
    private List<String> currencyPairs;

    public ListRatesRequest() {
    }

    public List<String> getCurrencyPairs() {
        return currencyPairs;
    }

    public void setCurrencyPairs(List<String> currencyPairs) {
        this.currencyPairs = currencyPairs;
    }

    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        if (currencyPairs != null && !currencyPairs.isEmpty()) {
            String joined = String.join(",", currencyPairs);
            sb.append("currency_pairs=").append(encodeParam(joined)).append("&");
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
