package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChargebackAlertData {

    @JsonProperty("chargeback_id")
    private String chargebackId;

    @JsonProperty("payment_intent_id")
    private String paymentIntentId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("appeal_time")
    private String appealTime;

    public String getAppealTime() { return appealTime; }
    public void setAppealTime(String value) { this.appealTime = value; }

    @JsonProperty("response_time")
    private String responseTime;

    public String getResponseTime() { return responseTime; }
    public void setResponseTime(String value) { this.responseTime = value; }

    @JsonProperty("merchant_order_id")
    private String merchantOrderId;

    public String getMerchantOrderId() { return merchantOrderId; }
    public void setMerchantOrderId(String value) { this.merchantOrderId = value; }

    @JsonProperty("order_amount")
    private String orderAmount;

    public String getOrderAmount() { return orderAmount; }
    public void setOrderAmount(String value) { this.orderAmount = value; }

    @JsonProperty("order_currency")
    private String orderCurrency;

    public String getOrderCurrency() { return orderCurrency; }
    public void setOrderCurrency(String value) { this.orderCurrency = value; }

    @JsonProperty("order_status")
    private String orderStatus;

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String value) { this.orderStatus = value; }

    @JsonProperty("alert_amount")
    private String alertAmount;

    public String getAlertAmount() { return alertAmount; }
    public void setAlertAmount(String value) { this.alertAmount = value; }

    @JsonProperty("alert_currency")
    private String alertCurrency;

    public String getAlertCurrency() { return alertCurrency; }
    public void setAlertCurrency(String value) { this.alertCurrency = value; }

    @JsonProperty("alert_status")
    private String alertStatus;

    public String getAlertStatus() { return alertStatus; }
    public void setAlertStatus(String value) { this.alertStatus = value; }

    @JsonProperty("alert_type")
    private String alertType;

    public String getAlertType() { return alertType; }
    public void setAlertType(String value) { this.alertType = value; }

    @JsonProperty("alert_create_time")
    private String alertCreateTime;

    public String getAlertCreateTime() { return alertCreateTime; }
    public void setAlertCreateTime(String value) { this.alertCreateTime = value; }

    @JsonProperty("alert_update_time")
    private String alertUpdateTime;

    public String getAlertUpdateTime() { return alertUpdateTime; }
    public void setAlertUpdateTime(String value) { this.alertUpdateTime = value; }

    @JsonProperty("appeal_status")
    private String appealStatus;

    public String getAppealStatus() { return appealStatus; }
    public void setAppealStatus(String value) { this.appealStatus = value; }

    @JsonProperty("response_status")
    private String responseStatus;

    public String getResponseStatus() { return responseStatus; }
    public void setResponseStatus(String value) { this.responseStatus = value; }

    @JsonProperty("transaction_time")
    private String transactionTime;

    public String getTransactionTime() { return transactionTime; }
    public void setTransactionTime(String value) { this.transactionTime = value; }

    public ChargebackAlertData() {
    }

    public String getChargebackId() {
        return chargebackId;
    }

    public void setChargebackId(String chargebackId) {
        this.chargebackId = chargebackId;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
