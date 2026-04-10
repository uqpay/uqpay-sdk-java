package com.uqpay.sdk.payment;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.payment.model.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class PaymentIntentsService {

    private final ApiClient apiClient;

    public PaymentIntentsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public PaymentIntent create(@NotNull CreatePaymentIntentRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public PaymentIntent create(@NotNull CreatePaymentIntentRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v2/payment_intents/create", request, PaymentIntent.class, options);
    }

    @NotNull
    public PaymentIntent get(@NotNull String paymentIntentId) throws UqpayException {
        return get(paymentIntentId, null);
    }

    @NotNull
    public PaymentIntent get(@NotNull String paymentIntentId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(paymentIntentId, "paymentIntentId must not be null");
        if (paymentIntentId.isEmpty()) {
            throw new IllegalArgumentException("paymentIntentId must not be empty");
        }
        return apiClient.get("/v2/payment_intents/" + paymentIntentId, PaymentIntent.class, options);
    }

    @NotNull
    public PaymentIntent update(@NotNull String paymentIntentId, @NotNull UpdatePaymentIntentRequest request)
            throws UqpayException {
        return update(paymentIntentId, request, null);
    }

    @NotNull
    public PaymentIntent update(@NotNull String paymentIntentId, @NotNull UpdatePaymentIntentRequest request,
                                @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(paymentIntentId, "paymentIntentId must not be null");
        if (paymentIntentId.isEmpty()) {
            throw new IllegalArgumentException("paymentIntentId must not be empty");
        }
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v2/payment_intents/" + paymentIntentId, request, PaymentIntent.class, options);
    }

    @NotNull
    public PaymentIntent confirm(@NotNull String paymentIntentId, @NotNull ConfirmPaymentIntentRequest request)
            throws UqpayException {
        return confirm(paymentIntentId, request, null);
    }

    @NotNull
    public PaymentIntent confirm(@NotNull String paymentIntentId, @NotNull ConfirmPaymentIntentRequest request,
                                 @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(paymentIntentId, "paymentIntentId must not be null");
        if (paymentIntentId.isEmpty()) {
            throw new IllegalArgumentException("paymentIntentId must not be empty");
        }
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v2/payment_intents/" + paymentIntentId + "/confirm", request, PaymentIntent.class, options);
    }

    @NotNull
    public PaymentIntent capture(@NotNull String paymentIntentId, @NotNull CapturePaymentIntentRequest request)
            throws UqpayException {
        return capture(paymentIntentId, request, null);
    }

    @NotNull
    public PaymentIntent capture(@NotNull String paymentIntentId, @NotNull CapturePaymentIntentRequest request,
                                 @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(paymentIntentId, "paymentIntentId must not be null");
        if (paymentIntentId.isEmpty()) {
            throw new IllegalArgumentException("paymentIntentId must not be empty");
        }
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v2/payment_intents/" + paymentIntentId + "/capture", request, PaymentIntent.class, options);
    }

    @NotNull
    public PaymentIntent cancel(@NotNull String paymentIntentId, @NotNull CancelPaymentIntentRequest request)
            throws UqpayException {
        return cancel(paymentIntentId, request, null);
    }

    @NotNull
    public PaymentIntent cancel(@NotNull String paymentIntentId, @NotNull CancelPaymentIntentRequest request,
                                @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(paymentIntentId, "paymentIntentId must not be null");
        if (paymentIntentId.isEmpty()) {
            throw new IllegalArgumentException("paymentIntentId must not be empty");
        }
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v2/payment_intents/" + paymentIntentId + "/cancel", request, PaymentIntent.class, options);
    }

    @NotNull
    public ListPaymentIntentsResponse list(@NotNull ListPaymentIntentsRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListPaymentIntentsResponse list(@NotNull ListPaymentIntentsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v2/payment_intents" + queryString, ListPaymentIntentsResponse.class, options);
    }
}
