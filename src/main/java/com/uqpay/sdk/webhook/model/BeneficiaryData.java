package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeneficiaryData {

    // =========================================================================
    // Beneficiary status constants
    // =========================================================================

    public static final String BENEFICIARY_STATUS_ACTIVE = "ACTIVE";
    public static final String BENEFICIARY_STATUS_INACTIVE = "INACTIVE";
    public static final String BENEFICIARY_STATUS_PENDING = "PENDING";
    public static final String BENEFICIARY_STATUS_REJECTED = "REJECTED";

    // =========================================================================
    // Beneficiary entity type constants
    // =========================================================================

    public static final String BENEFICIARY_ENTITY_TYPE_INDIVIDUAL = "INDIVIDUAL";
    public static final String BENEFICIARY_ENTITY_TYPE_COMPANY = "COMPANY";

    // =========================================================================
    // Payment type constants
    // =========================================================================

    public static final String PAYMENT_TYPE_LOCAL = "LOCAL";
    public static final String PAYMENT_TYPE_INTERNATIONAL = "INTERNATIONAL";

    // =========================================================================
    // Fields
    // =========================================================================

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_currency_code")
    private String accountCurrencyCode;

    @JsonProperty("beneficiary_id")
    private String beneficiaryId;

    @JsonProperty("beneficiary_first_name")
    private String beneficiaryFirstName;

    @JsonProperty("beneficiary_last_name")
    private String beneficiaryLastName;

    @JsonProperty("beneficiary_nickname")
    private String beneficiaryNickname;

    @JsonProperty("beneficiary_company_name")
    private String beneficiaryCompanyName;

    @JsonProperty("beneficiary_email")
    private String beneficiaryEmail;

    @JsonProperty("beneficiary_entity_type")
    private String beneficiaryEntityType;

    @JsonProperty("beneficiary_status")
    private String beneficiaryStatus;

    @JsonProperty("beneficiary_address")
    private String beneficiaryAddress;

    @JsonProperty("beneficiary_bank_details")
    private String beneficiaryBankDetails;

    @JsonProperty("bank_country_code")
    private String bankCountryCode;

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("short_reference_id")
    private String shortReferenceId;

    @JsonProperty("direct_id")
    private String directId;

    public BeneficiaryData() {
    }

    // =========================================================================
    // Helper methods
    // =========================================================================

    public BeneficiaryAddress getBeneficiaryAddressParsed() {
        if (beneficiaryAddress == null || beneficiaryAddress.isEmpty()) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(beneficiaryAddress, BeneficiaryAddress.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse beneficiary address: " + e.getMessage(), e);
        }
    }

    public BeneficiaryBankDetails getBeneficiaryBankDetailsParsed() {
        if (beneficiaryBankDetails == null || beneficiaryBankDetails.isEmpty()) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(beneficiaryBankDetails, BeneficiaryBankDetails.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse beneficiary bank details: " + e.getMessage(), e);
        }
    }

    public String getFullName() {
        if ((beneficiaryFirstName == null || beneficiaryFirstName.isEmpty())
                && (beneficiaryLastName == null || beneficiaryLastName.isEmpty())) {
            return "";
        }
        if (beneficiaryFirstName == null || beneficiaryFirstName.isEmpty()) {
            return beneficiaryLastName;
        }
        if (beneficiaryLastName == null || beneficiaryLastName.isEmpty()) {
            return beneficiaryFirstName;
        }
        return beneficiaryFirstName + " " + beneficiaryLastName;
    }

    public boolean isIndividual() {
        return BENEFICIARY_ENTITY_TYPE_INDIVIDUAL.equals(beneficiaryEntityType);
    }

    public boolean isCompany() {
        return BENEFICIARY_ENTITY_TYPE_COMPANY.equals(beneficiaryEntityType);
    }

    public boolean isActive() {
        return BENEFICIARY_STATUS_ACTIVE.equals(beneficiaryStatus);
    }

    public boolean isLocalPayment() {
        return PAYMENT_TYPE_LOCAL.equals(paymentType);
    }

    public boolean isInternationalPayment() {
        return PAYMENT_TYPE_INTERNATIONAL.equals(paymentType);
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountCurrencyCode() {
        return accountCurrencyCode;
    }

    public void setAccountCurrencyCode(String accountCurrencyCode) {
        this.accountCurrencyCode = accountCurrencyCode;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getBeneficiaryFirstName() {
        return beneficiaryFirstName;
    }

    public void setBeneficiaryFirstName(String beneficiaryFirstName) {
        this.beneficiaryFirstName = beneficiaryFirstName;
    }

    public String getBeneficiaryLastName() {
        return beneficiaryLastName;
    }

    public void setBeneficiaryLastName(String beneficiaryLastName) {
        this.beneficiaryLastName = beneficiaryLastName;
    }

    public String getBeneficiaryNickname() {
        return beneficiaryNickname;
    }

    public void setBeneficiaryNickname(String beneficiaryNickname) {
        this.beneficiaryNickname = beneficiaryNickname;
    }

    public String getBeneficiaryCompanyName() {
        return beneficiaryCompanyName;
    }

    public void setBeneficiaryCompanyName(String beneficiaryCompanyName) {
        this.beneficiaryCompanyName = beneficiaryCompanyName;
    }

    public String getBeneficiaryEmail() {
        return beneficiaryEmail;
    }

    public void setBeneficiaryEmail(String beneficiaryEmail) {
        this.beneficiaryEmail = beneficiaryEmail;
    }

    public String getBeneficiaryEntityType() {
        return beneficiaryEntityType;
    }

    public void setBeneficiaryEntityType(String beneficiaryEntityType) {
        this.beneficiaryEntityType = beneficiaryEntityType;
    }

    public String getBeneficiaryStatus() {
        return beneficiaryStatus;
    }

    public void setBeneficiaryStatus(String beneficiaryStatus) {
        this.beneficiaryStatus = beneficiaryStatus;
    }

    public String getBeneficiaryAddress() {
        return beneficiaryAddress;
    }

    public void setBeneficiaryAddress(String beneficiaryAddress) {
        this.beneficiaryAddress = beneficiaryAddress;
    }

    public String getBeneficiaryBankDetails() {
        return beneficiaryBankDetails;
    }

    public void setBeneficiaryBankDetails(String beneficiaryBankDetails) {
        this.beneficiaryBankDetails = beneficiaryBankDetails;
    }

    public String getBankCountryCode() {
        return bankCountryCode;
    }

    public void setBankCountryCode(String bankCountryCode) {
        this.bankCountryCode = bankCountryCode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getShortReferenceId() {
        return shortReferenceId;
    }

    public void setShortReferenceId(String shortReferenceId) {
        this.shortReferenceId = shortReferenceId;
    }

    public String getDirectId() {
        return directId;
    }

    public void setDirectId(String directId) {
        this.directId = directId;
    }
}
