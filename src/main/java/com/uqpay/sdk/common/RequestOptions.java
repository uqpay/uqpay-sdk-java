package com.uqpay.sdk.common;

import org.jetbrains.annotations.Nullable;

/**
 * Optional parameters for API requests.
 * <p>
 * Use this class to customize individual API requests with custom idempotency keys
 * or authentication tokens.
 * </p>
 *
 * <pre>{@code
 * RequestOptions options = RequestOptions.builder()
 *     .idempotencyKey("unique-request-id-123")
 *     .build();
 *
 * client.issuing().cards().create(request, options);
 * }</pre>
 */
public final class RequestOptions {

    private final String idempotencyKey; // Optional; UUID format; auto-generated if not set; cached by API for 24 hours
    private final String authToken; // Optional; overrides TokenProvider token for this request
    private final String onBehalfOf; // Optional; UQPAY sub-account ID for connected account delegation

    private RequestOptions(Builder builder) {
        this.idempotencyKey = builder.idempotencyKey;
        this.authToken = builder.authToken;
        this.onBehalfOf = builder.onBehalfOf;
    }

    /**
     * Creates a new builder for {@link RequestOptions}.
     *
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates request options with the specified idempotency key.
     *
     * @param idempotencyKey the idempotency key
     * @return a new {@link RequestOptions} instance
     */
    public static RequestOptions withIdempotencyKey(String idempotencyKey) {
        return builder().idempotencyKey(idempotencyKey).build();
    }

    /**
     * Returns the idempotency key.
     * <p>
     * If not specified, a UUID will be generated automatically for each request.
     * </p>
     *
     * @return the idempotency key, or null if not specified
     */
    @Nullable
    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    /**
     * Returns the custom authentication token.
     * <p>
     * If not specified, the token from the TokenProvider will be used.
     * </p>
     *
     * @return the authentication token, or null if not specified
     */
    @Nullable
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Returns the sub-account ID for delegation.
     * <p>
     * When set, the {@code x-on-behalf-of} header will be sent with the request,
     * allowing API calls to be made on behalf of a sub-account.
     * </p>
     *
     * @return the sub-account ID, or null if not specified
     */
    @Nullable
    public String getOnBehalfOf() {
        return onBehalfOf;
    }

    /**
     * Builder for {@link RequestOptions}.
     */
    public static final class Builder {

        private String idempotencyKey;
        private String authToken;
        private String onBehalfOf;

        private Builder() {
        }

        /**
         * Sets the idempotency key for the request.
         * <p>
         * Idempotency keys are used to ensure that requests are only processed once,
         * even if they are sent multiple times. This is useful for retry logic.
         * </p>
         *
         * @param idempotencyKey the idempotency key
         * @return this builder
         */
        public Builder idempotencyKey(@Nullable String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        /**
         * Sets a custom authentication token for the request.
         * <p>
         * This overrides the token from the TokenProvider for this specific request.
         * </p>
         *
         * @param authToken the authentication token
         * @return this builder
         */
        public Builder authToken(@Nullable String authToken) {
            this.authToken = authToken;
            return this;
        }

        /**
         * Sets the sub-account ID for delegation.
         * <p>
         * When set, the {@code x-on-behalf-of} header will be sent with the request.
         * </p>
         *
         * @param onBehalfOf the sub-account ID
         * @return this builder
         */
        public Builder onBehalfOf(@Nullable String onBehalfOf) {
            this.onBehalfOf = onBehalfOf;
            return this;
        }

        /**
         * Builds the request options.
         *
         * @return a new {@link RequestOptions} instance
         */
        public RequestOptions build() {
            return new RequestOptions(this);
        }
    }
}
