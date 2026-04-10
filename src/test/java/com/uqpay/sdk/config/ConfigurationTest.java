package com.uqpay.sdk.config;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link Configuration}.
 */
@DisplayName("Configuration")
class ConfigurationTest {

    @Nested
    @DisplayName("Builder")
    class BuilderTests {

        @Test
        @DisplayName("should create configuration with required fields")
        void shouldCreateConfigurationWithRequiredFields() {
            Configuration config = Configuration.builder()
                    .clientId("test-client-id")
                    .apiKey("test-api-key")
                    .build();

            assertThat(config.getClientId()).isEqualTo("test-client-id");
            assertThat(config.getApiKey()).isEqualTo("test-api-key");
        }

        @Test
        @DisplayName("should default to SANDBOX environment when not specified")
        void shouldDefaultToSandboxEnvironment() {
            Configuration config = Configuration.builder()
                    .clientId("test-client-id")
                    .apiKey("test-api-key")
                    .build();

            assertThat(config.getEnvironment()).isEqualTo(Environment.SANDBOX);
        }

        @Test
        @DisplayName("should use specified environment")
        void shouldUseSpecifiedEnvironment() {
            Configuration config = Configuration.builder()
                    .clientId("test-client-id")
                    .apiKey("test-api-key")
                    .environment(Environment.PRODUCTION)
                    .build();

            assertThat(config.getEnvironment()).isEqualTo(Environment.PRODUCTION);
        }

        @Test
        @DisplayName("should create default HTTP client when not specified")
        void shouldCreateDefaultHttpClientWhenNotSpecified() {
            Configuration config = Configuration.builder()
                    .clientId("test-client-id")
                    .apiKey("test-api-key")
                    .build();

            assertThat(config.getHttpClient()).isNotNull();
        }

        @Test
        @DisplayName("should use custom HTTP client when specified")
        void shouldUseCustomHttpClient() {
            OkHttpClient customClient = new OkHttpClient.Builder()
                    .connectTimeout(Duration.ofSeconds(60))
                    .build();

            Configuration config = Configuration.builder()
                    .clientId("test-client-id")
                    .apiKey("test-api-key")
                    .httpClient(customClient)
                    .build();

            assertThat(config.getHttpClient()).isSameAs(customClient);
        }

        @Test
        @DisplayName("should throw NullPointerException when clientId is null")
        void shouldThrowWhenClientIdIsNull() {
            assertThatThrownBy(() -> Configuration.builder()
                    .apiKey("test-api-key")
                    .build())
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("clientId");
        }

        @Test
        @DisplayName("should throw NullPointerException when apiKey is null")
        void shouldThrowWhenApiKeyIsNull() {
            assertThatThrownBy(() -> Configuration.builder()
                    .clientId("test-client-id")
                    .build())
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("apiKey");
        }
    }

    @Nested
    @DisplayName("Factory Methods")
    class FactoryMethodTests {

        @Test
        @DisplayName("sandbox() should create configuration with SANDBOX environment")
        void sandboxShouldCreateSandboxConfiguration() {
            Configuration config = Configuration.sandbox("client-id", "api-key");

            assertThat(config.getClientId()).isEqualTo("client-id");
            assertThat(config.getApiKey()).isEqualTo("api-key");
            assertThat(config.getEnvironment()).isEqualTo(Environment.SANDBOX);
        }

        @Test
        @DisplayName("production() should create configuration with PRODUCTION environment")
        void productionShouldCreateProductionConfiguration() {
            Configuration config = Configuration.production("client-id", "api-key");

            assertThat(config.getClientId()).isEqualTo("client-id");
            assertThat(config.getApiKey()).isEqualTo("api-key");
            assertThat(config.getEnvironment()).isEqualTo(Environment.PRODUCTION);
        }
    }
}
