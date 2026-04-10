package com.uqpay.sdk.issuing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardholderInfo {

    @JsonProperty("cardholder_id")
    private String cardholderId; // UUID

    @JsonProperty("email")
    private String email;

    @JsonProperty("number_of_cards")
    private int numberOfCards; // total cards associated, including all statuses

    @JsonProperty("first_name")
    private String firstName; // no numbers or special chars except . , - ' and spaces

    @JsonProperty("last_name")
    private String lastName; // no numbers or special chars except . , - ' and spaces

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("cardholder_status")
    private String cardholderStatus; // FAILED | PENDING | SUCCESS | INCOMPLETE

    @JsonProperty("date_of_birth")
    private String dateOfBirth; // yyyy-MM-dd

    @JsonProperty("country_code")
    private String countryCode; // ISO 3166-1 alpha-2, e.g. "SG"

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("review_status")
    private String reviewStatus; // reserved for future use

    @JsonProperty("delivery_address")
    private DeliveryAddress deliveryAddress;

    public CardholderInfo() {
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

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCardholderStatus() {
        return cardholderStatus;
    }

    public void setCardholderStatus(String cardholderStatus) {
        this.cardholderStatus = cardholderStatus;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
