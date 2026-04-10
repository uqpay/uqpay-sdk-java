package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateConversionResponse {

    @JsonProperty("conversion_id")
    private String conversionId; // Required. UUID v4 identifier for the conversion transaction

    @JsonProperty("short_reference_id")
    private String shortReferenceId; // Required. System-generated reference, e.g. "P220406-LLCVLRM"

    @JsonProperty("sell_currency")
    private String sellCurrency; // Required. ISO 4217 currency code of the currency sold

    @JsonProperty("sell_amount")
    private String sellAmount; // Required. Amount of currency sold (decimal string)

    @JsonProperty("buy_currency")
    private String buyCurrency; // Required. ISO 4217 currency code of the currency purchased

    @JsonProperty("buy_amount")
    private String buyAmount; // Required. Amount of currency purchased after exchange (decimal string)

    @JsonProperty("created_date")
    private String createTime; // Required. ISO 8601 timestamp when the conversion was created

    @JsonProperty("currency_pair")
    private String currencyPair; // Required. Currency pair designation, e.g. "USDSGD"

    @JsonProperty("reference")
    private String referenceId; // Required. System reference identifier, e.g. "XC240822-TXCRW2EI"

    @JsonProperty("status")
    private String conversionStatus; // Required. PROCESSING, AWAITING_FUNDS, TRADE_SETTLED, or FUNDS_ARRIVED

    public CreateConversionResponse() {
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

    public String getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(String sellAmount) {
        this.sellAmount = sellAmount;
    }

    public String getBuyCurrency() {
        return buyCurrency;
    }

    public void setBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    public String getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(String buyAmount) {
        this.buyAmount = buyAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getConversionStatus() {
        return conversionStatus;
    }

    public void setConversionStatus(String conversionStatus) {
        this.conversionStatus = conversionStatus;
    }

}
