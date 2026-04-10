package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents a company representative for account creation.
 * This model matches the Create Account API spec structure.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRepresentative {

    @JsonProperty("representative_id")
    private String representativeId; // Optional. Unique identifier of the representative

    @JsonProperty("roles")
    private String roles; // Required. DIRECTOR, BENEFICIAL_OWNER, DIRECTOR_BENEFICIAL_OWNER, or AUTHORISED_PERSON

    @JsonProperty("first_name")
    private String firstName; // Required. First name matching official documents, max 100 chars

    @JsonProperty("last_name")
    private String lastName; // Required. Last name matching official documents, max 100 chars

    @JsonProperty("nationality")
    private String nationality; // Required. ISO 3166-1 alpha-2 country code, e.g. "US"

    @JsonProperty("date_of_birth")
    private String dateOfBirth; // Required. Format: YYYY-MM-DD (ISO date)

    @JsonProperty("identification")
    private Identification identification; // Required. Identification document details for KYC

    @JsonProperty("residential_address")
    private Address residentialAddress; // Optional. Residential address of the representative

    @JsonProperty("as_applicant")
    private Boolean asApplicant; // Optional. Whether this representative is the applicant, default false

    @JsonProperty("share_percentage")
    private String sharePercentage; // Required if role is not DIRECTOR. Ownership share percentage

    @JsonProperty("other_documentation")
    @JsonAlias({"other_documents", "other_doucments"})
    private List<DocumentItem> otherDocumentation; // Optional. Additional supporting documents

    public AccountRepresentative() {
    }

    public String getRepresentativeId() {
        return representativeId;
    }

    public void setRepresentativeId(String representativeId) {
        this.representativeId = representativeId;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    public Address getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(Address residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public Boolean getAsApplicant() {
        return asApplicant;
    }

    public void setAsApplicant(Boolean asApplicant) {
        this.asApplicant = asApplicant;
    }

    public String getSharePercentage() {
        return sharePercentage;
    }

    public void setSharePercentage(String sharePercentage) {
        this.sharePercentage = sharePercentage;
    }

    public List<DocumentItem> getOtherDocumentation() {
        return otherDocumentation;
    }

    public void setOtherDocumentation(List<DocumentItem> otherDocumentation) {
        this.otherDocumentation = otherDocumentation;
    }
}
