package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayoutDetailResponse extends Payout {

    @JsonProperty("amount_payer_pays")
    private String amountPayerPays; // Required. Total amount the payer remits (including fees)

    @JsonProperty("source_currency")
    private String sourceCurrency; // Required. ISO 4217 currency code the payer sends

    @JsonProperty("source_amount")
    private String sourceAmount; // Required. Amount paid by payer in source currency

    @JsonProperty("amount_beneficiary_receives")
    private String amountBeneficiaryReceives; // Required. Final amount the beneficiary receives

    @JsonProperty("payout_method")
    private String payoutMethod; // Required. LOCAL or SWIFT

    @JsonProperty("payer")
    private PayoutPayer payer; // Optional. Payer details (entity who initiates the payout)

    @JsonProperty("beneficiary")
    private Beneficiary beneficiary; // Required. Beneficiary details associated with this payout

    public PayoutDetailResponse() {
    }

    public String getAmountPayerPays() {
        return amountPayerPays;
    }

    public void setAmountPayerPays(String amountPayerPays) {
        this.amountPayerPays = amountPayerPays;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(String sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public String getAmountBeneficiaryReceives() {
        return amountBeneficiaryReceives;
    }

    public void setAmountBeneficiaryReceives(String amountBeneficiaryReceives) {
        this.amountBeneficiaryReceives = amountBeneficiaryReceives;
    }

    public String getPayoutMethod() {
        return payoutMethod;
    }

    public void setPayoutMethod(String payoutMethod) {
        this.payoutMethod = payoutMethod;
    }

    public PayoutPayer getPayer() {
        return payer;
    }

    public void setPayer(PayoutPayer payer) {
        this.payer = payer;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }
}
