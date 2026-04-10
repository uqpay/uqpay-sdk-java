package com.uqpay.sdk.common;

/**
 * Exception thrown when a network or connection error occurs.
 * <p>
 * This exception is thrown when:
 * <ul>
 *   <li>Connection to the API server fails</li>
 *   <li>Request times out</li>
 *   <li>DNS resolution fails</li>
 *   <li>Other I/O errors occur</li>
 * </ul>
 */
public class UqpayNetworkException extends UqpayException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new network exception with the specified detail message.
     *
     * @param message the detail message
     */
    public UqpayNetworkException(String message) {
        super(message);
    }

    /**
     * Constructs a new network exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public UqpayNetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
