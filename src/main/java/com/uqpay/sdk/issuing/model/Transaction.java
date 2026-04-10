package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {

    @JsonProperty("transaction_id")
    private String transactionId; // Required. Unique identifier for the transaction (UUID)

    @JsonProperty("card_id")
    private String cardId; // Required. Unique identifier for the card (UUID)

    @JsonProperty("transaction_type")
    private String transactionType; // Required. AUTHORIZATION, REFUND, FUND COLLECTION, ATM DEPOSIT, REVERSAL, VALIDATION, SETTLEMENT DEBIT/CREDIT/REVERSAL, CHARGEBACK DEBIT/CREDIT

    @JsonProperty("transaction_amount")
    private String transactionAmount; // Required. Original transaction amount

    @JsonProperty("transaction_currency")
    private String transactionCurrency; // Required. ISO 4217 currency code of the transaction

    @JsonProperty("billing_amount")
    private String billingAmount; // Required. Amount in billing currency

    @JsonProperty("billing_currency")
    private String billingCurrency; // Required. ISO 4217 billing currency code

    @JsonProperty("merchant_data")
    private MerchantData merchantData; // Required. Merchant information for the transaction

    @JsonProperty("transaction_status")
    private String transactionStatus; // Required. APPROVED, DECLINED, or PENDING

    @JsonProperty("card_number")
    private String cardNumber; // Required. Masked card number, e.g. "************5668"

    @JsonProperty("cardholder_id")
    private String cardholderId; // Required. The cardholder's unique identifier (UUID)

    @JsonProperty("short_transaction_id")
    private String shortTransactionId; // Required. Short unique identifier, e.g. "CT2024-03-01"

    @JsonProperty("original_transaction_id")
    private String originalTransactionId; // Required. UUID reference to original transaction

    @JsonProperty("transaction_fee")
    private String transactionFee; // Required. Transaction fee amount

    @JsonProperty("transaction_fee_currency")
    private String transactionFeeCurrency; // Required. ISO 4217 currency code of the fee

    @JsonProperty("fee_pass_through")
    private String feePassThrough; // Required. Y or N - whether the fee was deducted from card balance

    @JsonProperty("card_available_balance")
    private String cardAvailableBalance; // Required. Card available balance after transaction

    @JsonProperty("authorization_code")
    private String authorizationCode; // Required. Authorization code from the card network

    @JsonProperty("posted_time")
    private String postedTime; // Optional. Transaction posted time, ISO 8601 format

    @JsonProperty("description")
    private String description; // Required. Decline reason or approval remarks

    @JsonProperty("wallet_type")
    private String walletType; // Optional. Digital wallet type, e.g. ApplePay, GooglePay, GOOGLE ECOMMERCE

    @JsonProperty("transaction_time")
    private String transactionTime; // Required. Transaction occurrence time, ISO 8601 format

    public Transaction() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getBillingAmount() {
        return billingAmount;
    }

    public void setBillingAmount(String billingAmount) {
        this.billingAmount = billingAmount;
    }

    public String getBillingCurrency() {
        return billingCurrency;
    }

    public void setBillingCurrency(String billingCurrency) {
        this.billingCurrency = billingCurrency;
    }

    public MerchantData getMerchantData() {
        return merchantData;
    }

    public void setMerchantData(MerchantData merchantData) {
        this.merchantData = merchantData;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardholderId() {
        return cardholderId;
    }

    public void setCardholderId(String cardholderId) {
        this.cardholderId = cardholderId;
    }

    public String getShortTransactionId() {
        return shortTransactionId;
    }

    public void setShortTransactionId(String shortTransactionId) {
        this.shortTransactionId = shortTransactionId;
    }

    public String getOriginalTransactionId() {
        return originalTransactionId;
    }

    public void setOriginalTransactionId(String originalTransactionId) {
        this.originalTransactionId = originalTransactionId;
    }

    public String getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(String transactionFee) {
        this.transactionFee = transactionFee;
    }

    public String getTransactionFeeCurrency() {
        return transactionFeeCurrency;
    }

    public void setTransactionFeeCurrency(String transactionFeeCurrency) {
        this.transactionFeeCurrency = transactionFeeCurrency;
    }

    public String getFeePassThrough() {
        return feePassThrough;
    }

    public void setFeePassThrough(String feePassThrough) {
        this.feePassThrough = feePassThrough;
    }

    public String getCardAvailableBalance() {
        return cardAvailableBalance;
    }

    public void setCardAvailableBalance(String cardAvailableBalance) {
        this.cardAvailableBalance = cardAvailableBalance;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }
}
