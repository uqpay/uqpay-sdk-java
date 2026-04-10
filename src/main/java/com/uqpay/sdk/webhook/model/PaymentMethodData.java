package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentMethodData {

    @JsonProperty("type")
    private String type;

    @JsonProperty("card")
    private CardDetails card;

    @JsonProperty("alipaycn")
    private AlipayDetails alipayCN;

    @JsonProperty("alipayhk")
    private AlipayDetails alipayHK;

    @JsonProperty("grabpay")
    private AlipayDetails grabPay;

    @JsonProperty("wechatpay")
    private AlipayDetails weChatPay;

    public PaymentMethodData() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CardDetails getCard() {
        return card;
    }

    public void setCard(CardDetails card) {
        this.card = card;
    }

    public AlipayDetails getAlipayCN() {
        return alipayCN;
    }

    public void setAlipayCN(AlipayDetails alipayCN) {
        this.alipayCN = alipayCN;
    }

    public AlipayDetails getAlipayHK() {
        return alipayHK;
    }

    public void setAlipayHK(AlipayDetails alipayHK) {
        this.alipayHK = alipayHK;
    }

    public AlipayDetails getGrabPay() {
        return grabPay;
    }

    public void setGrabPay(AlipayDetails grabPay) {
        this.grabPay = grabPay;
    }

    public AlipayDetails getWeChatPay() {
        return weChatPay;
    }

    public void setWeChatPay(AlipayDetails weChatPay) {
        this.weChatPay = weChatPay;
    }
}
