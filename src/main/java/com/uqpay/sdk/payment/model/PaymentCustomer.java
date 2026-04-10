package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentCustomer {

    @JsonProperty("first_name")
    private String firstName; // Required. Customer's first name

    @JsonProperty("last_name")
    private String lastName; // Required. Customer's last name

    @JsonProperty("email")
    private String email; // Required. Customer's email address

    @JsonProperty("phone_number")
    private String phoneNumber; // Optional. Customer's phone number

    @JsonProperty("description")
    private String description; // Optional. Arbitrary customer description, max 255 chars

    @JsonProperty("address")
    private PaymentCustomerAddress address; // Optional. Customer's address details

    @JsonProperty("metadata")
    private Map<String, Object> metadata; // Optional. Custom key-value JSON object

    public PaymentCustomer() {
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public PaymentCustomerAddress getAddress() { return address; }
    public void setAddress(PaymentCustomerAddress address) { this.address = address; }

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
