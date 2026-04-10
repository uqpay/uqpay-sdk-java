package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetrieveCardResponse {

    @JsonProperty("card_id")
    private String cardId; // UUID

    @JsonProperty("card_bin")
    private String cardBin; // card number prefix (BIN)

    @JsonProperty("card_scheme")
    private String cardScheme; // e.g. VISA

    @JsonProperty("card_currency")
    private String cardCurrency; // SGD or USD

    @JsonProperty("card_number")
    private String cardNumber; // masked, e.g. "************5668"

    @JsonProperty("form_factor")
    private String formFactor; // VIRTUAL | PHYSICAL

    @JsonProperty("mode_type")
    private String modeType; // SINGLE (prepaid) | SHARE (debit)

    @JsonProperty("card_product_id")
    private String cardProductId; // UUID

    @JsonProperty("card_limit")
    private Double cardLimit; // fixed credit limit, not cumulative balance

    @JsonProperty("available_balance")
    private String availableBalance; // currency refers to card_currency

    @JsonProperty("cardholder")
    private CardholderInfo cardholder;

    @JsonProperty("spending_controls")
    private List<SpendingControl> spendingControls; // transaction spending rules

    @JsonProperty("no_pin_payment_amount")
    private String noPinPaymentAmount; // includes currency unit, e.g. "2000USD"

    @JsonProperty("risk_controls")
    private RiskControls riskControls; // 3DS and MCC restrictions

    @JsonProperty("metadata")
    private Object metadata; // key-value pairs, max 3200 bytes

    @JsonProperty("card_status")
    private String cardStatus; // PENDING | ACTIVE | FROZEN | BLOCKED | CANCELLED | LOST | STOLEN | FAILED

    @JsonProperty("update_reason")
    private String updateReason; // reason for last status change, max 100 chars

    @JsonProperty("consumed_amount")
    private String consumedAmount; // cumulative amount of card limit already used

    public RetrieveCardResponse() {
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public String getCardScheme() {
        return cardScheme;
    }

    public void setCardScheme(String cardScheme) {
        this.cardScheme = cardScheme;
    }

    public String getCardCurrency() {
        return cardCurrency;
    }

    public void setCardCurrency(String cardCurrency) {
        this.cardCurrency = cardCurrency;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public String getCardProductId() {
        return cardProductId;
    }

    public void setCardProductId(String cardProductId) {
        this.cardProductId = cardProductId;
    }

    public Double getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(Double cardLimit) {
        this.cardLimit = cardLimit;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public CardholderInfo getCardholder() {
        return cardholder;
    }

    public void setCardholder(CardholderInfo cardholder) {
        this.cardholder = cardholder;
    }

    public List<SpendingControl> getSpendingControls() {
        return spendingControls;
    }

    public void setSpendingControls(List<SpendingControl> spendingControls) {
        this.spendingControls = spendingControls;
    }

    public String getNoPinPaymentAmount() {
        return noPinPaymentAmount;
    }

    public void setNoPinPaymentAmount(String noPinPaymentAmount) {
        this.noPinPaymentAmount = noPinPaymentAmount;
    }

    public RiskControls getRiskControls() {
        return riskControls;
    }

    public void setRiskControls(RiskControls riskControls) {
        this.riskControls = riskControls;
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

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getUpdateReason() {
        return updateReason;
    }

    public void setUpdateReason(String updateReason) {
        this.updateReason = updateReason;
    }

    public String getConsumedAmount() {
        return consumedAmount;
    }

    public void setConsumedAmount(String consumedAmount) {
        this.consumedAmount = consumedAmount;
    }
}
