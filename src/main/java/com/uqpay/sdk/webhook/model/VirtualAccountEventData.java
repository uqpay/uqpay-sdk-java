package com.uqpay.sdk.webhook.model;

import com.uqpay.sdk.banking.model.VirtualAccountApplication;

/**
 * Complete Virtual Account application state delivered by
 * {@code virtual.account.create}, {@code virtual.account.update}, and
 * {@code virtual.account.closed}. The event {@code source_id} is the same value
 * as {@link #getApplicationId()}.
 */
public class VirtualAccountEventData extends VirtualAccountApplication {
}
