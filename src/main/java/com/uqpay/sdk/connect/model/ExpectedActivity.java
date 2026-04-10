package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents expected activity details for a sub-account creation request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpectedActivity {

    @JsonProperty("account_purpose")
    private List<String> accountPurpose;

    @JsonProperty("other_purpose")
    private String otherPurpose;

    @JsonProperty("banking_countries")
    private List<String> bankingCountries;

    @JsonProperty("banking_currencies")
    private List<String> bankingCurrencies;

    @JsonProperty("internationally")
    private Integer internationally;

    @JsonProperty("turnover_monthly")
    private String turnoverMonthly;

    @JsonProperty("turnover_monthly_currency")
    private String turnoverMonthlyCurrency;

    public ExpectedActivity() {
    }

    public List<String> getAccountPurpose() {
        return accountPurpose;
    }

    public void setAccountPurpose(List<String> accountPurpose) {
        this.accountPurpose = accountPurpose;
    }

    public String getOtherPurpose() {
        return otherPurpose;
    }

    public void setOtherPurpose(String otherPurpose) {
        this.otherPurpose = otherPurpose;
    }

    public List<String> getBankingCountries() {
        return bankingCountries;
    }

    public void setBankingCountries(List<String> bankingCountries) {
        this.bankingCountries = bankingCountries;
    }

    public List<String> getBankingCurrencies() {
        return bankingCurrencies;
    }

    public void setBankingCurrencies(List<String> bankingCurrencies) {
        this.bankingCurrencies = bankingCurrencies;
    }

    public Integer getInternationally() {
        return internationally;
    }

    public void setInternationally(Integer internationally) {
        this.internationally = internationally;
    }

    public String getTurnoverMonthly() {
        return turnoverMonthly;
    }

    public void setTurnoverMonthly(String turnoverMonthly) {
        this.turnoverMonthly = turnoverMonthly;
    }

    public String getTurnoverMonthlyCurrency() {
        return turnoverMonthlyCurrency;
    }

    public void setTurnoverMonthlyCurrency(String turnoverMonthlyCurrency) {
        this.turnoverMonthlyCurrency = turnoverMonthlyCurrency;
    }
}
