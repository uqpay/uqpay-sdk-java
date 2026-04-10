package com.uqpay.sdk.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Environment}.
 */
@DisplayName("Environment")
class EnvironmentTest {

    @Test
    @DisplayName("SANDBOX should have correct base URL")
    void sandboxShouldHaveCorrectBaseUrl() {
        assertThat(Environment.SANDBOX.getBaseUrl())
                .isEqualTo("https://api-sandbox.uqpaytech.com/api");
    }

    @Test
    @DisplayName("SANDBOX should have correct files base URL")
    void sandboxShouldHaveCorrectFilesBaseUrl() {
        assertThat(Environment.SANDBOX.getFilesBaseUrl())
                .isEqualTo("https://files.uqpaytech.com/api");
    }

    @Test
    @DisplayName("PRODUCTION should have correct base URL")
    void productionShouldHaveCorrectBaseUrl() {
        assertThat(Environment.PRODUCTION.getBaseUrl())
                .isEqualTo("https://api.uqpay.com/api");
    }

    @Test
    @DisplayName("PRODUCTION should have correct files base URL")
    void productionShouldHaveCorrectFilesBaseUrl() {
        assertThat(Environment.PRODUCTION.getFilesBaseUrl())
                .isEqualTo("https://files.uqpay.com/api");
    }

    @Test
    @DisplayName("Should have exactly two environments")
    void shouldHaveExactlyTwoEnvironments() {
        assertThat(Environment.values()).hasSize(2);
    }
}
