package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeneficiaryCreationRequest {

    @JsonProperty("nickname")
    private String nickname; // optional, max 120 chars

    @JsonProperty("entity_type")
    private String entityType; // required, "COMPANY" or "INDIVIDUAL"

    @JsonProperty("first_name")
    private String firstName; // required for INDIVIDUAL, max 45 chars

    @JsonProperty("last_name")
    private String lastName; // required for INDIVIDUAL, max 45 chars

    @JsonProperty("company_name")
    private String companyName; // required for COMPANY, max 120 chars

    @JsonProperty("payment_method")
    private String paymentMethod; // required, "LOCAL" or "SWIFT"

    @JsonProperty("bank_details")
    private BankDetails bankDetails; // required

    @JsonProperty("address")
    private Address address; // required (not needed when bank_country_code=SG & currency=SGD)

    @JsonProperty("email")
    private String email; // optional, email address of the beneficiary

    @JsonProperty("id_number")
    private String idNumber; // mandatory when China resident with CNH + LOCAL payment_method

    @JsonProperty("additional_info")
    private BeneficiaryAdditionalInfo additionalInfo; // optional

    public BeneficiaryCreationRequest() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public BeneficiaryAdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(BeneficiaryAdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
