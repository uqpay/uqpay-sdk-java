package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardProduct {

    @JsonProperty("product_id")
    private String productId; // Required. Unique identifier (UUID)

    @JsonProperty("mode_type")
    private String modeType; // Optional. SINGLE (prepaid only) or SHARE (supports debit mode)

    @JsonProperty("card_bin")
    private String cardBin; // Required. Card number prefix (BIN), e.g. "40963608"

    @JsonProperty("card_form")
    private List<String> cardForm; // Required. VIR (virtual) or PHY (physical)

    @JsonProperty("max_card_quota")
    private Integer maxCardQuota; // Optional. Max cards issuable for this product under the account

    @JsonProperty("card_scheme")
    private String cardScheme; // Required. Card scheme, e.g. VISA

    @JsonProperty("card_currency")
    private List<String> cardCurrency; // Required. ISO 4217 currency codes, e.g. ["SGD", "USD"]

    @JsonProperty("product_status")
    private String productStatus; // Required. ENABLED or DISABLED

    @JsonProperty("no_pin_payment_amount")
    private List<NoPinPaymentLimit> noPinPaymentAmount; // Required. Max allowable amounts for no-PIN transactions, per currency

    @JsonProperty("create_time")
    private String createTime; // Required. ISO 8601 timestamp, e.g. "2023-09-15T10:02:17+08:00"

    @JsonProperty("update_time")
    private String updateTime; // Required. ISO 8601 timestamp, e.g. "2023-09-15T10:02:17+08:00"

    @JsonProperty("required_fields")
    private List<ProductRequiredField> requiredFields;

    @JsonProperty("kyc_level")
    private String kycLevel; // SIMPLIFIED | STANDARD | ENHANCED

    public CardProduct() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public List<String> getCardForm() {
        return cardForm;
    }

    public void setCardForm(List<String> cardForm) {
        this.cardForm = cardForm;
    }

    public Integer getMaxCardQuota() {
        return maxCardQuota;
    }

    public void setMaxCardQuota(Integer maxCardQuota) {
        this.maxCardQuota = maxCardQuota;
    }

    public String getCardScheme() {
        return cardScheme;
    }

    public void setCardScheme(String cardScheme) {
        this.cardScheme = cardScheme;
    }

    public List<String> getCardCurrency() {
        return cardCurrency;
    }

    public void setCardCurrency(List<String> cardCurrency) {
        this.cardCurrency = cardCurrency;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public List<NoPinPaymentLimit> getNoPinPaymentAmount() {
        return noPinPaymentAmount;
    }

    public void setNoPinPaymentAmount(List<NoPinPaymentLimit> noPinPaymentAmount) {
        this.noPinPaymentAmount = noPinPaymentAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<ProductRequiredField> getRequiredFields() {
        return requiredFields;
    }

    public void setRequiredFields(List<ProductRequiredField> requiredFields) {
        this.requiredFields = requiredFields;
    }

    public String getKycLevel() {
        return kycLevel;
    }

    public void setKycLevel(String kycLevel) {
        this.kycLevel = kycLevel;
    }

}
