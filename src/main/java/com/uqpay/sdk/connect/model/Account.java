package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents a Connect account.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {

    @JsonProperty("account_id")
    private String accountId; // Required. Unique account identifier, UUID format

    @JsonProperty("entity_type")
    private EntityType entityType; // Required. COMPANY or INDIVIDUAL

    @JsonProperty("short_reference_id")
    private String shortReferenceId; // Optional. Short reference code, e.g. "P220406-LLCVLRM"

    @JsonProperty("status")
    private String status; // Optional. ACTIVE, PROCESSING, INACTIVE, or CLOSED

    @JsonProperty("verification_status")
    private String verificationStatus; // Optional. APPROVED, PENDING, REJECT, EXPIRED, or RETURN

    @JsonProperty("business_code")
    private List<String> businessCode; // Required. Business types: BANKING, ACQUIRING, or ISSUING

    @JsonProperty("account_name")
    private String accountName; // Optional. Display name of the account

    @JsonProperty("email")
    private String email; // Optional. Email address associated with the account

    @JsonProperty("country")
    private String country; // Optional. ISO 3166-1 alpha-2 country code, e.g. "US"

    @JsonProperty("review_reason")
    private String reviewReason; // Optional. Reason for review rejection or return, if applicable

    @JsonProperty("contact_details")
    private ContactDetails contactDetails; // Optional. Contact details (email and phone)

    @JsonProperty("business_details")
    private BusinessDetails businessDetails; // Optional. Business details, present for COMPANY entity type

    @JsonProperty("registration_address")
    private Address registrationAddress; // Optional. Registered address of the company

    @JsonProperty("business_address")
    private List<Address> businessAddress; // Optional. Business operating addresses

    @JsonProperty("representatives")
    private List<AccountRepresentative> representatives; // Optional. Company representatives (directors, shareholders, etc.)

    @JsonProperty("documents")
    private List<DocumentItem> documents; // Optional. Supporting documents for verification

    @JsonProperty("person_details")
    private PersonDetails personDetails; // Optional. Person details, present for INDIVIDUAL entity type

    @JsonProperty("residential_address")
    private Address residentialAddress; // Optional. Residential address for INDIVIDUAL entity type

    @JsonProperty("tos_acceptance")
    private TosAcceptance tosAcceptance; // Optional. Terms of service acceptance details

    public Account() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getShortReferenceId() {
        return shortReferenceId;
    }

    public void setShortReferenceId(String shortReferenceId) {
        this.shortReferenceId = shortReferenceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public List<String> getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(List<String> businessCode) {
        this.businessCode = businessCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getReviewReason() {
        return reviewReason;
    }

    public void setReviewReason(String reviewReason) {
        this.reviewReason = reviewReason;
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

    public List<AccountRepresentative> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(List<AccountRepresentative> representatives) {
        this.representatives = representatives;
    }

    public List<DocumentItem> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentItem> documents) {
        this.documents = documents;
    }

    public PersonDetails getPersonDetails() {
        return personDetails;
    }

    public void setPersonDetails(PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

    public Address getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(Address residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public TosAcceptance getTosAcceptance() {
        return tosAcceptance;
    }

    public void setTosAcceptance(TosAcceptance tosAcceptance) {
        this.tosAcceptance = tosAcceptance;
    }
}
