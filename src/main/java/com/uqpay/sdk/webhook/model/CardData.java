package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardData {

    // =========================================================================
    // Card status constants
    // =========================================================================

    public static final String CARD_STATUS_ACTIVE = "ACTIVE";
    public static final String CARD_STATUS_INACTIVE = "INACTIVE";
    public static final String CARD_STATUS_SUSPENDED = "SUSPENDED";
    public static final String CARD_STATUS_BLOCKED = "BLOCKED";
    public static final String CARD_STATUS_FROZEN = "FROZEN";
    public static final String CARD_STATUS_PRE_CANCEL = "PRE_CANCEL";
    public static final String CARD_STATUS_CLOSED = "CLOSED";
    public static final String CARD_STATUS_PENDING = "PENDING";

    // =========================================================================
    // Card scheme constants
    // =========================================================================

    public static final String CARD_SCHEME_VISA = "VISA";
    public static final String CARD_SCHEME_MASTERCARD = "MASTERCARD";
    public static final String CARD_SCHEME_UNIONPAY = "UNIONPAY";

    // =========================================================================
    // Form factor constants
    // =========================================================================

    public static final String FORM_FACTOR_VIRTUAL = "VIRTUAL";
    public static final String FORM_FACTOR_PHYSICAL = "PHYSICAL";

    // =========================================================================
    // Mode type constants
    // =========================================================================

    public static final String MODE_TYPE_SINGLE = "SINGLE";
    public static final String MODE_TYPE_MULTI = "MULTI";

    // =========================================================================
    // Spending interval constants
    // =========================================================================

    public static final String SPENDING_INTERVAL_PER_TRANSACTION = "PER_TRANSACTION";
    public static final String SPENDING_INTERVAL_DAILY = "DAILY";
    public static final String SPENDING_INTERVAL_WEEKLY = "WEEKLY";
    public static final String SPENDING_INTERVAL_MONTHLY = "MONTHLY";
    public static final String SPENDING_INTERVAL_YEARLY = "YEARLY";
    public static final String SPENDING_INTERVAL_ALL_TIME = "ALL_TIME";

    // =========================================================================
    // Cardholder status constants
    // =========================================================================

    public static final String CARDHOLDER_STATUS_SUCCESS = "SUCCESS";
    public static final String CARDHOLDER_STATUS_PENDING = "PENDING";
    public static final String CARDHOLDER_STATUS_FAILED = "FAILED";

    // =========================================================================
    // Fields
    // =========================================================================

    @JsonProperty("card_id")
    private String cardId;

    @JsonProperty("card_product_id")
    private String cardProductId;

    @JsonProperty("card_order_id")
    private String cardOrderId;

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("card_bin")
    private String cardBin;

    @JsonProperty("card_scheme")
    private String cardScheme;

    @JsonProperty("card_status")
    private String cardStatus;

    @JsonProperty("card_currency")
    private String cardCurrency;

    @JsonProperty("card_limit")
    private String cardLimit;

    @JsonProperty("card_available_balance")
    private String cardAvailableBalance;

    @JsonProperty("available_balance")
    private String availableBalance;

    @JsonProperty("form_factor")
    private String formFactor;

    @JsonProperty("mode_type")
    private String modeType;

    @JsonProperty("order_status")
    private String orderStatus;

    @JsonProperty("no_pin_payment_amount")
    private String noPinPaymentAmount;

    @JsonProperty("cardholder")
    private CardholderData cardholder;

    @JsonProperty("spending_limits")
    private List<SpendingLimit> spendingLimits;

    @JsonProperty("spending_controls")
    private List<SpendingLimit> spendingControls;

    @JsonProperty("risk_control")
    private RiskControl riskControl;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    public CardData() {
    }

    // =========================================================================
    // Helper methods
    // =========================================================================

    public String getEffectiveAvailableBalance() {
        if (availableBalance != null && !availableBalance.isEmpty()) {
            return availableBalance;
        }
        return cardAvailableBalance;
    }

    public List<SpendingLimit> getEffectiveSpendingLimits() {
        if (spendingControls != null && !spendingControls.isEmpty()) {
            return spendingControls;
        }
        return spendingLimits;
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardProductId() {
        return cardProductId;
    }

    public void setCardProductId(String cardProductId) {
        this.cardProductId = cardProductId;
    }

    public String getCardOrderId() {
        return cardOrderId;
    }

    public void setCardOrderId(String cardOrderId) {
        this.cardOrderId = cardOrderId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardCurrency() {
        return cardCurrency;
    }

    public void setCardCurrency(String cardCurrency) {
        this.cardCurrency = cardCurrency;
    }

    public String getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(String cardLimit) {
        this.cardLimit = cardLimit;
    }

    public String getCardAvailableBalance() {
        return cardAvailableBalance;
    }

    public void setCardAvailableBalance(String cardAvailableBalance) {
        this.cardAvailableBalance = cardAvailableBalance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getNoPinPaymentAmount() {
        return noPinPaymentAmount;
    }

    public void setNoPinPaymentAmount(String noPinPaymentAmount) {
        this.noPinPaymentAmount = noPinPaymentAmount;
    }

    public CardholderData getCardholder() {
        return cardholder;
    }

    public void setCardholder(CardholderData cardholder) {
        this.cardholder = cardholder;
    }

    public List<SpendingLimit> getSpendingLimits() {
        return spendingLimits;
    }

    public void setSpendingLimits(List<SpendingLimit> spendingLimits) {
        this.spendingLimits = spendingLimits;
    }

    public List<SpendingLimit> getSpendingControls() {
        return spendingControls;
    }

    public void setSpendingControls(List<SpendingLimit> spendingControls) {
        this.spendingControls = spendingControls;
    }

    public RiskControl getRiskControl() {
        return riskControl;
    }

    public void setRiskControl(RiskControl riskControl) {
        this.riskControl = riskControl;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
