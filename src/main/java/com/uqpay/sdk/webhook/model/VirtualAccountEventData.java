package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uqpay.sdk.banking.model.VirtualAccountApplication;

/**
 * Complete Virtual Account application state delivered by
 * {@code virtual.account.create}, {@code virtual.account.update}, and
 * {@code virtual.account.closed}. The event {@code source_id} is the same value
 * as {@link #getApplicationId()}. Typed parsing requires {@code account_id} and
 * {@code direct_id}; use the generic {@link com.uqpay.sdk.webhook.Event#getData()}
 * representation only when retaining a historical raw payload that predates
 * those correlation fields.
 */
public class VirtualAccountEventData extends VirtualAccountApplication {
    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("direct_id")
    private String directId;

    /**
     * Required on current events. Account whose Virtual Account application
     * changed. For connected-account events, this identifies the effective
     * connected account.
     */
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * Required on current events. Direct (main) account scope associated with
     * the event.
     */
    public String getDirectId() {
        return directId;
    }

    public void setDirectId(String directId) {
        this.directId = directId;
    }
}
