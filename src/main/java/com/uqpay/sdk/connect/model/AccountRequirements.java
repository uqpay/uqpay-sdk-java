package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents account verification requirements.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRequirements {

    @JsonProperty("currently_due")
    private List<String> currentlyDue;

    @JsonProperty("eventually_due")
    private List<String> eventuallyDue;

    @JsonProperty("past_due")
    private List<String> pastDue;

    @JsonProperty("disabled")
    private boolean disabled;

    @JsonProperty("disabled_reason")
    private String disabledReason;

    public AccountRequirements() {
    }

    public List<String> getCurrentlyDue() {
        return currentlyDue;
    }

    public void setCurrentlyDue(List<String> currentlyDue) {
        this.currentlyDue = currentlyDue;
    }

    public List<String> getEventuallyDue() {
        return eventuallyDue;
    }

    public void setEventuallyDue(List<String> eventuallyDue) {
        this.eventuallyDue = eventuallyDue;
    }

    public List<String> getPastDue() {
        return pastDue;
    }

    public void setPastDue(List<String> pastDue) {
        this.pastDue = pastDue;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getDisabledReason() {
        return disabledReason;
    }

    public void setDisabledReason(String disabledReason) {
        this.disabledReason = disabledReason;
    }
}
