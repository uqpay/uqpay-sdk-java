package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents an account creation request for the legacy Create Account endpoint.
 * <p>
 * When {@code entityType} is INDIVIDUAL, the {@code personDetails} and
 * {@code residentialAddress} fields should be set.
 * </p>
 * <p>
 * When {@code entityType} is COMPANY, the {@code country}, {@code businessDetails},
 * {@code registrationAddress}, {@code businessAddress}, and {@code representatives}
 * fields should be set.
 * </p>
 * <p>
 * Note: This endpoint is deprecated. Use {@link CreateSubAccountRequest} for
 * new integrations.
 * </p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateAccountRequest {

    @JsonProperty("entity_type")
    private EntityType entityType; // Required. COMPANY or INDIVIDUAL

    @JsonProperty("name")
    private String name; // Required. Display name of the account

    @JsonProperty("country")
    private String country; // Required for COMPANY. ISO 3166-1 alpha-2 country code, e.g. "US"

    @JsonProperty("contact_details")
    private ContactDetails contactDetails; // Required. Contact details (email, phone)

    @JsonProperty("business_details")
    private BusinessDetails businessDetails; // Required for COMPANY. Business information

    @JsonProperty("registration_address")
    private Address registrationAddress; // Required for COMPANY. Company registration address

    @JsonProperty("business_address")
    private List<Address> businessAddress; // Required for COMPANY. Business operating addresses

    @JsonProperty("representatives")
    private List<AccountRepresentative> representatives; // Required for COMPANY. Directors, shareholders, etc.

    @JsonProperty("person_details")
    private PersonDetails personDetails; // Required for INDIVIDUAL. Personal information

    @JsonProperty("residential_address")
    private Address residentialAddress; // Required for INDIVIDUAL. Residential address

    @JsonProperty("documents")
    private List<DocumentItem> documents; // Optional. Supporting documents for verification

    @JsonProperty("tos_acceptance")
    private TosAcceptance tosAcceptance; // Required. Terms of service acceptance (ip, date, user_agent)

    public CreateAccountRequest() {
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public List<DocumentItem> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentItem> documents) {
        this.documents = documents;
    }

    public TosAcceptance getTosAcceptance() {
        return tosAcceptance;
    }

    public void setTosAcceptance(TosAcceptance tosAcceptance) {
        this.tosAcceptance = tosAcceptance;
    }
}
