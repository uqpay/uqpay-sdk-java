package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePayoutRequest {

    @JsonProperty("beneficiary_id")
    private String beneficiaryId; // Conditional. Existing beneficiary UUID; omit if providing inline beneficiary object

    @JsonProperty("beneficiary")
    private BeneficiaryCreationRequest beneficiary; // Conditional. Inline beneficiary details; omit if using beneficiary_id

    @JsonProperty("payout_currency")
    private String payoutCurrency; // Conditional. ISO 4217 currency code the beneficiary receives; required when quote_id is specified

    @JsonProperty("payout_amount")
    private String payoutAmount; // Conditional. Amount the beneficiary receives in payout_currency; required when quote_id is specified

    @JsonProperty("purpose_code")
    private String purposeCode; // Required. Transaction purpose, e.g. WAGES_SALARY, GOODS_PURCHASED, PERSONAL_REMITTANCE

    @JsonProperty("payout_reference")
    private String payoutReference; // Required. Bank payment reference shown in beneficiary's bank records, max 100 chars

    @JsonProperty("fee_paid_by")
    private String feePaidBy; // Required. Fee structure: OURS (payer covers all fees)

    @JsonProperty("payout_date")
    private String payoutDate; // Required. Scheduled payment submission date, format: YYYY-MM-DD

    @JsonProperty("quote_id")
    private String quoteId; // Optional. UUID of a pre-created cross-currency quote; requires payout_currency and payout_amount

    @JsonProperty("currency")
    private String currency; // Required. ISO 4217 currency code the payer sends out

    @JsonProperty("amount")
    private String amount; // Required. Payout sum in source currency (decimal format)

    @JsonProperty("is_payer")
    private String isPayer; // Optional (deprecated). Y or N

    @JsonProperty("payer_id")
    private String payerId; // Optional (deprecated). UUID of the payer

    @JsonProperty("documentation")
    private List<Map<String, String>> documentation; // Optional. Supporting documents as base64-encoded files

    public CreatePayoutRequest() {
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public BeneficiaryCreationRequest getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(BeneficiaryCreationRequest beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getPayoutCurrency() {
        return payoutCurrency;
    }

    public void setPayoutCurrency(String payoutCurrency) {
        this.payoutCurrency = payoutCurrency;
    }

    public String getPayoutAmount() {
        return payoutAmount;
    }

    public void setPayoutAmount(String payoutAmount) {
        this.payoutAmount = payoutAmount;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getPayoutReference() {
        return payoutReference;
    }

    public void setPayoutReference(String payoutReference) {
        this.payoutReference = payoutReference;
    }

    public String getFeePaidBy() {
        return feePaidBy;
    }

    public void setFeePaidBy(String feePaidBy) {
        this.feePaidBy = feePaidBy;
    }

    public String getPayoutDate() {
        return payoutDate;
    }

    public void setPayoutDate(String payoutDate) {
        this.payoutDate = payoutDate;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIsPayer() {
        return isPayer;
    }

    public void setIsPayer(String isPayer) {
        this.isPayer = isPayer;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public List<Map<String, String>> getDocumentation() {
        return documentation;
    }

    public void setDocumentation(List<Map<String, String>> documentation) {
        this.documentation = documentation;
    }
}
