package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeneficiaryAdditionalInfo {

    @JsonProperty("organization_code")
    private String organizationCode; // Unified Social Credit Identifier for China companies, e.g. "91210106MA0P46BWXY"

    @JsonProperty("proxy_id")
    private String proxyId; // PayNow proxy identifier (SGD), supports UEN, phone number, or VPA

    @JsonProperty("id_type")
    private String idType; // "PASSPORT", "NATIONAL_ID", or "DRIVERS_LICENSE" (required when COP + INDIVIDUAL)

    @JsonProperty("id_number")
    private String idNumber; // identification number (required when COP + INDIVIDUAL)

    @JsonProperty("tax_id")
    private String taxId; // tax identification number (required when COP + COMPANY)

    @JsonProperty("msisdn")
    private String msisdn; // mobile phone in international format, e.g. "+65111111" (required when COP or HKD+LOCAL)

    public BeneficiaryAdditionalInfo() {
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getProxyId() {
        return proxyId;
    }

    public void setProxyId(String proxyId) {
        this.proxyId = proxyId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
}
