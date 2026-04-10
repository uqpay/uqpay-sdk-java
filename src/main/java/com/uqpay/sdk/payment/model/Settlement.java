package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Settlement {

    @JsonProperty("settlement_id")
    private String settlementId; // Required. Settlement identifier, UUID format

    @JsonProperty("account_id")
    private String accountId; // Required. Associated account identifier, UUID format

    @JsonProperty("account_name")
    private String accountName; // Required. Account name

    @JsonProperty("source_type")
    private String sourceType; // Required. Transaction source type, e.g. PAYMENT

    @JsonProperty("transaction_type")
    private String transactionType; // Required. Transaction type, e.g. PAYMENT

    @JsonProperty("merchant_order_id")
    private String merchantOrderId; // Optional. Merchant's own reference ID

    @JsonProperty("payment_intent_id")
    private String paymentIntentId; // Required. Payment intent identifier

    @JsonProperty("payment_method")
    private String paymentMethod; // Required. Payment method: card, alipaycn, alipayhk, unionpay, wechatpay

    @JsonProperty("transaction_create_date")
    private String transactionCreateDate; // Required. ISO 8601 datetime (UTC), transaction creation time

    @JsonProperty("transaction_amount")
    private String transactionAmount; // Required. Original transaction amount

    @JsonProperty("transaction_currency")
    private String transactionCurrency; // Required. ISO 4217 currency code for the transaction

    @JsonProperty("transaction_date")
    private String transactionDate; // Required. ISO 8601 datetime (UTC), transaction completion time

    @JsonProperty("settlement_amount")
    private String settlementAmount; // Required. Settlement amount

    @JsonProperty("settlement_currency")
    private String settlementCurrency; // Required. ISO 4217 currency code for the settlement

    @JsonProperty("net_settlement_amount")
    private String netSettlementAmount; // Required. Net amount after fees

    @JsonProperty("exchange_rate")
    private String exchangeRate; // Required. Rate applied from transaction_currency to settlement_currency

    @JsonProperty("fee_currency")
    private String feeCurrency; // Required. ISO 4217 currency code for all fee fields

    @JsonProperty("interchange_fee")
    private String interchangeFee; // Required. Interchange fee amount

    @JsonProperty("scheme_fee")
    private String schemeFee; // Required. Scheme fee amount

    @JsonProperty("transcation_fee") // Note: API uses misspelled key "transcation_fee"
    private String transactionFee; // Required. Transaction fee amount

    @JsonProperty("return_fee")
    private String returnFee; // Required. Return fee amount

    @JsonProperty("total_fee_amount")
    private String totalFeeAmount; // Required. Sum of all fees

    @JsonProperty("settlement_status")
    private String settlementStatus; // Required. Settlement status: SUCCESS

    @JsonProperty("settlement_batch_id")
    private String settlementBatchId; // Required. Settlement batch identifier

    @JsonProperty("settlement_create_date")
    private String settlementCreateDate; // Required. ISO 8601 datetime (UTC), settlement record creation time

    @JsonProperty("settlement_date")
    private String settlementDate; // Required. ISO 8601 datetime (UTC), settlement completion time

    public Settlement() {
    }

    public String getSettlementId() { return settlementId; }
    public void setSettlementId(String settlementId) { this.settlementId = settlementId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getSourceType() { return sourceType; }
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getMerchantOrderId() { return merchantOrderId; }
    public void setMerchantOrderId(String merchantOrderId) { this.merchantOrderId = merchantOrderId; }

    public String getPaymentIntentId() { return paymentIntentId; }
    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getTransactionCreateDate() { return transactionCreateDate; }
    public void setTransactionCreateDate(String transactionCreateDate) { this.transactionCreateDate = transactionCreateDate; }

    public String getTransactionAmount() { return transactionAmount; }
    public void setTransactionAmount(String transactionAmount) { this.transactionAmount = transactionAmount; }

    public String getTransactionCurrency() { return transactionCurrency; }
    public void setTransactionCurrency(String transactionCurrency) { this.transactionCurrency = transactionCurrency; }

    public String getTransactionDate() { return transactionDate; }
    public void setTransactionDate(String transactionDate) { this.transactionDate = transactionDate; }

    public String getSettlementAmount() { return settlementAmount; }
    public void setSettlementAmount(String settlementAmount) { this.settlementAmount = settlementAmount; }

    public String getSettlementCurrency() { return settlementCurrency; }
    public void setSettlementCurrency(String settlementCurrency) { this.settlementCurrency = settlementCurrency; }

    public String getNetSettlementAmount() { return netSettlementAmount; }
    public void setNetSettlementAmount(String netSettlementAmount) { this.netSettlementAmount = netSettlementAmount; }

    public String getExchangeRate() { return exchangeRate; }
    public void setExchangeRate(String exchangeRate) { this.exchangeRate = exchangeRate; }

    public String getFeeCurrency() { return feeCurrency; }
    public void setFeeCurrency(String feeCurrency) { this.feeCurrency = feeCurrency; }

    public String getInterchangeFee() { return interchangeFee; }
    public void setInterchangeFee(String interchangeFee) { this.interchangeFee = interchangeFee; }

    public String getSchemeFee() { return schemeFee; }
    public void setSchemeFee(String schemeFee) { this.schemeFee = schemeFee; }

    public String getTransactionFee() { return transactionFee; }
    public void setTransactionFee(String transactionFee) { this.transactionFee = transactionFee; }

    public String getReturnFee() { return returnFee; }
    public void setReturnFee(String returnFee) { this.returnFee = returnFee; }

    public String getTotalFeeAmount() { return totalFeeAmount; }
    public void setTotalFeeAmount(String totalFeeAmount) { this.totalFeeAmount = totalFeeAmount; }

    public String getSettlementStatus() { return settlementStatus; }
    public void setSettlementStatus(String settlementStatus) { this.settlementStatus = settlementStatus; }

    public String getSettlementBatchId() { return settlementBatchId; }
    public void setSettlementBatchId(String settlementBatchId) { this.settlementBatchId = settlementBatchId; }

    public String getSettlementCreateDate() { return settlementCreateDate; }
    public void setSettlementCreateDate(String settlementCreateDate) { this.settlementCreateDate = settlementCreateDate; }

    public String getSettlementDate() { return settlementDate; }
    public void setSettlementDate(String settlementDate) { this.settlementDate = settlementDate; }
}
