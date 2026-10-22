package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CapturePaymentIntentRequest {

    @JsonProperty("amount_to_capture")
    private String amountToCapture; // Omit for the full amount; a smaller final amount requires eligible pre-authorization.

    public CapturePaymentIntentRequest() {
    }

    public String getAmountToCapture() { return amountToCapture; }
    public void setAmountToCapture(String amountToCapture) { this.amountToCapture = amountToCapture; }

    @JsonProperty("merchant_capture_order_id")
    private String merchantCaptureOrderId;

    public String getMerchantCaptureOrderId() { return merchantCaptureOrderId; }
    public void setMerchantCaptureOrderId(String value) { this.merchantCaptureOrderId = value; }

    @JsonProperty("final_capture")
    private Boolean finalCapture; // Only true is supported; omit to use the final-capture default.

    public Boolean getFinalCapture() { return finalCapture; }
    public void setFinalCapture(Boolean value) { this.finalCapture = value; }
}
