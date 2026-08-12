package com.uqpay.sdk.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VirtualAccountApplicationSummary {
    @JsonProperty("application_id") private String applicationId;
    @JsonProperty("public_version") private long publicVersion;
    @JsonProperty("country") private String country;
    @JsonProperty("currency") private String currency;
    @JsonProperty("status") private VirtualAccountApplicationStatus status;
    @JsonProperty("created_at") private OffsetDateTime createdAt;

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }
    public long getPublicVersion() { return publicVersion; }
    public void setPublicVersion(long publicVersion) { this.publicVersion = publicVersion; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public VirtualAccountApplicationStatus getStatus() { return status; }
    public void setStatus(VirtualAccountApplicationStatus status) { this.status = status; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
