package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountData {

    // =========================================================================
    // Account status constants
    // =========================================================================

    public static final String ACCOUNT_STATUS_PROCESSING = "PROCESSING";
    public static final String ACCOUNT_STATUS_ACTIVE = "ACTIVE";
    public static final String ACCOUNT_STATUS_SUSPENDED = "SUSPENDED";
    public static final String ACCOUNT_STATUS_CLOSED = "CLOSED";

    // =========================================================================
    // Verification status constants
    // =========================================================================

    public static final String VERIFICATION_STATUS_PENDING = "PENDING";
    public static final String VERIFICATION_STATUS_APPROVED = "APPROVED";
    public static final String VERIFICATION_STATUS_REJECTED = "REJECTED";

    // =========================================================================
    // Entity type constants
    // =========================================================================

    public static final String ENTITY_TYPE_COMPANY = "COMPANY";
    public static final String ENTITY_TYPE_INDIVIDUAL = "INDIVIDUAL";

    // =========================================================================
    // Business structure constants
    // =========================================================================

    public static final String BUSINESS_STRUCTURE_SOLE_PROPRIETOR = "SOLE_PROPRIETOR";
    public static final String BUSINESS_STRUCTURE_PARTNERSHIP = "PARTNERSHIP";
    public static final String BUSINESS_STRUCTURE_CORPORATION = "CORPORATION";
    public static final String BUSINESS_STRUCTURE_LLC = "LLC";
    public static final String BUSINESS_STRUCTURE_NON_PROFIT = "NON_PROFIT";
    public static final String BUSINESS_STRUCTURE_GOVERNMENT_ENTITY = "GOVERNMENT_ENTITY";
    public static final String BUSINESS_STRUCTURE_PUBLICLY_TRADED = "PUBLICLY_TRADED";
    public static final String BUSINESS_STRUCTURE_PRIVATELY_HELD = "PRIVATELY_HELD";

    // =========================================================================
    // Representative role constants
    // =========================================================================

    public static final String ROLE_DIRECTOR = "DIRECTOR";
    public static final String ROLE_OWNER = "OWNER";
    public static final String ROLE_SHAREHOLDER = "SHAREHOLDER";
    public static final String ROLE_AUTHORIZED_USER = "AUTHORIZED_USER";
    public static final String ROLE_UBO = "UBO";

    // =========================================================================
    // Identification type constants
    // =========================================================================

    public static final String IDENTIFICATION_TYPE_PASSPORT = "PASSPORT";
    public static final String IDENTIFICATION_TYPE_NATIONAL_ID = "NATIONAL_ID";
    public static final String IDENTIFICATION_TYPE_DRIVER_LICENSE = "DRIVER_LICENSE";

    // =========================================================================
    // Account purpose constants
    // =========================================================================

    public static final String ACCOUNT_PURPOSE_COLLECTION = "COLLECTION";
    public static final String ACCOUNT_PURPOSE_PAYOUT = "PAYOUT";
    public static final String ACCOUNT_PURPOSE_BILL_PAYMENT = "bill_payment";

    // =========================================================================
    // Fields
    // =========================================================================

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("direct_id")
    private String directId;

    @JsonProperty("short_reference_id")
    private String shortReferenceId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("country")
    private String country;

    @JsonProperty("status")
    private String status;

    @JsonProperty("idv_status")
    private String idvStatus;

    @JsonProperty("verification_status")
    private String verificationStatus;

    @JsonProperty("review_reason")
    private String reviewReason;

    @JsonProperty("entity_type")
    private String entityType;

    @JsonProperty("contact_details")
    private ContactDetails contactDetails;

    @JsonProperty("business_details")
    private BusinessDetails businessDetails;

    @JsonProperty("registration_address")
    private Address registrationAddress;

    @JsonProperty("business_address")
    private List<Address> businessAddress;

    @JsonProperty("representatives")
    private List<Representative> representatives;

    @JsonProperty("source")
    private String source;

    public AccountData() {
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

    public String getDirectId() {
        return directId;
    }

    public void setDirectId(String directId) {
        this.directId = directId;
    }

    public String getShortReferenceId() {
        return shortReferenceId;
    }

    public void setShortReferenceId(String shortReferenceId) {
        this.shortReferenceId = shortReferenceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdvStatus() {
        return idvStatus;
    }

    public void setIdvStatus(String idvStatus) {
        this.idvStatus = idvStatus;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getReviewReason() {
        return reviewReason;
    }

    public void setReviewReason(String reviewReason) {
        this.reviewReason = reviewReason;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public BusinessDetails getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
    }

    public Address getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(Address registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public List<Address> getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(List<Address> businessAddress) {
        this.businessAddress = businessAddress;
    }

    public List<Representative> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(List<Representative> representatives) {
        this.representatives = representatives;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
