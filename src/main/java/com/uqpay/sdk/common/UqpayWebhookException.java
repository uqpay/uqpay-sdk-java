package com.uqpay.sdk.common;

public class UqpayWebhookException extends UqpayException {

    public UqpayWebhookException(String message) {
        super(message);
    }

    public UqpayWebhookException(String message, Throwable cause) {
        super(message, cause);
    }
}
