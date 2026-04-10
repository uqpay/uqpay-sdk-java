package com.uqpay.sdk;

import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.config.Environment;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assumptions;

/**
 * Helper class for integration tests.
 * <p>
 * Provides utilities for loading test credentials from environment variables
 * and creating test configurations.
 * </p>
 */
public final class TestHelper {

    private static final String ENV_CLIENT_ID = "UQPAY_CLIENT_ID";
    private static final String ENV_API_KEY = "UQPAY_API_KEY";
    private static final String ENV_SKIP_INTEGRATION = "SKIP_INTEGRATION_TESTS";

    private static Dotenv dotenv;

    static {
        try {
            // Try to load .env file from project root
            dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();
        } catch (Exception e) {
            // Ignore if .env file doesn't exist
            dotenv = null;
        }
    }

    private TestHelper() {
        // Prevent instantiation
    }

    /**
     * Gets an environment variable, first checking .env file, then system environment.
     *
     * @param name the variable name
     * @return the variable value, or null if not found
     */
    public static String getEnv(String name) {
        if (dotenv != null) {
            String value = dotenv.get(name);
            if (value != null && !value.isEmpty()) {
                return value;
            }
        }
        return System.getenv(name);
    }

    /**
     * Returns the client ID from environment variables.
     *
     * @return the client ID, or null if not set
     */
    public static String getClientId() {
        return getEnv(ENV_CLIENT_ID);
    }

    /**
     * Returns the API key from environment variables.
     *
     * @return the API key, or null if not set
     */
    public static String getApiKey() {
        return getEnv(ENV_API_KEY);
    }

    /**
     * Checks if integration tests should be skipped.
     *
     * @return true if integration tests should be skipped
     */
    public static boolean shouldSkipIntegrationTests() {
        String skipEnv = getEnv(ENV_SKIP_INTEGRATION);
        return "true".equalsIgnoreCase(skipEnv);
    }

    /**
     * Skips the current test if integration tests are disabled or credentials are missing.
     * <p>
     * Call this at the beginning of integration tests to ensure they are only run
     * when credentials are available.
     * </p>
     */
    public static void assumeIntegrationTestsEnabled() {
        Assumptions.assumeFalse(
                shouldSkipIntegrationTests(),
                "Skipping integration test: SKIP_INTEGRATION_TESTS is set to true"
        );

        String clientId = getClientId();
        String apiKey = getApiKey();

        Assumptions.assumeTrue(
                clientId != null && !clientId.isEmpty(),
                "Skipping integration test: UQPAY_CLIENT_ID environment variable not set"
        );

        Assumptions.assumeTrue(
                apiKey != null && !apiKey.isEmpty(),
                "Skipping integration test: UQPAY_API_KEY environment variable not set"
        );
    }

    /**
     * Creates a test configuration using credentials from environment variables.
     * <p>
     * Call {@link #assumeIntegrationTestsEnabled()} before using this method to ensure
     * credentials are available.
     * </p>
     *
     * @return a Configuration for the sandbox environment
     * @throws IllegalStateException if credentials are not available
     */
    public static Configuration getTestConfiguration() {
        String clientId = getClientId();
        String apiKey = getApiKey();

        if (clientId == null || clientId.isEmpty() || apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException(
                    "Test credentials not available. Set UQPAY_CLIENT_ID and UQPAY_API_KEY environment variables."
            );
        }

        return Configuration.builder()
                .clientId(clientId)
                .apiKey(apiKey)
                .environment(Environment.SANDBOX)
                .build();
    }

    /**
     * Creates a test configuration for the files API using credentials from environment variables.
     * <p>
     * The files API uses a separate base URL. This configuration sets the
     * {@code baseUrlOverride} to the sandbox files base URL.
     * </p>
     *
     * @return a Configuration for the sandbox files API
     * @throws IllegalStateException if credentials are not available
     */
    public static Configuration getFilesTestConfiguration() {
        String clientId = getClientId();
        String apiKey = getApiKey();

        if (clientId == null || clientId.isEmpty() || apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException(
                    "Test credentials not available. Set UQPAY_CLIENT_ID and UQPAY_API_KEY environment variables."
            );
        }

        return Configuration.builder()
                .clientId(clientId)
                .apiKey(apiKey)
                .environment(Environment.SANDBOX)
                .baseUrlOverride(Environment.SANDBOX.getFilesBaseUrl())
                .build();
    }

    /**
     * Returns a safe preview of a string, showing only the first few characters.
     *
     * @param value     the string to preview
     * @param maxLength the maximum number of characters to show
     * @return a truncated preview with "..." if the string is longer than maxLength
     */
    public static String safePreview(String value, int maxLength) {
        if (value == null) {
            return "null";
        }
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength) + "...";
    }
}
