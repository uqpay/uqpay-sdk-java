package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a sub-account creation request.
 * <p>
 * This handles the discriminated union for INDIVIDUAL vs COMPANY entity types
 * using the sub-account API schema which differs from the standard account
 * creation schema.
 * </p>
 * <p>
 * When {@code entityType} is INDIVIDUAL, the {@code individualInfo},
 * {@code identityVerification}, {@code expectedActivity}, and
 * {@code proofDocuments} fields should be set.
 * </p>
 * <p>
 * When {@code entityType} is COMPANY, the {@code companyInfo},
 * {@code companyAddress}, {@code ownershipDetails},
 * {@code businessDetails}, and {@code additionalDocuments} fields
 * should be set (or use {@code inherit = 1} to inherit from master).
 * </p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSubAccountRequest {

    @JsonProperty("entity_type")
    private EntityType entityType; // Required. COMPANY or INDIVIDUAL

    @JsonProperty("business_type")
    private String businessType; // Optional. ACQUIRING, BANKING, or ISSUING

    @JsonProperty("nickname")
    private String nickname; // Required. Display name for the sub-account, max 100 chars

    // ── INDIVIDUAL fields ───────────────────────────────────────────────────

    @JsonProperty("individual_info")
    private SubAccountIndividualInfo individualInfo; // Required for INDIVIDUAL. Personal information

    @JsonProperty("identity_verification")
    private IdentityVerification identityVerification; // Required for INDIVIDUAL. Identity verification documents

    @JsonProperty("expected_activity")
    private ExpectedActivity expectedActivity; // Required for INDIVIDUAL. Expected account activity details

    @JsonProperty("proof_documents")
    private ProofDocuments proofDocuments; // Optional for INDIVIDUAL. Proof of address or other documents

    // ── COMPANY fields ──────────────────────────────────────────────────────

    @JsonProperty("inherit")
    private Integer inherit; // Optional. 1 to inherit company info from master account, -1 otherwise

    @JsonProperty("company_info")
    private SubAccountCompanyInfo companyInfo; // Required for COMPANY (unless inherit=1). Company information

    @JsonProperty("company_address")
    private SubAccountCompanyAddress companyAddress; // Required for COMPANY (unless inherit=1). Company address details

    @JsonProperty("ownership_details")
    private SubAccountOwnershipDetails ownershipDetails; // Required for COMPANY. Ownership and representative details

    @JsonProperty("business_details")
    private SubAccountBusinessDetails businessDetails; // Required for COMPANY. Business operation details

    @JsonProperty("additional_documents")
    private AdditionalDocuments additionalDocuments; // Optional for COMPANY. Additional documents for onboarding

    // ── Common fields ───────────────────────────────────────────────────────

    @JsonProperty("tos_acceptance")
    private TosAcceptance tosAcceptance; // Optional. Terms of service acceptance (ip, date, user_agent)

    public CreateSubAccountRequest() {
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public SubAccountIndividualInfo getIndividualInfo() {
        return individualInfo;
    }

    public void setIndividualInfo(SubAccountIndividualInfo individualInfo) {
        this.individualInfo = individualInfo;
    }

    public IdentityVerification getIdentityVerification() {
        return identityVerification;
    }

    public void setIdentityVerification(IdentityVerification identityVerification) {
        this.identityVerification = identityVerification;
    }

    public ExpectedActivity getExpectedActivity() {
        return expectedActivity;
    }

    public void setExpectedActivity(ExpectedActivity expectedActivity) {
        this.expectedActivity = expectedActivity;
    }

    public ProofDocuments getProofDocuments() {
        return proofDocuments;
    }

    public void setProofDocuments(ProofDocuments proofDocuments) {
        this.proofDocuments = proofDocuments;
    }

    public Integer getInherit() {
        return inherit;
    }

    public void setInherit(Integer inherit) {
        this.inherit = inherit;
    }

    public SubAccountCompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(SubAccountCompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public SubAccountCompanyAddress getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(SubAccountCompanyAddress companyAddress) {
        this.companyAddress = companyAddress;
    }

    public SubAccountOwnershipDetails getOwnershipDetails() {
        return ownershipDetails;
    }

    public void setOwnershipDetails(SubAccountOwnershipDetails ownershipDetails) {
        this.ownershipDetails = ownershipDetails;
    }

    public SubAccountBusinessDetails getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(SubAccountBusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
    }

    public AdditionalDocuments getAdditionalDocuments() {
        return additionalDocuments;
    }

    public void setAdditionalDocuments(AdditionalDocuments additionalDocuments) {
        this.additionalDocuments = additionalDocuments;
    }

    public TosAcceptance getTosAcceptance() {
        return tosAcceptance;
    }

    public void setTosAcceptance(TosAcceptance tosAcceptance) {
        this.tosAcceptance = tosAcceptance;
    }
}
