package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Balance {

    @JsonProperty("balance_id")
    private String balanceId; // Required. Unique identifier for the account balance. UUID format.

    @JsonProperty("currency")
    private String currency; // Required. Currency code of the balance. ISO 4217 (e.g., USD).

    @JsonProperty("available_balance")
    private String availableBalance; // Required. Funds accessible for transactions.

    @JsonProperty("margin_balance")
    private String marginBalance; // Required. Margin balance amount.

    @JsonProperty("frozen_balance")
    private String frozenBalance; // Required. Frozen balance amount.

    @JsonProperty("prepaid_balance")
    private String prepaidBalance; // Prepaid balance amount.

    @JsonProperty("create_time")
    private String createTime; // Balance creation time.

    @JsonProperty("last_trade_time")
    private String lastTradeTime; // Last trade time.

    @JsonProperty("balance_status")
    private String balanceStatus; // Balance status.

    public Balance() {
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

    public String getPrepaidBalance() {
        return prepaidBalance;
    }

    public void setPrepaidBalance(String prepaidBalance) {
        this.prepaidBalance = prepaidBalance;
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
