package com.uqpay.sdk.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Exception thrown when the UQPAY API returns an error response (HTTP 4xx or 5xx).
 * <p>
 * Contains the error code, message, and HTTP status code from the API response.
 * </p>
 *
 * <pre>{@code
 * try {
 *     client.issuing().cards().get("invalid-id");
 * } catch (UqpayApiException e) {
 *     if (e.isNotFound()) {
 *         System.out.println("Card not found");
 *     } else if (e.isBadRequest()) {
 *         System.out.println("Invalid request: " + e.getMessage());
 *     }
 * }
 * }</pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UqpayApiException extends UqpayException {

    private static final long serialVersionUID = 1L;

    @JsonProperty("code")
    private String code; // Error code, e.g. "invalid_number", "too_many_requests"

    @JsonProperty("message")
    private String errorMessage; // Human-readable error description; content/format may change, do not parse

    private int statusCode; // HTTP status: 200 OK, 201 Created, 400 Bad Request, 401 Unauthorized, 404 Not Found, 500 Server Error

    /**
     * Default constructor for JSON deserialization.
     */
    public UqpayApiException() {
        super("API error");
    }

    /**
     * Constructs a new API exception with the specified details.
     *
     * @param code       the error code
     * @param message    the error message
     * @param statusCode the HTTP status code
     */
    public UqpayApiException(@Nullable String code, @Nullable String message, int statusCode) {
        super(formatMessage(code, message, statusCode));
        this.code = code;
        this.errorMessage = message;
        this.statusCode = statusCode;
    }

    private static String formatMessage(String code, String message, int statusCode) {
        if (code != null && message != null) {
            return String.format("%s: %s (HTTP %d)", code, message, statusCode);
        } else if (message != null) {
            return String.format("%s (HTTP %d)", message, statusCode);
        } else if (code != null) {
            return String.format("%s (HTTP %d)", code, statusCode);
        }
        return String.format("Request failed with status %d", statusCode);
    }

    /**
     * Returns the error code from the API response.
     *
     * @return the error code, or null if not available
     */
    @Nullable
    public String getCode() {
        return code;
    }

    /**
     * Returns the error message from the API response.
     *
     * @return the error message, or null if not available
     */
    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Returns the HTTP status code.
     *
     * @return the HTTP status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the HTTP status code. Used during deserialization.
     *
     * @param statusCode the HTTP status code
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Returns true if this is a "not found" error (HTTP 404).
     *
     * @return true if HTTP status code is 404
     */
    public boolean isNotFound() {
        return statusCode == 404;
    }

    /**
     * Returns true if this is an "unauthorized" error (HTTP 401).
     *
     * @return true if HTTP status code is 401
     */
    public boolean isUnauthorized() {
        return statusCode == 401;
    }

    /**
     * Returns true if this is a "bad request" error (HTTP 400).
     *
     * @return true if HTTP status code is 400
     */
    public boolean isBadRequest() {
        return statusCode == 400;
    }

    /**
     * Returns true if this is a "forbidden" error (HTTP 403).
     *
     * @return true if HTTP status code is 403
     */
    public boolean isForbidden() {
        return statusCode == 403;
    }

    /**
     * Returns true if this is a server error (HTTP 5xx).
     *
     * @return true if HTTP status code is 500 or above
     */
    public boolean isServerError() {
        return statusCode >= 500;
    }

    /**
     * Returns true if this is a client error (HTTP 4xx).
     *
     * @return true if HTTP status code is between 400 and 499
     */
    public boolean isClientError() {
        return statusCode >= 400 && statusCode < 500;
    }

    @Override
    @NotNull
    @JsonIgnore
    public String getMessage() {
        return formatMessage(code, errorMessage, statusCode);
    }
}
