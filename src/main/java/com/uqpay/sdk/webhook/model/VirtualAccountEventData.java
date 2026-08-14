package com.uqpay.sdk.webhook.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uqpay.sdk.banking.model.VirtualAccountApplication;

/**
 * Complete Virtual Account application state delivered by
 * {@code virtual.account.create}, {@code virtual.account.update}, and
 * {@code virtual.account.closed}. The event {@code source_id} is the same value
 * as {@link #getApplicationId()}. Current events require {@code account_id} and
 * {@code direct_id}. The parser tolerates their absence only so historical or
 * retried payloads emitted before those correlation fields were restored remain
 * consumable; their getters return {@code null} for such legacy payloads.
 */
public class VirtualAccountEventData extends VirtualAccountApplication {
    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("direct_id")
    private String directId;

    /**
     * Required on current events. Account whose Virtual Account application
     * changed. For connected-account events, this identifies the effective
     * connected account. May be {@code null} only for a legacy/retried payload.
     */
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * Required on current events. Direct (main) account scope associated with
     * the event. May be {@code null} only for a legacy/retried payload.
     */
    public String getDirectId() {
        return directId;
    }

    public void setDirectId(String directId) {
        this.directId = directId;
    }
}
