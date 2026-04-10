package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateIssuingTransferResponse {

    @JsonProperty("transfer_id")
    private String transferId; // UUID. Unique identifier for the created transfer

    @JsonProperty("short_transaction_id")
    private String shortTransactionId;

    public CreateIssuingTransferResponse() {
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getShortTransactionId() {
        return shortTransactionId;
    }

    public void setShortTransactionId(String shortTransactionId) {
        this.shortTransactionId = shortTransactionId;
    }
}
