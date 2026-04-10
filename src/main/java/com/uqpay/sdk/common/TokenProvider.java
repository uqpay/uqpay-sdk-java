package com.uqpay.sdk.common;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for providing authentication tokens.
 * <p>
 * Implementations should handle token caching and automatic refresh.
 * The default implementation is {@link com.uqpay.sdk.auth.DefaultTokenProvider}.
 * </p>
 */
public interface TokenProvider {

    /**
     * Returns a valid authentication token.
     * <p>
     * Implementations should cache the token and refresh it automatically when needed.
     * This method must be thread-safe.
     * </p>
     *
     * @return a valid authentication token
     * @throws UqpayAuthException if token retrieval fails
     */
    @NotNull
    String getToken() throws UqpayAuthException;
}
