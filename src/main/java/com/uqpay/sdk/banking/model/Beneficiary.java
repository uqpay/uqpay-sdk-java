package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Beneficiary {

    @JsonProperty("beneficiary_id")
    private String beneficiaryId; // UUID v4 of the beneficiary

    @JsonProperty("nickname")
    private String nickname; // max 120 chars

    @JsonProperty("entity_type")
    private String entityType; // "COMPANY" or "INDIVIDUAL"

    @JsonProperty("first_name")
    private String firstName; // only for INDIVIDUAL, max 45 chars

    @JsonProperty("last_name")
    private String lastName; // only for INDIVIDUAL, max 45 chars

    @JsonProperty("company_name")
    private String companyName; // only for COMPANY, max 120 chars

    @JsonProperty("payment_method")
    private String paymentMethod; // "LOCAL" or "SWIFT"

    @JsonProperty("bank_details")
    private BankDetails bankDetails;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("email")
    private String email; // email address of the beneficiary

    @JsonProperty("create_time")
    private String createTime; // ISO 8601 timestamp, e.g. "2024-03-01T00:00:00+08:00"

    @JsonProperty("update_time")
    private String updateTime; // ISO 8601 timestamp

    @JsonProperty("beneficiary_status")
    private String status; // "ACTIVE" or "PENDING"

    @JsonProperty("short_reference_id")
    private String shortReferenceId; // system-generated reference, e.g. "P220406-LLCVLRM"

    @JsonProperty("id_number")
    private String idNumber; // individual identification number (mandatory for China CNH + LOCAL)

    @JsonProperty("summary")
    private String summary; // summary of the beneficiary

    @JsonProperty("additional_info")
    private BeneficiaryAdditionalInfo additionalInfo;

    public Beneficiary() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShortReferenceId() {
        return shortReferenceId;
    }

    public void setShortReferenceId(String shortReferenceId) {
        this.shortReferenceId = shortReferenceId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public BeneficiaryAdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(BeneficiaryAdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
