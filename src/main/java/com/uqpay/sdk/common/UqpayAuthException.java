package com.uqpay.sdk.common;

/**
 * Exception thrown when authentication fails.
 * <p>
 * This exception is thrown when:
 * <ul>
 *   <li>Token retrieval fails</li>
 *   <li>Credentials are invalid</li>
 *   <li>Token refresh fails</li>
 * </ul>
 */
public class UqpayAuthException extends UqpayException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new authentication exception with the specified detail message.
     *
     * @param message the detail message
     */
    public UqpayAuthException(String message) {
        super(message);
    }

    /**
     * Constructs a new authentication exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public UqpayAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
