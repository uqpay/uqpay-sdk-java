package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateIssuingTransferRequest {

    @JsonProperty("source_account_id")
    private String sourceAccountId; // Required. UUID of the account initiating the transfer

    @JsonProperty("destination_account_id")
    private String destinationAccountId; // Required. UUID of the account receiving the transfer

    @JsonProperty("currency")
    private String currency; // Required. Transfer currency, e.g. "SGD"

    @JsonProperty("amount")
    private String amount; // Required. Precision limited to two decimal places

    @JsonProperty("remark")
    private String remark; // Optional

    public CreateIssuingTransferRequest() {
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(String destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
