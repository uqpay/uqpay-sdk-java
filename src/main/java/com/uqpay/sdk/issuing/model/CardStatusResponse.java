package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardStatusResponse {

    @JsonProperty("card_id")
    private String cardId; // UUID

    @JsonProperty("card_order_id")
    private String cardOrderId; // UUID

    @JsonProperty("order_status")
    private String orderStatus; // PENDING | PROCESSING | SUCCESS | FAILED

    @JsonProperty("update_reason")
    private String updateReason; // max 100 chars

    public CardStatusResponse() {
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUpdateReason() {
        return updateReason;
    }

    public void setUpdateReason(String updateReason) {
        this.updateReason = updateReason;
    }
}
