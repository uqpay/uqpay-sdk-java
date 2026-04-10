package com.uqpay.sdk.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RequestOptions}.
 */
@DisplayName("RequestOptions")
class RequestOptionsTest {

    @Test
    @DisplayName("should create options with idempotency key")
    void shouldCreateOptionsWithIdempotencyKey() {
        RequestOptions options = RequestOptions.builder()
                .idempotencyKey("unique-key-123")
                .build();

        assertThat(options.getIdempotencyKey()).isEqualTo("unique-key-123");
        assertThat(options.getAuthToken()).isNull();
    }

    @Test
    @DisplayName("should create options with auth token")
    void shouldCreateOptionsWithAuthToken() {
        RequestOptions options = RequestOptions.builder()
                .authToken("custom-token")
                .build();

        assertThat(options.getAuthToken()).isEqualTo("custom-token");
        assertThat(options.getIdempotencyKey()).isNull();
    }

    @Test
    @DisplayName("should create options with both fields")
    void shouldCreateOptionsWithBothFields() {
        RequestOptions options = RequestOptions.builder()
                .idempotencyKey("unique-key-456")
                .authToken("custom-token")
                .build();

        assertThat(options.getIdempotencyKey()).isEqualTo("unique-key-456");
        assertThat(options.getAuthToken()).isEqualTo("custom-token");
    }

    @Test
    @DisplayName("should create empty options")
    void shouldCreateEmptyOptions() {
        RequestOptions options = RequestOptions.builder().build();

        assertThat(options.getIdempotencyKey()).isNull();
        assertThat(options.getAuthToken()).isNull();
    }

    @Test
    @DisplayName("withIdempotencyKey factory method should work")
    void withIdempotencyKeyFactoryMethodShouldWork() {
        RequestOptions options = RequestOptions.withIdempotencyKey("my-key");

        assertThat(options.getIdempotencyKey()).isEqualTo("my-key");
        assertThat(options.getAuthToken()).isNull();
    }
}
