package com.uqpay.sdk.connect;

import com.uqpay.sdk.common.ApiClient;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Client for the Connect API module.
 * <p>
 * Provides access to Connect sub-services such as {@link AccountsService}.
 * </p>
 */
public final class ConnectClient {

    private final AccountsService accounts;
    private final RfisService rfis;

    /**
     * Creates a new ConnectClient.
     *
     * @param apiClient the API client for making HTTP requests
     */
    public ConnectClient(@NotNull ApiClient apiClient) {
        Objects.requireNonNull(apiClient, "apiClient must not be null");
        this.accounts = new AccountsService(apiClient);
        this.rfis = new RfisService(apiClient);
    }

    /**
     * Returns the accounts service for managing Connect accounts.
     *
     * @return the accounts service
     */
    @NotNull
    public AccountsService getAccounts() {
        return accounts;
    }

    @NotNull
    public RfisService getRfis() {
        return rfis;
    }
}
