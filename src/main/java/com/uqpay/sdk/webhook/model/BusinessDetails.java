package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessDetails {

    @JsonProperty("legal_entity_name")
    private String legalEntityName;

    @JsonProperty("legal_entity_name_english")
    private String legalEntityNameEnglish;

    @JsonProperty("incorporation_date")
    private String incorporationDate;

    @JsonProperty("registration_number")
    private String registrationNumber;

    @JsonProperty("business_structure")
    private String businessStructure;

    @JsonProperty("product_description")
    private String productDescription;

    @JsonProperty("merchant_category_code")
    private String merchantCategoryCode;

    @JsonProperty("mcc")
    private String mcc;

    @JsonProperty("estimated_worker_count")
    private String estimatedWorkerCount;

    @JsonProperty("monthly_estimated_revenue")
    private MonthlyEstimatedRevenue monthlyEstimatedRevenue;

    @JsonProperty("account_purpose")
    private List<String> accountPurpose;

    @JsonProperty("identifier")
    private Identifier identifier;

    @JsonProperty("website_url")
    private String websiteUrl;

    @JsonProperty("country")
    private String country;

    @JsonProperty("industry_code")
    private String industryCode;

    @JsonProperty("banking_countries")
    private List<String> bankingCountries;

    @JsonProperty("banking_currencies")
    private List<String> bankingCurrencies;

    @JsonProperty("business_address")
    private List<Address> businessAddress;

    @JsonProperty("registration_address")
    private Address registrationAddress;

    @JsonProperty("identification_expiry_date")
    private String identificationExpiryDate;

    @JsonProperty("identification_issue_date")
    private String identificationIssueDate;

    public BusinessDetails() {
    }

    public String getLegalEntityName() {
        return legalEntityName;
    }

    public void setLegalEntityName(String legalEntityName) {
        this.legalEntityName = legalEntityName;
    }

    public String getLegalEntityNameEnglish() {
        return legalEntityNameEnglish;
    }

    public void setLegalEntityNameEnglish(String legalEntityNameEnglish) {
        this.legalEntityNameEnglish = legalEntityNameEnglish;
    }

    public String getIncorporationDate() {
        return incorporationDate;
    }

    public void setIncorporationDate(String incorporationDate) {
        this.incorporationDate = incorporationDate;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBusinessStructure() {
        return businessStructure;
    }

    public void setBusinessStructure(String businessStructure) {
        this.businessStructure = businessStructure;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getEstimatedWorkerCount() {
        return estimatedWorkerCount;
    }

    public void setEstimatedWorkerCount(String estimatedWorkerCount) {
        this.estimatedWorkerCount = estimatedWorkerCount;
    }

    public MonthlyEstimatedRevenue getMonthlyEstimatedRevenue() {
        return monthlyEstimatedRevenue;
    }

    public void setMonthlyEstimatedRevenue(MonthlyEstimatedRevenue monthlyEstimatedRevenue) {
        this.monthlyEstimatedRevenue = monthlyEstimatedRevenue;
    }

    public List<String> getAccountPurpose() {
        return accountPurpose;
    }

    public void setAccountPurpose(List<String> accountPurpose) {
        this.accountPurpose = accountPurpose;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
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

    public List<Address> getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(List<Address> businessAddress) {
        this.businessAddress = businessAddress;
    }

    public Address getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(Address registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public String getIdentificationExpiryDate() {
        return identificationExpiryDate;
    }

    public void setIdentificationExpiryDate(String identificationExpiryDate) {
        this.identificationExpiryDate = identificationExpiryDate;
    }

    public String getIdentificationIssueDate() {
        return identificationIssueDate;
    }

    public void setIdentificationIssueDate(String identificationIssueDate) {
        this.identificationIssueDate = identificationIssueDate;
    }
}
