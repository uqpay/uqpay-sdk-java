package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VirtualAccountApplicationResult {
    @JsonProperty("payment_method") private VirtualAccountPaymentMethod paymentMethod;
    @JsonProperty("status") private VirtualAccountApplicationResultStatus status;
    @JsonProperty("virtual_accounts") private List<VirtualAccountApplicationBankDetail> virtualAccounts;
    @JsonProperty("error") private VirtualAccountApplicationError error;

    public VirtualAccountPaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(VirtualAccountPaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public VirtualAccountApplicationResultStatus getStatus() { return status; }
    public void setStatus(VirtualAccountApplicationResultStatus status) { this.status = status; }
    public List<VirtualAccountApplicationBankDetail> getVirtualAccounts() { return virtualAccounts; }
    public void setVirtualAccounts(List<VirtualAccountApplicationBankDetail> virtualAccounts) { this.virtualAccounts = virtualAccounts; }
    public VirtualAccountApplicationError getError() { return error; }
    public void setError(VirtualAccountApplicationError error) { this.error = error; }
}
