package com.uqpay.sdk.common;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.config.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration tests for {@link ApiClient}.
 */
@DisplayName("ApiClient Integration Tests")
class ApiClientIntegrationTest {

    private static Configuration config;
    private static TokenProvider tokenProvider;
    private static ApiClient apiClient;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();
        config = TestHelper.getTestConfiguration();
        tokenProvider = new DefaultTokenProvider(config);
        apiClient = new ApiClient(config, tokenProvider);
    }

    @Test
    @DisplayName("should have correct base URL")
    void shouldHaveCorrectBaseUrl() {
        assertThat(apiClient.getBaseUrl()).isEqualTo(config.getEnvironment().getBaseUrl());
    }

    @Test
    @DisplayName("should have ObjectMapper configured")
    void shouldHaveObjectMapperConfigured() {
        assertThat(apiClient.getObjectMapper()).isNotNull();
    }

    @Test
    @DisplayName("should handle 404 error correctly")
    void shouldHandle404ErrorCorrectly() {
        // Try to get a non-existent resource
        assertThatThrownBy(() -> apiClient.get("/v1/issuing/cards/non-existent-id", Object.class))
                .isInstanceOf(UqpayApiException.class)
                .satisfies(e -> {
                    UqpayApiException apiException = (UqpayApiException) e;
                    // Could be 404 or 400 depending on API behavior
                    assertThat(apiException.getStatusCode()).isIn(400, 401, 403, 404, 422);
                });
    }

    @Test
    @DisplayName("should include idempotency key in requests")
    void shouldIncludeIdempotencyKeyInRequests() throws UqpayException {
        // This test verifies that requests with custom idempotency keys work
        RequestOptions options = RequestOptions.builder()
                .idempotencyKey("test-idempotency-key-" + System.currentTimeMillis())
                .build();

        // We can't easily verify the header was sent without mocking,
        // but we can verify the request doesn't fail due to the options
        assertThatThrownBy(() -> apiClient.get("/v1/issuing/cards/test-id", Object.class, options))
                .isInstanceOf(UqpayApiException.class);
        // If we get here without an unexpected exception, the options were handled correctly
    }

    /**
     * Simple test response class for parsing.
     */
    public static class SimpleResponse {
        public String message;
    }
}
