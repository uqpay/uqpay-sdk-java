package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Representative {

    @JsonProperty("representative_id")
    private String representativeId;

    @JsonProperty("roles")
    private String roles;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("local_name")
    private String localName;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("share_percentage")
    private String sharePercentage;

    @JsonProperty("area_code")
    private String areaCode;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("tax_number")
    private String taxNumber;

    @JsonProperty("identification")
    private Identification identification;

    @JsonProperty("residential_address")
    private Address residentialAddress;

    @JsonProperty("is_applicant")
    private boolean isApplicant;

    @JsonProperty("as_applicant")
    private boolean asApplicant;

    @JsonProperty("idv_status")
    private String idvStatus;

    @JsonProperty("idv_id")
    private String idvId;

    @JsonProperty("citizenship_status")
    private int citizenshipStatus;

    public Representative() {
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

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
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

    public String getSharePercentage() {
        return sharePercentage;
    }

    public void setSharePercentage(String sharePercentage) {
        this.sharePercentage = sharePercentage;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
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

    public boolean isApplicant() {
        return isApplicant;
    }

    public void setApplicant(boolean applicant) {
        isApplicant = applicant;
    }

    public boolean isAsApplicant() {
        return asApplicant;
    }

    public void setAsApplicant(boolean asApplicant) {
        this.asApplicant = asApplicant;
    }

    public String getIdvStatus() {
        return idvStatus;
    }

    public void setIdvStatus(String idvStatus) {
        this.idvStatus = idvStatus;
    }

    public String getIdvId() {
        return idvId;
    }

    public void setIdvId(String idvId) {
        this.idvId = idvId;
    }

    public int getCitizenshipStatus() {
        return citizenshipStatus;
    }

    public void setCitizenshipStatus(int citizenshipStatus) {
        this.citizenshipStatus = citizenshipStatus;
    }
}
