package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePayoutRequest {

    @JsonProperty("payout_amount")
    private String amount; // Required. The amount to be withdrawn through this payout

    @JsonProperty("payout_currency")
    private String currency; // Required. ISO 4217 three-letter currency code

    @JsonProperty("statement_descriptor")
    private String statementDescriptor; // Optional. Reference displayed on recipient's bank statement, max 15 chars

    @JsonProperty("internal_note")
    private String internalNote; // Optional. Internal payout remark information

    @JsonProperty("payout_account_id")
    private String payoutAccountId; // optional

    public CreatePayoutRequest() {
    }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getStatementDescriptor() { return statementDescriptor; }
    public void setStatementDescriptor(String statementDescriptor) { this.statementDescriptor = statementDescriptor; }

    public String getInternalNote() { return internalNote; }
    public void setInternalNote(String internalNote) { this.internalNote = internalNote; }

    public String getPayoutAccountId() { return payoutAccountId; }
    public void setPayoutAccountId(String payoutAccountId) { this.payoutAccountId = payoutAccountId; }
}
