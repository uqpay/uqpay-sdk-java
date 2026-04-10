package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTransferRequest {

    // Required. UUID of the source account
    @JsonProperty("source_account_id")
    private String sourceAccountId;

    // Required. UUID of the destination account
    @JsonProperty("target_account_id")
    private String targetAccountId;

    // Required. ISO 4217 currency code, e.g. "USD"
    @JsonProperty("currency")
    private String currency;

    // Required. Transfer amount as string, e.g. "1000"
    @JsonProperty("amount")
    private String amount;

    // Required. Reason for the transfer
    @JsonProperty("reason")
    private String reason;

    public CreateTransferRequest() {
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(String targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
