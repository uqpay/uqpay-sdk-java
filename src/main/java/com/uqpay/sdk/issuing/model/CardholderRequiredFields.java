package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardholderRequiredFields {

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("residential_address")
    private ResidentialAddress residentialAddress;

    @JsonProperty("identity")
    private Identity identity;

    @JsonProperty("kyc_verification")
    private KycVerification kycVerification;

    public CardholderRequiredFields() {}

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
