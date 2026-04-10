package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cardholder {

    @JsonProperty("cardholder_id")
    private String cardholderId; // UUID

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName; // 1-40 chars, alphabetic + spaces only

    @JsonProperty("last_name")
    private String lastName; // 1-40 chars, alphabetic + spaces only

    @JsonProperty("country_code")
    private String countryCode; // ISO 3166-1 alpha-2, e.g. "SG"

    @JsonProperty("cardholder_status")
    private String cardholderStatus; // FAILED | PENDING | SUCCESS | INCOMPLETE

    @JsonProperty("number_of_cards")
    private Integer numberOfCards; // total cards associated, including all statuses

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("date_of_birth")
    private String dateOfBirth; // format: yyyy-mm-dd

    @JsonProperty("delivery_address")
    private DeliveryAddress deliveryAddress;

    @JsonProperty("review_status")
    private String reviewStatus; // reserved for future use

    @JsonProperty("gender")
    private String gender; // MALE or FEMALE

    @JsonProperty("nationality")
    private String nationality; // ISO 3166-1 alpha-2

    @JsonProperty("residential_address")
    private ResidentialAddress residentialAddress;

    @JsonProperty("idv_status")
    private String idvStatus; // PENDING | PASSED | FAILED

    @JsonProperty("idv_verification_url")
    private String idvVerificationUrl;

    @JsonProperty("idv_url_expires_at")
    private String idvUrlExpiresAt;

    @JsonProperty("create_time")
    private String createTime; // format: yyyy-mm-dd hh:mm:ss

    public Cardholder() {
    }

    public String getCardholderId() {
        return cardholderId;
    }

    public void setCardholderId(String cardholderId) {
        this.cardholderId = cardholderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCardholderStatus() {
        return cardholderStatus;
    }

    public void setCardholderStatus(String cardholderStatus) {
        this.cardholderStatus = cardholderStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Integer getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(Integer numberOfCards) {
        this.numberOfCards = numberOfCards;
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

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
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

    public String getIdvStatus() {
        return idvStatus;
    }

    public void setIdvStatus(String idvStatus) {
        this.idvStatus = idvStatus;
    }

    public String getIdvVerificationUrl() {
        return idvVerificationUrl;
    }

    public void setIdvVerificationUrl(String idvVerificationUrl) {
        this.idvVerificationUrl = idvVerificationUrl;
    }

    public String getIdvUrlExpiresAt() {
        return idvUrlExpiresAt;
    }

    public void setIdvUrlExpiresAt(String idvUrlExpiresAt) {
        this.idvUrlExpiresAt = idvUrlExpiresAt;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
