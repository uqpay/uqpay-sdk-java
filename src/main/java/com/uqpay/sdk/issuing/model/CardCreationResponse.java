package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardCreationResponse {

    @JsonProperty("card_id")
    private String cardId; // UUID

    @JsonProperty("card_order_id")
    private String cardOrderId; // UUID

    @JsonProperty("create_time")
    private String createTime; // ISO 8601, e.g. "2024-03-21T17:17:32+08:00"

    @JsonProperty("card_status")
    private String cardStatus; // PENDING | ACTIVE | FROZEN | BLOCKED | CANCELLED | LOST | STOLEN | FAILED

    @JsonProperty("order_status")
    private String orderStatus; // PENDING | PROCESSING | SUCCESS | FAILED

    @JsonProperty("cardholder_status")
    private String cardholderStatus;

    @JsonProperty("message")
    private String message;

    public CardCreationResponse() {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getCardholderStatus() {
        return cardholderStatus;
    }

    public void setCardholderStatus(String cardholderStatus) {
        this.cardholderStatus = cardholderStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
