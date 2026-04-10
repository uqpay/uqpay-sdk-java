package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Conversion {

    @JsonProperty("conversion_id")
    private String conversionId; // Required. UUID v4 identifier for the conversion transaction

    @JsonProperty("short_reference_id")
    private String shortReferenceId; // Required. System-generated reference, e.g. "P220406-LLCVLRM"

    @JsonProperty("sell_currency")
    private String sellCurrency; // Required. ISO 4217 currency code of the currency being sold

    @JsonProperty("buy_currency")
    private String buyCurrency; // Required. ISO 4217 currency code of the currency being purchased

    @JsonProperty("sell_amount")
    private String sellAmount; // Required. Amount of currency sold (decimal string)

    @JsonProperty("buy_amount")
    private String buyAmount; // Required. Amount of currency purchased (decimal string)

    @JsonProperty("client_rate")
    private String clientRate; // Required. Exchange rate applied to the transaction

    @JsonProperty("conversion_status")
    private String conversionStatus; // Required. PROCESSING, AWAITING_FUNDS, TRADE_SETTLED, or FUNDS_ARRIVED

    @JsonProperty("create_time")
    private String createTime; // Required. ISO 8601 timestamp when the conversion was initiated

    @JsonProperty("settle_time")
    private String settleTime; // Required. ISO 8601 timestamp when the conversion was settled

    @JsonProperty("account_name")
    private String accountName; // Required. Name of the customer associated with the account

    @JsonProperty("creator")
    private String creator; // Required. Entity that initiated the conversion

    public Conversion() {
    }

    public String getConversionId() {
        return conversionId;
    }

    public void setConversionId(String conversionId) {
        this.conversionId = conversionId;
    }

    public String getShortReferenceId() {
        return shortReferenceId;
    }

    public void setShortReferenceId(String shortReferenceId) {
        this.shortReferenceId = shortReferenceId;
    }

    public String getSellCurrency() {
        return sellCurrency;
    }

    public void setSellCurrency(String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    public String getBuyCurrency() {
        return buyCurrency;
    }

    public void setBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    public String getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(String sellAmount) {
        this.sellAmount = sellAmount;
    }

    public String getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(String buyAmount) {
        this.buyAmount = buyAmount;
    }

    public String getClientRate() {
        return clientRate;
    }

    public void setClientRate(String clientRate) {
        this.clientRate = clientRate;
    }

    public String getConversionStatus() {
        return conversionStatus;
    }

    public void setConversionStatus(String conversionStatus) {
        this.conversionStatus = conversionStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
