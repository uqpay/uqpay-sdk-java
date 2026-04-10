package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePaymentIntentRequest {

    @JsonProperty("amount")
    private String amount; // required, smallest currency unit

    @JsonProperty("currency")
    private String currency; // required, ISO 4217 three-letter code

    @JsonProperty("merchant_order_id")
    private String merchantOrderId; // required

    @JsonProperty("description")
    private String description; // required, max 32 chars, displayed to customer

    @JsonProperty("return_url")
    private String returnUrl; // required, redirect URL or app scheme URI after authentication

    @JsonProperty("metadata")
    private Object metadata; // optional, key-value JSON object, max 512 bytes

    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod; // required

    @JsonProperty("ip_address")
    private String ipAddress; // optional, IPv4 or IPv6

    @JsonProperty("browser_info")
    private BrowserInfo browserInfo; // optional, for risk and fraud prevention

    @JsonProperty("payment_orders")
    private PaymentOrders paymentOrders; // optional, purchase order details

    public CreatePaymentIntentRequest() {
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

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getMetadata() {
        if (metadata instanceof Map) {
            return (Map<String, String>) metadata;
        }
        return Collections.emptyMap();
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public BrowserInfo getBrowserInfo() {
        return browserInfo;
    }

    public void setBrowserInfo(BrowserInfo browserInfo) {
        this.browserInfo = browserInfo;
    }

    public PaymentOrders getPaymentOrders() {
        return paymentOrders;
    }

    public void setPaymentOrders(PaymentOrders paymentOrders) {
        this.paymentOrders = paymentOrders;
    }
}
