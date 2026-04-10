package com.uqpay.sdk.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link UqpayApiException}.
 */
@DisplayName("UqpayApiException")
class UqpayApiExceptionTest {

    @Test
    @DisplayName("should create exception with all fields")
    void shouldCreateExceptionWithAllFields() {
        UqpayApiException exception = new UqpayApiException("ERR001", "Something went wrong", 400);

        assertThat(exception.getCode()).isEqualTo("ERR001");
        assertThat(exception.getErrorMessage()).isEqualTo("Something went wrong");
        assertThat(exception.getStatusCode()).isEqualTo(400);
        assertThat(exception.getMessage()).contains("ERR001");
        assertThat(exception.getMessage()).contains("Something went wrong");
        assertThat(exception.getMessage()).contains("400");
    }

    @Test
    @DisplayName("should detect not found errors")
    void shouldDetectNotFoundErrors() {
        UqpayApiException exception = new UqpayApiException("NOT_FOUND", "Resource not found", 404);

        assertThat(exception.isNotFound()).isTrue();
        assertThat(exception.isBadRequest()).isFalse();
        assertThat(exception.isUnauthorized()).isFalse();
        assertThat(exception.isServerError()).isFalse();
        assertThat(exception.isClientError()).isTrue();
    }

    @Test
    @DisplayName("should detect unauthorized errors")
    void shouldDetectUnauthorizedErrors() {
        UqpayApiException exception = new UqpayApiException("UNAUTHORIZED", "Invalid token", 401);

        assertThat(exception.isUnauthorized()).isTrue();
        assertThat(exception.isNotFound()).isFalse();
        assertThat(exception.isBadRequest()).isFalse();
        assertThat(exception.isServerError()).isFalse();
        assertThat(exception.isClientError()).isTrue();
    }

    @Test
    @DisplayName("should detect bad request errors")
    void shouldDetectBadRequestErrors() {
        UqpayApiException exception = new UqpayApiException("INVALID_PARAM", "Invalid parameter", 400);

        assertThat(exception.isBadRequest()).isTrue();
        assertThat(exception.isNotFound()).isFalse();
        assertThat(exception.isUnauthorized()).isFalse();
        assertThat(exception.isServerError()).isFalse();
        assertThat(exception.isClientError()).isTrue();
    }

    @Test
    @DisplayName("should detect forbidden errors")
    void shouldDetectForbiddenErrors() {
        UqpayApiException exception = new UqpayApiException("FORBIDDEN", "Access denied", 403);

        assertThat(exception.isForbidden()).isTrue();
        assertThat(exception.isClientError()).isTrue();
        assertThat(exception.isServerError()).isFalse();
    }

    @Test
    @DisplayName("should detect server errors")
    void shouldDetectServerErrors() {
        UqpayApiException exception500 = new UqpayApiException("SERVER_ERROR", "Internal error", 500);
        UqpayApiException exception503 = new UqpayApiException("UNAVAILABLE", "Service unavailable", 503);

        assertThat(exception500.isServerError()).isTrue();
        assertThat(exception503.isServerError()).isTrue();
        assertThat(exception500.isClientError()).isFalse();
        assertThat(exception503.isClientError()).isFalse();
    }

    @Test
    @DisplayName("should be deserializable from JSON")
    void shouldBeDeserializableFromJson() throws Exception {
        String json = "{\"code\":\"ERR123\",\"message\":\"Test error message\"}";

        ObjectMapper mapper = new ObjectMapper();
        UqpayApiException exception = mapper.readValue(json, UqpayApiException.class);
        exception.setStatusCode(422);

        assertThat(exception.getCode()).isEqualTo("ERR123");
        assertThat(exception.getErrorMessage()).isEqualTo("Test error message");
        assertThat(exception.getStatusCode()).isEqualTo(422);
    }

    @Test
    @DisplayName("should handle null code gracefully")
    void shouldHandleNullCodeGracefully() {
        UqpayApiException exception = new UqpayApiException(null, "Error message", 500);

        assertThat(exception.getCode()).isNull();
        assertThat(exception.getMessage()).contains("Error message");
        assertThat(exception.getMessage()).contains("500");
    }

    @Test
    @DisplayName("should handle null message gracefully")
    void shouldHandleNullMessageGracefully() {
        UqpayApiException exception = new UqpayApiException("ERR001", null, 500);

        assertThat(exception.getErrorMessage()).isNull();
        assertThat(exception.getMessage()).contains("ERR001");
        assertThat(exception.getMessage()).contains("500");
    }

    @Test
    @DisplayName("should handle both null code and message")
    void shouldHandleBothNullCodeAndMessage() {
        UqpayApiException exception = new UqpayApiException(null, null, 500);

        assertThat(exception.getMessage()).contains("500");
    }
}
