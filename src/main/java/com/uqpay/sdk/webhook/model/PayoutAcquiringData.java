package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayoutAcquiringData {

    @JsonProperty("payout_id")
    private String payoutId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("payout_status")
    private String payoutStatus;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("complete_time")
    private String completeTime;

    public String getCompleteTime() { return completeTime; }
    public void setCompleteTime(String value) { this.completeTime = value; }

    @JsonProperty("account_id")
    private String accountId;

    public String getAccountId() { return accountId; }
    public void setAccountId(String value) { this.accountId = value; }

    @JsonProperty("account_name")
    private String accountName;

    public String getAccountName() { return accountName; }
    public void setAccountName(String value) { this.accountName = value; }

    @JsonProperty("payout_amount")
    private java.math.BigDecimal payoutAmount;

    public java.math.BigDecimal getPayoutAmount() { return payoutAmount; }
    public void setPayoutAmount(java.math.BigDecimal value) { this.payoutAmount = value; }

    @JsonProperty("payout_currency")
    private String payoutCurrency;

    public String getPayoutCurrency() { return payoutCurrency; }
    public void setPayoutCurrency(String value) { this.payoutCurrency = value; }

    @JsonProperty("payout_reference")
    private String payoutReference;

    public String getPayoutReference() { return payoutReference; }
    public void setPayoutReference(String value) { this.payoutReference = value; }

    public PayoutAcquiringData() {
    }

    public String getPayoutId() {
        return payoutId;
    }

    public void setPayoutId(String payoutId) {
        this.payoutId = payoutId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayoutStatus() {
        return payoutStatus;
    }

    public void setPayoutStatus(String payoutStatus) {
        this.payoutStatus = payoutStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
