package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentBalance {

    @JsonProperty("balance_id")
    private String balanceId; // Required. Account balance ID, UUID format

    @JsonProperty("currency")
    private String currency; // Required. ISO 4217 currency code, e.g. "SGD"

    @JsonProperty("available_balance")
    private String availableBalance; // Required. Current available balance

    @JsonProperty("payable_balance")
    private String payableBalance; // Required. Payable account balance

    @JsonProperty("pending_balance")
    private String pendingBalance; // Required. Pending balance

    @JsonProperty("reserved_balance")
    private String reservedBalance; // Required. Reserved balance

    @JsonProperty("margin_balance")
    private String marginBalance; // Required. Margin balance

    @JsonProperty("frozen_balance")
    private String frozenBalance; // Required. Frozen funds

    public PaymentBalance() {
    }

    public String getBalanceId() { return balanceId; }
    public void setBalanceId(String balanceId) { this.balanceId = balanceId; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getAvailableBalance() { return availableBalance; }
    public void setAvailableBalance(String availableBalance) { this.availableBalance = availableBalance; }

    public String getPayableBalance() { return payableBalance; }
    public void setPayableBalance(String payableBalance) { this.payableBalance = payableBalance; }

    public String getPendingBalance() { return pendingBalance; }
    public void setPendingBalance(String pendingBalance) { this.pendingBalance = pendingBalance; }

    public String getReservedBalance() { return reservedBalance; }
    public void setReservedBalance(String reservedBalance) { this.reservedBalance = reservedBalance; }

    public String getMarginBalance() { return marginBalance; }
    public void setMarginBalance(String marginBalance) { this.marginBalance = marginBalance; }

    public String getFrozenBalance() { return frozenBalance; }
    public void setFrozenBalance(String frozenBalance) { this.frozenBalance = frozenBalance; }
}
