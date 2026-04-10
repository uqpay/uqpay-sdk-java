package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionData {

    // =========================================================================
    // Conversion status constants
    // =========================================================================

    public static final String CONVERSION_STATUS_TRADE_SETTLED = "TRADE_SETTLED";
    public static final String CONVERSION_STATUS_AWAITING_FUNDS = "AWAITING_FUNDS";
    public static final String CONVERSION_STATUS_FUNDS_ARRIVED = "FUNDS_ARRIVED";
    public static final String CONVERSION_STATUS_PENDING = "PENDING";
    public static final String CONVERSION_STATUS_COMPLETED = "COMPLETED";
    public static final String CONVERSION_STATUS_CANCELED = "CANCELED";
    public static final String CONVERSION_STATUS_FAILED = "FAILED";

    // =========================================================================
    // Conversion way constants
    // =========================================================================

    public static final String CONVERSION_WAY_API = "API";
    public static final String CONVERSION_WAY_WEB = "WEB";

    // =========================================================================
    // Fields
    // =========================================================================

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("buy_amount")
    private String buyAmount;

    @JsonProperty("buy_currency")
    private String buyCurrency;

    @JsonProperty("client_rate")
    private String clientRate;

    @JsonProperty("conversion_id")
    private String conversionId;

    @JsonProperty("conversion_status")
    private String conversionStatus;

    @JsonProperty("conversion_way")
    private String conversionWay;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("creator")
    private String creator;

    @JsonProperty("direct_id")
    private String directId;

    @JsonProperty("sell_amount")
    private String sellAmount;

    @JsonProperty("sell_currency")
    private String sellCurrency;

    @JsonProperty("settle_time")
    private String settleTime;

    @JsonProperty("short_reference_id")
    private String shortReferenceId;

    public ConversionData() {
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(String buyAmount) {
        this.buyAmount = buyAmount;
    }

    public String getBuyCurrency() {
        return buyCurrency;
    }

    public void setBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    public String getClientRate() {
        return clientRate;
    }

    public void setClientRate(String clientRate) {
        this.clientRate = clientRate;
    }

    public String getConversionId() {
        return conversionId;
    }

    public void setConversionId(String conversionId) {
        this.conversionId = conversionId;
    }

    public String getConversionStatus() {
        return conversionStatus;
    }

    public void setConversionStatus(String conversionStatus) {
        this.conversionStatus = conversionStatus;
    }

    public String getConversionWay() {
        return conversionWay;
    }

    public void setConversionWay(String conversionWay) {
        this.conversionWay = conversionWay;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDirectId() {
        return directId;
    }

    public void setDirectId(String directId) {
        this.directId = directId;
    }

    public String getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(String sellAmount) {
        this.sellAmount = sellAmount;
    }

    public String getSellCurrency() {
        return sellCurrency;
    }

    public void setSellCurrency(String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public String getShortReferenceId() {
        return shortReferenceId;
    }

    public void setShortReferenceId(String shortReferenceId) {
        this.shortReferenceId = shortReferenceId;
    }
}
