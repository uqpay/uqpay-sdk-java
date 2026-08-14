package com.uqpay.sdk.webhook.model;

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
}
