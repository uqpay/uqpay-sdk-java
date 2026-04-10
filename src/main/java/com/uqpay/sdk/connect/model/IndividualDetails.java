package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents individual account details.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndividualDetails {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("ssn_last4")
    private String ssnLast4;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("contact_info")
    private ContactDetails contactInfo;

    @JsonProperty("tos_acceptance")
    private TosAcceptance tosAcceptance;

    public IndividualDetails() {
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSsnLast4() {
        return ssnLast4;
    }

    public void setSsnLast4(String ssnLast4) {
        this.ssnLast4 = ssnLast4;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactDetails getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactDetails contactInfo) {
        this.contactInfo = contactInfo;
    }

    public TosAcceptance getTosAcceptance() {
        return tosAcceptance;
    }

    public void setTosAcceptance(TosAcceptance tosAcceptance) {
        this.tosAcceptance = tosAcceptance;
    }
}
