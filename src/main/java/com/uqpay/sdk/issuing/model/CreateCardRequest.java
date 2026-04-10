package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCardRequest {

    @JsonProperty("card_limit")
    private Double cardLimit; // optional; fixed credit limit, >= 0.01 for BINs 527735/555071/555243, >= 0 for others

    @JsonProperty("card_currency")
    private String cardCurrency; // required; SGD or USD

    @JsonProperty("cardholder_id")
    private String cardholderId; // required; UUID

    @JsonProperty("card_product_id")
    private String cardProductId; // required; UUID

    @JsonProperty("spending_controls")
    private List<SpendingControl> spendingControls; // optional; transaction spending rules

    @JsonProperty("risk_controls")
    private RiskControls riskControls; // optional; 3DS and MCC restrictions

    @JsonProperty("metadata")
    private Map<String, String> metadata; // optional; key-value pairs, max 3200 bytes

    @JsonProperty("usage_type")
    private String usageType; // optional; NORMAL or ONE_TIME

    @JsonProperty("auto_cancel_trigger")
    private String autoCancelTrigger; // optional; ON_AUTH or ON_CAPTURE

    @JsonProperty("expiry_at")
    private String expiryAt; // optional; ISO 8601

    @JsonProperty("cardholder_required_fields")
    private CardholderRequiredFields cardholderRequiredFields; // optional

    public CreateCardRequest() {
    }

    public Double getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(Double cardLimit) {
        this.cardLimit = cardLimit;
    }

    public String getCardCurrency() {
        return cardCurrency;
    }

    public void setCardCurrency(String cardCurrency) {
        this.cardCurrency = cardCurrency;
    }

    public String getCardholderId() {
        return cardholderId;
    }

    public void setCardholderId(String cardholderId) {
        this.cardholderId = cardholderId;
    }

    public String getCardProductId() {
        return cardProductId;
    }

    public void setCardProductId(String cardProductId) {
        this.cardProductId = cardProductId;
    }

    public List<SpendingControl> getSpendingControls() {
        return spendingControls;
    }

    public void setSpendingControls(List<SpendingControl> spendingControls) {
        this.spendingControls = spendingControls;
    }

    public RiskControls getRiskControls() {
        return riskControls;
    }

    public void setRiskControls(RiskControls riskControls) {
        this.riskControls = riskControls;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getAutoCancelTrigger() {
        return autoCancelTrigger;
    }

    public void setAutoCancelTrigger(String autoCancelTrigger) {
        this.autoCancelTrigger = autoCancelTrigger;
    }

    public String getExpiryAt() {
        return expiryAt;
    }

    public void setExpiryAt(String expiryAt) {
        this.expiryAt = expiryAt;
    }

    public CardholderRequiredFields getCardholderRequiredFields() {
        return cardholderRequiredFields;
    }

    public void setCardholderRequiredFields(CardholderRequiredFields cardholderRequiredFields) {
        this.cardholderRequiredFields = cardholderRequiredFields;
    }
}
