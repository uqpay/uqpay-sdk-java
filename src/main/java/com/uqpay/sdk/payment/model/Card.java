package com.uqpay.sdk.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Card {

    @JsonProperty("card_name")
    private String cardName; // Required. Cardholder name as printed on the card

    @JsonProperty("card_number")
    private String cardNumber; // Required. Card number, 13-19 digits

    @JsonProperty("expiry_month")
    private String expiryMonth; // Required. Expiry month in MM format (01-12)

    @JsonProperty("expiry_year")
    private String expiryYear; // Required. Expiry year in YYYY format, must be >= current year

    @JsonProperty("cvc")
    private String cvc; // Required. 3-4 digit card security code

    @JsonProperty("network")
    private String network; // Required. Card network, e.g. Visa, MasterCard, American Express, Discover

    @JsonProperty("billing")
    private Billing billing; // Required. Billing address associated with the card

    @JsonProperty("auto_capture")
    private Boolean autoCapture; // Optional. Auto-capture payment after authorization

    @JsonProperty("authorization_type")
    private String authorizationType; // Optional. Authorization type: manual or automatic

    @JsonProperty("three_ds_action")
    private String threeDsAction; // Optional. 3DS action: enforce_3ds, optional_3ds, or skip_3ds

    @JsonProperty("three_ds")
    private Map<String, Object> threeDs; // Optional. 3D Secure authentication data

    public Card() {
    }

    public String getCardName() { return cardName; }
    public void setCardName(String cardName) { this.cardName = cardName; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getExpiryMonth() { return expiryMonth; }
    public void setExpiryMonth(String expiryMonth) { this.expiryMonth = expiryMonth; }

    public String getExpiryYear() { return expiryYear; }
    public void setExpiryYear(String expiryYear) { this.expiryYear = expiryYear; }

    public String getCvc() { return cvc; }
    public void setCvc(String cvc) { this.cvc = cvc; }

    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }

    public Billing getBilling() { return billing; }
    public void setBilling(Billing billing) { this.billing = billing; }

    public Boolean getAutoCapture() { return autoCapture; }
    public void setAutoCapture(Boolean autoCapture) { this.autoCapture = autoCapture; }

    public String getAuthorizationType() { return authorizationType; }
    public void setAuthorizationType(String authorizationType) { this.authorizationType = authorizationType; }

    public String getThreeDsAction() { return threeDsAction; }
    public void setThreeDsAction(String threeDsAction) { this.threeDsAction = threeDsAction; }

    public Map<String, Object> getThreeDs() { return threeDs; }
    public void setThreeDs(Map<String, Object> threeDs) { this.threeDs = threeDs; }
}
