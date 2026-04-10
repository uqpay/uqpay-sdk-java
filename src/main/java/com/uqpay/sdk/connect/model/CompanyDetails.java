package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents company account details.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDetails {

    @JsonProperty("legal_name")
    private String legalName;

    @JsonProperty("tax_id")
    private String taxId;

    @JsonProperty("business_type")
    private String businessType;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("contact_info")
    private ContactDetails contactInfo;

    @JsonProperty("tos_acceptance")
    private TosAcceptance tosAcceptance;

    @JsonProperty("representatives")
    private List<Representative> representatives;

    public CompanyDetails() {
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
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

    public List<Representative> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(List<Representative> representatives) {
        this.representatives = representatives;
    }
}
