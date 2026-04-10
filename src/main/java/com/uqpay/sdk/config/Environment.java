package com.uqpay.sdk.config;

/**
 * Represents the UQPAY API environment.
 * <p>
 * Use {@link #SANDBOX} for testing and development, and {@link #PRODUCTION} for live transactions.
 * </p>
 *
 * <pre>{@code
 * Environment env = Environment.SANDBOX;
 * String apiUrl = env.getBaseUrl();
 * }</pre>
 */
public enum Environment {

    /**
     * Sandbox environment for testing and development.
     */
    SANDBOX("https://api-sandbox.uqpaytech.com/api", "https://files.uqpaytech.com/api"), // Rate limits: auth 60/min, authenticated 500/min

    /**
     * Production environment for live transactions.
     */
    PRODUCTION("https://api.uqpay.com/api", "https://files.uqpay.com/api"); // Rate limits: auth 100/min, authenticated 300/min

    private final String baseUrl;
    private final String filesBaseUrl;

    Environment(String baseUrl, String filesBaseUrl) {
        this.baseUrl = baseUrl;
        this.filesBaseUrl = filesBaseUrl;
    }

    /**
     * Returns the base URL for API requests.
     *
     * @return the base URL for API requests
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Returns the base URL for file operations.
     *
     * @return the base URL for file operations
     */
    public String getFilesBaseUrl() {
        return filesBaseUrl;
    }
}
