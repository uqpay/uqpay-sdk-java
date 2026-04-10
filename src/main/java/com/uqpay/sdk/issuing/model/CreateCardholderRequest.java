package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCardholderRequest {

    @JsonProperty("email")
    private String email; // required

    @JsonProperty("phone_number")
    private String phoneNumber; // required; country-specific validation

    @JsonProperty("first_name")
    private String firstName; // required; 1-40 chars, alphabetic + spaces only

    @JsonProperty("last_name")
    private String lastName; // required; 1-40 chars, alphabetic + spaces only

    @JsonProperty("country_code")
    private String countryCode; // required; ISO 3166-1 alpha-2, e.g. "SG"

    @JsonProperty("date_of_birth")
    private String dateOfBirth; // optional; format: yyyy-mm-dd

    @JsonProperty("delivery_address")
    private DeliveryAddress deliveryAddress; // optional; all sub-fields required if provided

    @JsonProperty("document_type")
    private String documentType; // optional; pdf | png | jpg | jpeg

    @JsonProperty("document")
    private String document; // optional; base64 encoded, max 2MB

    @JsonProperty("gender")
    private String gender; // optional; MALE or FEMALE

    @JsonProperty("nationality")
    private String nationality; // optional; ISO 3166-1 alpha-2

    @JsonProperty("residential_address")
    private ResidentialAddress residentialAddress; // optional

    @JsonProperty("identity")
    private Identity identity; // optional

    @JsonProperty("kyc_verification")
    private KycVerification kycVerification; // optional

    public CreateCardholderRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public ResidentialAddress getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(ResidentialAddress residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public KycVerification getKycVerification() {
        return kycVerification;
    }

    public void setKycVerification(KycVerification kycVerification) {
        this.kycVerification = kycVerification;
    }
}
