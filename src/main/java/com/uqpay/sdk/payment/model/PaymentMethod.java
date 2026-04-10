package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentMethod {

    @JsonProperty("type")
    private String type;

    @JsonProperty("card")
    private Card card;

    @JsonProperty("card_present")
    private CardPresent cardPresent;

    @JsonProperty("alipaycn")
    private WalletPayment alipayCN;

    @JsonProperty("alipayhk")
    private WalletPayment alipayHK;

    @JsonProperty("unionpay")
    private WalletPayment unionPay;

    @JsonProperty("wechatpay")
    private WeChatPay weChatPay;

    @JsonProperty("grabpay")
    private GrabPay grabPay;

    @JsonProperty("paynow")
    private WalletPayment payNow;

    @JsonProperty("truemoney")
    private WalletPayment trueMoney;

    @JsonProperty("tng")
    private WalletPayment tng;

    @JsonProperty("gcash")
    private WalletPayment gCash;

    @JsonProperty("dana")
    private WalletPayment dana;

    @JsonProperty("kakaopay")
    private WalletPayment kakaoPay;

    @JsonProperty("toss")
    private WalletPayment toss;

    @JsonProperty("naverpay")
    private WalletPayment naverPay;

    @JsonProperty("applepay")
    private ApplePay applePay;

    @JsonProperty("googlepay")
    private GooglePay googlePay;

    @JsonProperty("crypto")
    private CryptoPay crypto;

    public PaymentMethod() {
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }

    public CardPresent getCardPresent() { return cardPresent; }
    public void setCardPresent(CardPresent cardPresent) { this.cardPresent = cardPresent; }

    public WalletPayment getAlipayCN() { return alipayCN; }
    public void setAlipayCN(WalletPayment alipayCN) { this.alipayCN = alipayCN; }

    public WalletPayment getAlipayHK() { return alipayHK; }
    public void setAlipayHK(WalletPayment alipayHK) { this.alipayHK = alipayHK; }

    public WalletPayment getUnionPay() { return unionPay; }
    public void setUnionPay(WalletPayment unionPay) { this.unionPay = unionPay; }

    public WeChatPay getWeChatPay() { return weChatPay; }
    public void setWeChatPay(WeChatPay weChatPay) { this.weChatPay = weChatPay; }

    public GrabPay getGrabPay() { return grabPay; }
    public void setGrabPay(GrabPay grabPay) { this.grabPay = grabPay; }

    public WalletPayment getPayNow() { return payNow; }
    public void setPayNow(WalletPayment payNow) { this.payNow = payNow; }

    public WalletPayment getTrueMoney() { return trueMoney; }
    public void setTrueMoney(WalletPayment trueMoney) { this.trueMoney = trueMoney; }

    public WalletPayment getTng() { return tng; }
    public void setTng(WalletPayment tng) { this.tng = tng; }

    public WalletPayment getGCash() { return gCash; }
    public void setGCash(WalletPayment gCash) { this.gCash = gCash; }

    public WalletPayment getDana() { return dana; }
    public void setDana(WalletPayment dana) { this.dana = dana; }

    public WalletPayment getKakaoPay() { return kakaoPay; }
    public void setKakaoPay(WalletPayment kakaoPay) { this.kakaoPay = kakaoPay; }

    public WalletPayment getToss() { return toss; }
    public void setToss(WalletPayment toss) { this.toss = toss; }

    public WalletPayment getNaverPay() { return naverPay; }
    public void setNaverPay(WalletPayment naverPay) { this.naverPay = naverPay; }

    public ApplePay getApplePay() { return applePay; }
    public void setApplePay(ApplePay applePay) { this.applePay = applePay; }

    public GooglePay getGooglePay() { return googlePay; }
    public void setGooglePay(GooglePay googlePay) { this.googlePay = googlePay; }

    public CryptoPay getCrypto() { return crypto; }
    public void setCrypto(CryptoPay crypto) { this.crypto = crypto; }
}
