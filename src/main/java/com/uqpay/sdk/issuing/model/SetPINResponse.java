package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetPINResponse {

    @JsonProperty("request_status")
    private String requestStatus; // SUCCESS means accepted, not completed.

    public SetPINResponse() {
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @JsonProperty("card_id")
    private String cardId;

    public String getCardId() { return cardId; }

    public void setCardId(String cardId) { this.cardId = cardId; }

    @JsonProperty("card_order_id")
    private String cardOrderId;

    public String getCardOrderId() { return cardOrderId; }

    public void setCardOrderId(String cardOrderId) { this.cardOrderId = cardOrderId; }

    @JsonProperty("order_status")
    private String orderStatus; // PROCESSING at acceptance.

    public String getOrderStatus() { return orderStatus; }

    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    @JsonProperty("create_time")
    private String createTime;

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }
}
