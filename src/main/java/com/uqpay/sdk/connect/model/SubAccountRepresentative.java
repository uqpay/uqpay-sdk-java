package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents a representative in a company sub-account creation request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubAccountRepresentative {

    @JsonProperty("legal_first_name_english")
    private String legalFirstNameEnglish; // Required. Legal first name in English, max 100 chars

    @JsonProperty("legal_last_name_english")
    private String legalLastNameEnglish; // Required. Legal last name in English, max 100 chars

    @JsonProperty("name_in_other_language")
    private String nameInOtherLanguage; // Optional. Name in other language, max 100 chars

    @JsonProperty("email_address")
    private String emailAddress; // Optional. Email address, max 100 chars

    @JsonProperty("is_applicant")
    private String isApplicant; // Optional. "0" or "1", indicates if representative is the applicant

    @JsonProperty("job_title")
    private String jobTitle; // Required. DIRECTOR, BENEFICIAL_OWNER, BENEFICIAL_OWNER_AND_DIRECTOR, or AUTHORISED_PERSON

    @JsonProperty("ownership_percentage")
    private Double ownershipPercentage; // Optional. Ownership share percentage

    @JsonProperty("nationality")
    private String nationality; // Required. ISO 3166-1 alpha-2 country code

    @JsonProperty("tax_number")
    private String taxNumber; // Optional. Tax identification number, max 100 chars

    @JsonProperty("phone_number")
    private String phoneNumber; // Optional. Phone number, max 25 chars

    @JsonProperty("date_of_birth")
    private String dateOfBirth; // Required. Format: YYYY-MM-DD

    @JsonProperty("country_or_territory")
    private String countryOrTerritory; // Optional. ISO 3166-1 alpha-2 country code for residential address

    @JsonProperty("street_address")
    private String streetAddress; // Optional. Street address, max 100 chars

    @JsonProperty("apartment_suite_or_floor")
    private String apartmentSuiteOrFloor; // Optional. Apartment, suite, or floor, max 100 chars

    @JsonProperty("city")
    private String city; // Optional. City name, max 100 chars

    @JsonProperty("state")
    private String state; // Optional. State or province

    @JsonProperty("postal_code")
    private String postalCode; // Optional. Postal code, max 100 chars

    @JsonProperty("identification_type")
    private String identificationType; // Required. PASSPORT, DRIVERS_LICENSE, or NATIONAL_ID

    @JsonProperty("identification_value")
    private String identificationValue; // Required. ID number on the document, max 100 chars

    @JsonProperty("identity_docs")
    private List<String> identityDocs; // Optional. Base64-encoded images or file IDs for identity documents

    @JsonProperty("other_documents")
    private List<OtherProof> otherDocuments; // Optional. Additional documents (type: PROOF_OF_ADDRESS or OTHERS)

    @JsonProperty("face_docs")
    private List<String> faceDocs; // Optional. Base64-encoded images or file IDs for face verification

    public SubAccountRepresentative() {
    }

    public String getLegalFirstNameEnglish() {
        return legalFirstNameEnglish;
    }

    public void setLegalFirstNameEnglish(String legalFirstNameEnglish) {
        this.legalFirstNameEnglish = legalFirstNameEnglish;
    }

    public String getLegalLastNameEnglish() {
        return legalLastNameEnglish;
    }

    public void setLegalLastNameEnglish(String legalLastNameEnglish) {
        this.legalLastNameEnglish = legalLastNameEnglish;
    }

    public String getNameInOtherLanguage() {
        return nameInOtherLanguage;
    }

    public void setNameInOtherLanguage(String nameInOtherLanguage) {
        this.nameInOtherLanguage = nameInOtherLanguage;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getIsApplicant() {
        return isApplicant;
    }

    public void setIsApplicant(String isApplicant) {
        this.isApplicant = isApplicant;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Double getOwnershipPercentage() {
        return ownershipPercentage;
    }

    public void setOwnershipPercentage(Double ownershipPercentage) {
        this.ownershipPercentage = ownershipPercentage;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountryOrTerritory() {
        return countryOrTerritory;
    }

    public void setCountryOrTerritory(String countryOrTerritory) {
        this.countryOrTerritory = countryOrTerritory;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getApartmentSuiteOrFloor() {
        return apartmentSuiteOrFloor;
    }

    public void setApartmentSuiteOrFloor(String apartmentSuiteOrFloor) {
        this.apartmentSuiteOrFloor = apartmentSuiteOrFloor;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationValue() {
        return identificationValue;
    }

    public void setIdentificationValue(String identificationValue) {
        this.identificationValue = identificationValue;
    }

    public List<String> getIdentityDocs() {
        return identityDocs;
    }

    public void setIdentityDocs(List<String> identityDocs) {
        this.identityDocs = identityDocs;
    }

    public List<OtherProof> getOtherDocuments() {
        return otherDocuments;
    }

    public void setOtherDocuments(List<OtherProof> otherDocuments) {
        this.otherDocuments = otherDocuments;
    }

    public List<String> getFaceDocs() {
        return faceDocs;
    }

    public void setFaceDocs(List<String> faceDocs) {
        this.faceDocs = faceDocs;
    }
}
