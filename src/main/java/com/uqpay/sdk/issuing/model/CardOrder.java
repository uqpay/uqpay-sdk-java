package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardOrder {

    @JsonProperty("card_id")
    private String cardId; // UUID

    @JsonProperty("card_order_id")
    private String cardOrderId; // UUID

    @JsonProperty("order_type")
    private String orderType; // CARD_CREATE | CARD_RECHARGE | CARD_WITHDRAW | CARD_UPDATE

    @JsonProperty("amount")
    private Double amount; // recharge/withdraw amount

    @JsonProperty("card_currency")
    private String cardCurrency; // SGD or USD

    @JsonProperty("create_time")
    private String createTime; // ISO 8601

    @JsonProperty("update_time")
    private String updateTime; // ISO 8601

    @JsonProperty("complete_time")
    private String completeTime; // ISO 8601

    @JsonProperty("order_status")
    private String orderStatus; // PENDING | PROCESSING | SUCCESS | FAILED

    public CardOrder() {
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardOrderId() {
        return cardOrderId;
    }

    public void setCardOrderId(String cardOrderId) {
        this.cardOrderId = cardOrderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCardCurrency() {
        return cardCurrency;
    }

    public void setCardCurrency(String cardCurrency) {
        this.cardCurrency = cardCurrency;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
