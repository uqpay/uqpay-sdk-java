package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TerminalInfo {

    @JsonProperty("terminal_id")
    private String terminalId;

    @JsonProperty("mobile_device")
    private Boolean mobileDevice;

    @JsonProperty("use_embedded_reader")
    private Boolean useEmbeddedReader;

    public TerminalInfo() {
    }

    public String getTerminalId() { return terminalId; }
    public void setTerminalId(String terminalId) { this.terminalId = terminalId; }

    public Boolean getMobileDevice() { return mobileDevice; }
    public void setMobileDevice(Boolean mobileDevice) { this.mobileDevice = mobileDevice; }

    public Boolean getUseEmbeddedReader() { return useEmbeddedReader; }
    public void setUseEmbeddedReader(Boolean useEmbeddedReader) { this.useEmbeddedReader = useEmbeddedReader; }
}
