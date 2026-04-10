package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplePay {

    @JsonProperty("flow")
    private String flow;

    @JsonProperty("os_type")
    private String osType;

    @JsonProperty("is_present")
    private Boolean isPresent;

    @JsonProperty("network")
    private String network;

    @JsonProperty("card_type")
    private String cardType;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("auth_method")
    private String authMethod;

    @JsonProperty("network_token")
    private NetworkToken networkToken;

    @JsonProperty("billing_contact")
    private BillingContact billingContact;

    public ApplePay() {
    }

    public String getFlow() { return flow; }
    public void setFlow(String flow) { this.flow = flow; }

    public String getOsType() { return osType; }
    public void setOsType(String osType) { this.osType = osType; }

    public Boolean getIsPresent() { return isPresent; }
    public void setIsPresent(Boolean isPresent) { this.isPresent = isPresent; }

    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public String getAuthMethod() { return authMethod; }
    public void setAuthMethod(String authMethod) { this.authMethod = authMethod; }

    public NetworkToken getNetworkToken() { return networkToken; }
    public void setNetworkToken(NetworkToken networkToken) { this.networkToken = networkToken; }

    public BillingContact getBillingContact() { return billingContact; }
    public void setBillingContact(BillingContact billingContact) { this.billingContact = billingContact; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NetworkToken {

        @JsonProperty("number")
        private String number;

        @JsonProperty("expiry_month")
        private String expiryMonth;

        @JsonProperty("expiry_year")
        private String expiryYear;

        @JsonProperty("cryptogram")
        private String cryptogram;

        @JsonProperty("eci")
        private String eci;

        public NetworkToken() {
        }

        public String getNumber() { return number; }
        public void setNumber(String number) { this.number = number; }

        public String getExpiryMonth() { return expiryMonth; }
        public void setExpiryMonth(String expiryMonth) { this.expiryMonth = expiryMonth; }

        public String getExpiryYear() { return expiryYear; }
        public void setExpiryYear(String expiryYear) { this.expiryYear = expiryYear; }

        public String getCryptogram() { return cryptogram; }
        public void setCryptogram(String cryptogram) { this.cryptogram = cryptogram; }

        public String getEci() { return eci; }
        public void setEci(String eci) { this.eci = eci; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BillingContact {

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        @JsonProperty("email")
        private String email;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("address")
        private BillingContactAddress address;

        public BillingContact() {
        }

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public BillingContactAddress getAddress() { return address; }
        public void setAddress(BillingContactAddress address) { this.address = address; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BillingContactAddress {

        @JsonProperty("street")
        private String street;

        @JsonProperty("city")
        private String city;

        @JsonProperty("state")
        private String state;

        @JsonProperty("postal_code")
        private String postalCode;

        @JsonProperty("country_code")
        private String countryCode;

        public BillingContactAddress() {
        }

        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getPostalCode() { return postalCode; }
        public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

        public String getCountryCode() { return countryCode; }
        public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    }
}
