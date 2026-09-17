package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlipayDetails {

    @JsonProperty("flow")
    private String flow;

    @JsonProperty("os_type")
    private String osType;

    public AlipayDetails() {
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    @JsonProperty("static_qrcode")
    private String staticQrcode;

    public String getStaticQrcode() {
        return staticQrcode;
    }

    public void setStaticQrcode(String staticQrcode) {
        this.staticQrcode = staticQrcode;
    }

    @JsonProperty("static_qrcode_extension")
    private String staticQrcodeExtension;

    public String getStaticQrcodeExtension() {
        return staticQrcodeExtension;
    }

    public void setStaticQrcodeExtension(String staticQrcodeExtension) {
        this.staticQrcodeExtension = staticQrcodeExtension;
    }

    @JsonProperty("static_qrcode_number_plate")
    private String staticQrcodeNumberPlate;

    public String getStaticQrcodeNumberPlate() {
        return staticQrcodeNumberPlate;
    }

    public void setStaticQrcodeNumberPlate(String staticQrcodeNumberPlate) {
        this.staticQrcodeNumberPlate = staticQrcodeNumberPlate;
    }
}
