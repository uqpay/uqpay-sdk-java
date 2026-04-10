package com.uqpay.sdk.common;

/**
 * Base exception class for all UQPAY SDK exceptions.
 * <p>
 * This is the root of the exception hierarchy. Catch this exception to handle all
 * UQPAY-related errors, or catch more specific subclasses for fine-grained error handling.
 * </p>
 *
 * <pre>{@code
 * try {
 *     client.issuing().cards().create(request);
 * } catch (UqpayApiException e) {
 *     // Handle API errors (4xx, 5xx responses)
 *     System.err.println("API error: " + e.getCode() + " - " + e.getMessage());
 * } catch (UqpayNetworkException e) {
 *     // Handle network/connection errors
 *     System.err.println("Network error: " + e.getMessage());
 * } catch (UqpayException e) {
 *     // Handle other UQPAY errors
 *     System.err.println("Error: " + e.getMessage());
 * }
 * }</pre>
 */
public class UqpayException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public UqpayException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public UqpayException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param cause the cause
     */
    public UqpayException(Throwable cause) {
        super(cause);
    }
}
