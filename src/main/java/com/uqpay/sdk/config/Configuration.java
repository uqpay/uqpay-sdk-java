package com.uqpay.sdk.config;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Objects;

/**
 * Holds SDK configuration including credentials, environment, and HTTP client settings.
 * <p>
 * Use the {@link Builder} to create an instance:
 * </p>
 *
 * <pre>{@code
 * Configuration config = Configuration.builder()
 *     .clientId("your-client-id")
 *     .apiKey("your-api-key")
 *     .environment(Environment.SANDBOX)
 *     .build();
 * }</pre>
 */
public final class Configuration {

    private static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(30);
    private static final Duration DEFAULT_READ_TIMEOUT = Duration.ofSeconds(30);
    private static final Duration DEFAULT_WRITE_TIMEOUT = Duration.ofSeconds(30);

    private final String clientId; // Required; maps to x-client-id header
    private final String apiKey; // Required; maps to x-api-key header
    private final Environment environment; // Defaults to SANDBOX if not set
    private final OkHttpClient httpClient;
    private final String baseUrlOverride; // Optional; overrides environment base URL (e.g. for files API)

    private Configuration(Builder builder) {
        this.clientId = Objects.requireNonNull(builder.clientId, "clientId must not be null");
        this.apiKey = Objects.requireNonNull(builder.apiKey, "apiKey must not be null");
        this.environment = builder.environment != null ? builder.environment : Environment.SANDBOX;
        this.httpClient = builder.httpClient != null ? builder.httpClient : createDefaultHttpClient();
        this.baseUrlOverride = builder.baseUrlOverride;
    }

    private static OkHttpClient createDefaultHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .readTimeout(DEFAULT_READ_TIMEOUT)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT)
                .build();
    }

    /**
     * Creates a new builder for {@link Configuration}.
     *
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a configuration with the specified credentials using the sandbox environment.
     *
     * @param clientId the client ID
     * @param apiKey   the API key
     * @return a new configuration instance
     */
    public static Configuration sandbox(@NotNull String clientId, @NotNull String apiKey) {
        return builder()
                .clientId(clientId)
                .apiKey(apiKey)
                .environment(Environment.SANDBOX)
                .build();
    }

    /**
     * Creates a configuration with the specified credentials using the production environment.
     *
     * @param clientId the client ID
     * @param apiKey   the API key
     * @return a new configuration instance
     */
    public static Configuration production(@NotNull String clientId, @NotNull String apiKey) {
        return builder()
                .clientId(clientId)
                .apiKey(apiKey)
                .environment(Environment.PRODUCTION)
                .build();
    }

    /**
     * Returns the client ID.
     *
     * @return the client ID
     */
    @NotNull
    public String getClientId() {
        return clientId;
    }

    /**
     * Returns the API key.
     *
     * @return the API key
     */
    @NotNull
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Returns the environment.
     *
     * @return the environment
     */
    @NotNull
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Returns the HTTP client used for API requests.
     *
     * @return the HTTP client
     */
    @NotNull
    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Returns the base URL override, or null if not set.
     * <p>
     * When set, this overrides the environment's base URL. Used for services
     * that operate on a different base URL (e.g., files API).
     * </p>
     *
     * @return the base URL override, or null
     */
    @Nullable
    public String getBaseUrlOverride() {
        return baseUrlOverride;
    }

    /**
     * Builder for {@link Configuration}.
     */
    public static final class Builder {

        private String clientId;
        private String apiKey;
        private Environment environment;
        private OkHttpClient httpClient;
        private String baseUrlOverride;

        private Builder() {
        }

        /**
         * Sets the client ID.
         *
         * @param clientId the client ID
         * @return this builder
         */
        public Builder clientId(@NotNull String clientId) {
            this.clientId = Objects.requireNonNull(clientId, "clientId must not be null");
            return this;
        }

        /**
         * Sets the API key.
         *
         * @param apiKey the API key
         * @return this builder
         */
        public Builder apiKey(@NotNull String apiKey) {
            this.apiKey = Objects.requireNonNull(apiKey, "apiKey must not be null");
            return this;
        }

        /**
         * Sets the environment. Defaults to {@link Environment#SANDBOX} if not specified.
         *
         * @param environment the environment
         * @return this builder
         */
        public Builder environment(@Nullable Environment environment) {
            this.environment = environment;
            return this;
        }

        /**
         * Sets a custom HTTP client. If not specified, a default client will be created
         * with 30-second timeouts for connect, read, and write operations.
         *
         * @param httpClient the HTTP client
         * @return this builder
         */
        public Builder httpClient(@Nullable OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Sets a base URL override. When set, the API client will use this URL
         * instead of the environment's default base URL.
         *
         * @param baseUrlOverride the base URL override
         * @return this builder
         */
        public Builder baseUrlOverride(@Nullable String baseUrlOverride) {
            this.baseUrlOverride = baseUrlOverride;
            return this;
        }

        /**
         * Builds the configuration.
         *
         * @return a new {@link Configuration} instance
         * @throws NullPointerException if clientId or apiKey is null
         */
        public Configuration build() {
            return new Configuration(this);
        }
    }
}
