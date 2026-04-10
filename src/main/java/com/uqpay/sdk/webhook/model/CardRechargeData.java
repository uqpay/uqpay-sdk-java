package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardRechargeData {

    @JsonProperty("card_id")
    private String cardId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("card_currency")
    private String cardCurrency;

    @JsonProperty("card_available_balance")
    private String cardAvailableBalance;

    @JsonProperty("card_status")
    private String cardStatus;

    @JsonProperty("order_status")
    private String orderStatus;

    @JsonProperty("complete_time")
    private String completeTime;

    @JsonProperty("update_time")
    private String updateTime;

    public CardRechargeData() {
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardCurrency() {
        return cardCurrency;
    }

    public void setCardCurrency(String cardCurrency) {
        this.cardCurrency = cardCurrency;
    }

    public String getCardAvailableBalance() {
        return cardAvailableBalance;
    }

    public void setCardAvailableBalance(String cardAvailableBalance) {
        this.cardAvailableBalance = cardAvailableBalance;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
