package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents business details for a company account creation request.
 */
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

    @JsonProperty("estimated_worker_count")
    private String estimatedWorkerCount;

    @JsonProperty("monthly_estimated_revenue")
    private MonthlyEstimatedRevenue monthlyEstimatedRevenue;

    @JsonProperty("account_purpose")
    private List<String> accountPurpose;

    @JsonProperty("other_purpose")
    private String otherPurpose;

    @JsonProperty("identifier")
    private BusinessIdentifier identifier;

    @JsonProperty("website_url")
    private String websiteUrl;

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

    public String getOtherPurpose() {
        return otherPurpose;
    }

    public void setOtherPurpose(String otherPurpose) {
        this.otherPurpose = otherPurpose;
    }

    public BusinessIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(BusinessIdentifier identifier) {
        this.identifier = identifier;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}
