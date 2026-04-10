package com.uqpay.sdk.payment;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.payment.model.ListPaymentAttemptsRequest;
import com.uqpay.sdk.payment.model.ListPaymentAttemptsResponse;
import com.uqpay.sdk.payment.model.PaymentAttempt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class PaymentAttemptsService {

    private final ApiClient apiClient;

    public PaymentAttemptsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public PaymentAttempt get(@NotNull String attemptId) throws UqpayException {
        return get(attemptId, null);
    }

    @NotNull
    public PaymentAttempt get(@NotNull String attemptId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(attemptId, "attemptId must not be null");
        if (attemptId.isEmpty()) {
            throw new IllegalArgumentException("attemptId must not be empty");
        }
        return apiClient.get("/v2/payment/payment_attempts/" + attemptId, PaymentAttempt.class, options);
    }

    @NotNull
    public ListPaymentAttemptsResponse list(@NotNull ListPaymentAttemptsRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListPaymentAttemptsResponse list(@NotNull ListPaymentAttemptsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v2/payment/payment_attempts" + queryString, ListPaymentAttemptsResponse.class, options);
    }
}
