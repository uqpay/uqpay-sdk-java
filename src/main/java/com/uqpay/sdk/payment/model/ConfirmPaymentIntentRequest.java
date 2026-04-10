package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfirmPaymentIntentRequest {

    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod; // Optional. Payment method details to confirm the intent

    @JsonProperty("return_url")
    private String returnUrl; // Optional. Redirect URL or app scheme URI after authentication, max 1024 chars

    @JsonProperty("ip_address")
    private String ipAddress; // Optional. IPv4 or IPv6 address; required when three_ds_action=enforce_3ds

    @JsonProperty("browser_info")
    private BrowserInfo browserInfo; // Optional. Browser info for risk prevention; required when three_ds_action=enforce_3ds

    public ConfirmPaymentIntentRequest() {
    }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getReturnUrl() { return returnUrl; }
    public void setReturnUrl(String returnUrl) { this.returnUrl = returnUrl; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public BrowserInfo getBrowserInfo() { return browserInfo; }
    public void setBrowserInfo(BrowserInfo browserInfo) { this.browserInfo = browserInfo; }
}
