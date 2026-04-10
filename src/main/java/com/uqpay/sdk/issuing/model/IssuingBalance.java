package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssuingBalance {

    @JsonProperty("balance_id")
    private String balanceId; // Required. Unique identifier for the account balance (UUID)

    @JsonProperty("currency")
    private String currency; // Required. ISO 4217 currency code

    @JsonProperty("available_balance")
    private String availableBalance; // Required. Funds accessible for use

    @JsonProperty("margin_balance")
    private String marginBalance; // Required. Margin balance held in the account

    @JsonProperty("frozen_balance")
    private String frozenBalance; // Required. Funds temporarily restricted

    @JsonProperty("create_time")
    private String createTime; // Required. ISO 8601 timestamp

    @JsonProperty("last_trade_time")
    private String lastTradeTime; // Required. ISO 8601 timestamp of last trade/update

    @JsonProperty("balance_status")
    private String balanceStatus; // Required. ACTIVE, PENDING, PROCESSING, or CLOSED

    public IssuingBalance() {
    }

    public String getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(String balanceId) {
        this.balanceId = balanceId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getMarginBalance() {
        return marginBalance;
    }

    public void setMarginBalance(String marginBalance) {
        this.marginBalance = marginBalance;
    }

    public String getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(String frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(String lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public String getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(String balanceStatus) {
        this.balanceStatus = balanceStatus;
    }
}
