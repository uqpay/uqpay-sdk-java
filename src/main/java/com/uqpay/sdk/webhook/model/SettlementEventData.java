package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SettlementEventData {

    @JsonProperty("settlement_batch_id")
    private String settlementBatchId;

    @JsonProperty("settled_amount")
    private String settledAmount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("settled_time")
    private String settledTime;

    public SettlementEventData() {
    }

    public String getSettlementBatchId() {
        return settlementBatchId;
    }

    public void setSettlementBatchId(String settlementBatchId) {
        this.settlementBatchId = settlementBatchId;
    }

    public String getSettledAmount() {
        return settledAmount;
    }

    public void setSettledAmount(String settledAmount) {
        this.settledAmount = settledAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSettledTime() {
        return settledTime;
    }

    public void setSettledTime(String settledTime) {
        this.settledTime = settledTime;
    }
}
