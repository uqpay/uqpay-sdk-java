package com.uqpay.sdk.banking.model;

/** Results do not reserve funds or guarantee a later payout creation. */
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class ValidatePayoutResponse {
    @com.fasterxml.jackson.annotation.JsonProperty("currency")
    private String currency;

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    @com.fasterxml.jackson.annotation.JsonProperty("amount")
    private String amount;

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
    @com.fasterxml.jackson.annotation.JsonProperty("fee_amount")
    private String feeAmount;

    public String getFeeAmount() { return feeAmount; }
    public void setFeeAmount(String feeAmount) { this.feeAmount = feeAmount; }
    @com.fasterxml.jackson.annotation.JsonProperty("total_amount")
    private String totalAmount;

    public String getTotalAmount() { return totalAmount; }
    public void setTotalAmount(String totalAmount) { this.totalAmount = totalAmount; }
    @com.fasterxml.jackson.annotation.JsonProperty("channel_code")
    private String channelCode;

    public String getChannelCode() { return channelCode; }
    public void setChannelCode(String channelCode) { this.channelCode = channelCode; }
    @com.fasterxml.jackson.annotation.JsonProperty("sufficient_funds")
    private Boolean sufficientFunds;

    public Boolean getSufficientFunds() { return sufficientFunds; }
    public void setSufficientFunds(Boolean sufficientFunds) { this.sufficientFunds = sufficientFunds; }
    @com.fasterxml.jackson.annotation.JsonProperty("beneficiary_valid")
    private Boolean beneficiaryValid;

    public Boolean getBeneficiaryValid() { return beneficiaryValid; }
    public void setBeneficiaryValid(Boolean beneficiaryValid) { this.beneficiaryValid = beneficiaryValid; }
}
