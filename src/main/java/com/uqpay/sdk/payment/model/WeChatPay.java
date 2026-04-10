package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeChatPay {

    @JsonProperty("flow")
    private String flow;

    @JsonProperty("os_type")
    private String osType;

    @JsonProperty("is_present")
    private Boolean isPresent;

    @JsonProperty("payment_code")
    private String paymentCode;

    @JsonProperty("open_id")
    private String openId;

    public WeChatPay() {
    }

    public String getFlow() { return flow; }
    public void setFlow(String flow) { this.flow = flow; }

    public String getOsType() { return osType; }
    public void setOsType(String osType) { this.osType = osType; }

    public Boolean getIsPresent() { return isPresent; }
    public void setIsPresent(Boolean isPresent) { this.isPresent = isPresent; }

    public String getPaymentCode() { return paymentCode; }
    public void setPaymentCode(String paymentCode) { this.paymentCode = paymentCode; }

    public String getOpenId() { return openId; }
    public void setOpenId(String openId) { this.openId = openId; }
}
