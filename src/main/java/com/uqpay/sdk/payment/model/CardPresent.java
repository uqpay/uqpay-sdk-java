package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardPresent {

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("expiry_month")
    private String expiryMonth;

    @JsonProperty("expiry_year")
    private String expiryYear;

    @JsonProperty("cardholder_verification_method")
    private String cardholderVerificationMethod;

    @JsonProperty("encrypted_pin")
    private String encryptedPin;

    @JsonProperty("pan_entry_mode")
    private String panEntryMode;

    @JsonProperty("fallback")
    private Boolean fallback;

    @JsonProperty("fallback_reason")
    private String fallbackReason;

    @JsonProperty("emv_tags")
    private String emvTags;

    @JsonProperty("track1")
    private String track1;

    @JsonProperty("track2")
    private String track2;

    @JsonProperty("terminal_info")
    private TerminalInfo terminalInfo;

    @JsonProperty("system_trace_audit_number")
    private String systemTraceAuditNumber;

    public CardPresent() {
    }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getExpiryMonth() { return expiryMonth; }
    public void setExpiryMonth(String expiryMonth) { this.expiryMonth = expiryMonth; }

    public String getExpiryYear() { return expiryYear; }
    public void setExpiryYear(String expiryYear) { this.expiryYear = expiryYear; }

    public String getCardholderVerificationMethod() { return cardholderVerificationMethod; }
    public void setCardholderVerificationMethod(String cardholderVerificationMethod) { this.cardholderVerificationMethod = cardholderVerificationMethod; }

    public String getEncryptedPin() { return encryptedPin; }
    public void setEncryptedPin(String encryptedPin) { this.encryptedPin = encryptedPin; }

    public String getPanEntryMode() { return panEntryMode; }
    public void setPanEntryMode(String panEntryMode) { this.panEntryMode = panEntryMode; }

    public Boolean getFallback() { return fallback; }
    public void setFallback(Boolean fallback) { this.fallback = fallback; }

    public String getFallbackReason() { return fallbackReason; }
    public void setFallbackReason(String fallbackReason) { this.fallbackReason = fallbackReason; }

    public String getEmvTags() { return emvTags; }
    public void setEmvTags(String emvTags) { this.emvTags = emvTags; }

    public String getTrack1() { return track1; }
    public void setTrack1(String track1) { this.track1 = track1; }

    public String getTrack2() { return track2; }
    public void setTrack2(String track2) { this.track2 = track2; }

    public TerminalInfo getTerminalInfo() { return terminalInfo; }
    public void setTerminalInfo(TerminalInfo terminalInfo) { this.terminalInfo = terminalInfo; }

    public String getSystemTraceAuditNumber() { return systemTraceAuditNumber; }
    public void setSystemTraceAuditNumber(String systemTraceAuditNumber) { this.systemTraceAuditNumber = systemTraceAuditNumber; }
}
