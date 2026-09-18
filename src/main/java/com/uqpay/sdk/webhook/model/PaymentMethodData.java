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

    @JsonProperty("card_present")
    private CardDetails cardPresent;

    public CardDetails getCardPresent() {
        return cardPresent;
    }

    public void setCardPresent(CardDetails cardPresent) {
        this.cardPresent = cardPresent;
    }

    @JsonProperty("alipay")
    private AlipayDetails alipay;

    public AlipayDetails getAlipay() {
        return alipay;
    }

    public void setAlipay(AlipayDetails alipay) {
        this.alipay = alipay;
    }

    @JsonProperty("paynow")
    private AlipayDetails payNow;

    public AlipayDetails getPayNow() {
        return payNow;
    }

    public void setPayNow(AlipayDetails payNow) {
        this.payNow = payNow;
    }

    @JsonProperty("applepay")
    private AlipayDetails applePay;

    public AlipayDetails getApplePay() {
        return applePay;
    }

    public void setApplePay(AlipayDetails applePay) {
        this.applePay = applePay;
    }

    @JsonProperty("googlepay")
    private AlipayDetails googlePay;

    public AlipayDetails getGooglePay() {
        return googlePay;
    }

    public void setGooglePay(AlipayDetails googlePay) {
        this.googlePay = googlePay;
    }

    @JsonProperty("unionpay")
    private AlipayDetails unionPay;

    public AlipayDetails getUnionPay() {
        return unionPay;
    }

    public void setUnionPay(AlipayDetails unionPay) {
        this.unionPay = unionPay;
    }

    @JsonProperty("crypto")
    private AlipayDetails crypto;

    public AlipayDetails getCrypto() {
        return crypto;
    }

    public void setCrypto(AlipayDetails crypto) {
        this.crypto = crypto;
    }

    @JsonProperty("tng")
    private AlipayDetails tNG;

    public AlipayDetails getTNG() {
        return tNG;
    }

    public void setTNG(AlipayDetails tNG) {
        this.tNG = tNG;
    }

    @JsonProperty("truemoney")
    private AlipayDetails trueMoney;

    public AlipayDetails getTrueMoney() {
        return trueMoney;
    }

    public void setTrueMoney(AlipayDetails trueMoney) {
        this.trueMoney = trueMoney;
    }

    @JsonProperty("gcash")
    private AlipayDetails gCash;

    public AlipayDetails getGCash() {
        return gCash;
    }

    public void setGCash(AlipayDetails gCash) {
        this.gCash = gCash;
    }

    @JsonProperty("dana")
    private AlipayDetails dana;

    public AlipayDetails getDana() {
        return dana;
    }

    public void setDana(AlipayDetails dana) {
        this.dana = dana;
    }

    @JsonProperty("kakaopay")
    private AlipayDetails kakaoPay;

    public AlipayDetails getKakaoPay() {
        return kakaoPay;
    }

    public void setKakaoPay(AlipayDetails kakaoPay) {
        this.kakaoPay = kakaoPay;
    }

    @JsonProperty("tosspay")
    private AlipayDetails tossPay;

    public AlipayDetails getTossPay() {
        return tossPay;
    }

    public void setTossPay(AlipayDetails tossPay) {
        this.tossPay = tossPay;
    }

    @JsonProperty("naverpay")
    private AlipayDetails naverPay;

    public AlipayDetails getNaverPay() {
        return naverPay;
    }

    public void setNaverPay(AlipayDetails naverPay) {
        this.naverPay = naverPay;
    }

    @JsonProperty("mpay")
    private AlipayDetails mPay;

    public AlipayDetails getMPay() {
        return mPay;
    }

    public void setMPay(AlipayDetails mPay) {
        this.mPay = mPay;
    }

    @JsonProperty("kplus")
    private AlipayDetails kPlus;

    public AlipayDetails getKPlus() {
        return kPlus;
    }

    public void setKPlus(AlipayDetails kPlus) {
        this.kPlus = kPlus;
    }

    @JsonProperty("boost")
    private AlipayDetails boost;

    public AlipayDetails getBoost() {
        return boost;
    }

    public void setBoost(AlipayDetails boost) {
        this.boost = boost;
    }

    @JsonProperty("rabbitlinepay")
    private AlipayDetails rabbitLinePay;

    public AlipayDetails getRabbitLinePay() {
        return rabbitLinePay;
    }

    public void setRabbitLinePay(AlipayDetails rabbitLinePay) {
        this.rabbitLinePay = rabbitLinePay;
    }

    @JsonProperty("kaspi")
    private AlipayDetails kaspi;

    public AlipayDetails getKaspi() {
        return kaspi;
    }

    public void setKaspi(AlipayDetails kaspi) {
        this.kaspi = kaspi;
    }

    @JsonProperty("hipay")
    private AlipayDetails hiPay;

    public AlipayDetails getHiPay() {
        return hiPay;
    }

    public void setHiPay(AlipayDetails hiPay) {
        this.hiPay = hiPay;
    }

    @JsonProperty("shopeepay")
    private AlipayDetails shopeePay;

    public AlipayDetails getShopeePay() {
        return shopeePay;
    }

    public void setShopeePay(AlipayDetails shopeePay) {
        this.shopeePay = shopeePay;
    }
}
