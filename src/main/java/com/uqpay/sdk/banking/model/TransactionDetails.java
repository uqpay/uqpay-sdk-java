package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDetails {

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("processing_time")
    private String processingTime;

    @JsonProperty("settlement_time")
    private String settlementTime;

    @JsonProperty("exchange_rate")
    private String exchangeRate;

    @JsonProperty("processor_response")
    private String processorResponse;

    public TransactionDetails() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }

    public String getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(String settlementTime) {
        this.settlementTime = settlementTime;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getProcessorResponse() {
        return processorResponse;
    }

    public void setProcessorResponse(String processorResponse) {
        this.processorResponse = processorResponse;
    }
}
