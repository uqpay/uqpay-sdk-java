package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardUpdateRequest {

    @JsonProperty("card_limit")
    private Double cardLimit; // optional; leave empty when mode_type is SINGLE

    @JsonProperty("no_pin_payment_amount")
    private Double noPinPaymentAmount; // optional; >= 0, defaults to 200 SGD; set 0 to disable no-PIN payments

    @JsonProperty("spending_controls")
    private List<SpendingControl> spendingControls; // optional; transaction spending rules

    @JsonProperty("risk_controls")
    private RiskControls riskControls; // optional; 3DS and MCC restrictions

    @JsonProperty("metadata")
    private Map<String, String> metadata; // optional; key-value pairs, max 3200 bytes

    public CardUpdateRequest() {
    }

    public Double getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(Double cardLimit) {
        this.cardLimit = cardLimit;
    }

    public Double getNoPinPaymentAmount() {
        return noPinPaymentAmount;
    }

    public void setNoPinPaymentAmount(Double noPinPaymentAmount) {
        this.noPinPaymentAmount = noPinPaymentAmount;
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
}
