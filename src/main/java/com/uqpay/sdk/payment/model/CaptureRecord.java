package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Capture processing result; acceptance alone does not establish completion. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaptureRecord {

    @JsonProperty("capture_id")
    private String captureId;

    public String getCaptureId() { return captureId; }
    public void setCaptureId(String value) { this.captureId = value; }

    @JsonProperty("merchant_capture_order_id")
    private String merchantCaptureOrderId;

    public String getMerchantCaptureOrderId() { return merchantCaptureOrderId; }
    public void setMerchantCaptureOrderId(String value) { this.merchantCaptureOrderId = value; }

    @JsonProperty("capture_sequence")
    private Integer captureSequence;

    public Integer getCaptureSequence() { return captureSequence; }
    public void setCaptureSequence(Integer value) { this.captureSequence = value; }

    @JsonProperty("capture_amount")
    private String captureAmount;

    public String getCaptureAmount() { return captureAmount; }
    public void setCaptureAmount(String value) { this.captureAmount = value; }

    @JsonProperty("currency")
    private String currency;

    public String getCurrency() { return currency; }
    public void setCurrency(String value) { this.currency = value; }

    @JsonProperty("capture_mode")
    private String captureMode;

    public String getCaptureMode() { return captureMode; }
    public void setCaptureMode(String value) { this.captureMode = value; }

    @JsonProperty("channel_capture_status")
    private Integer channelCaptureStatus;

    public Integer getChannelCaptureStatus() { return channelCaptureStatus; }
    public void setChannelCaptureStatus(Integer value) { this.channelCaptureStatus = value; }

    @JsonProperty("arn")
    private String arn;

    public String getArn() { return arn; }
    public void setArn(String value) { this.arn = value; }

    @JsonProperty("channel_capture_time")
    private String channelCaptureTime;

    public String getChannelCaptureTime() { return channelCaptureTime; }
    public void setChannelCaptureTime(String value) { this.channelCaptureTime = value; }

    @JsonProperty("request_time")
    private String requestTime;

    public String getRequestTime() { return requestTime; }
    public void setRequestTime(String value) { this.requestTime = value; }

    @JsonProperty("complete_time")
    private String completeTime;

    public String getCompleteTime() { return completeTime; }
    public void setCompleteTime(String value) { this.completeTime = value; }
}
