package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePaymentIntentRequest {

    @JsonProperty("amount")
    private String amount; // Optional. Payment amount to charge

    @JsonProperty("currency")
    private String currency; // Optional. ISO 4217 three-letter currency code

    @JsonProperty("merchant_order_id")
    private String merchantOrderId; // Optional. Merchant reference ID from merchant's system

    @JsonProperty("description")
    private String description; // Optional. Customer-facing descriptor, max 32 chars

    @JsonProperty("return_url")
    private String returnUrl; // Optional. Redirect URL after payment authentication, max 1024 chars

    @JsonProperty("metadata")
    private Object metadata; // Optional. Custom key-value JSON object, max 512 bytes

    @JsonProperty("payment_orders")
    private PaymentOrders paymentOrders; // Optional. Purchase order with type and product details

    @JsonProperty("customer_id")
    private String customerId; // Optional. Unique customer ID for recurring payments; omit when using customer object

    @JsonProperty("customer")
    private PaymentCustomer customer; // Optional. Customer details; omit when customer_id is provided

    public UpdatePaymentIntentRequest() {
    }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getMerchantOrderId() { return merchantOrderId; }
    public void setMerchantOrderId(String merchantOrderId) { this.merchantOrderId = merchantOrderId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReturnUrl() { return returnUrl; }
    public void setReturnUrl(String returnUrl) { this.returnUrl = returnUrl; }

    @SuppressWarnings("unchecked")
    public Map<String, String> getMetadata() {
        if (metadata instanceof Map) {
            return (Map<String, String>) metadata;
        }
        return Collections.emptyMap();
    }

    public void setMetadata(Object metadata) { this.metadata = metadata; }

    public PaymentOrders getPaymentOrders() { return paymentOrders; }
    public void setPaymentOrders(PaymentOrders paymentOrders) { this.paymentOrders = paymentOrders; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public PaymentCustomer getCustomer() { return customer; }
    public void setCustomer(PaymentCustomer customer) { this.customer = customer; }
}
