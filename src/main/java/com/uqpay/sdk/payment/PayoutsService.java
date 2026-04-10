package com.uqpay.sdk.payment;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.payment.model.CreatePayoutRequest;
import com.uqpay.sdk.payment.model.ListPayoutsRequest;
import com.uqpay.sdk.payment.model.ListPayoutsResponse;
import com.uqpay.sdk.payment.model.Payout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class PayoutsService {

    private final ApiClient apiClient;

    public PayoutsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public Payout create(@NotNull CreatePayoutRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public Payout create(@NotNull CreatePayoutRequest request, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v2/payment/payout/create", request, Payout.class, options);
    }

    @NotNull
    public Payout get(@NotNull String payoutId) throws UqpayException {
        return get(payoutId, null);
    }

    @NotNull
    public Payout get(@NotNull String payoutId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(payoutId, "payoutId must not be null");
        if (payoutId.isEmpty()) {
            throw new IllegalArgumentException("payoutId must not be empty");
        }
        return apiClient.get("/v2/payment/payout/" + payoutId, Payout.class, options);
    }

    @NotNull
    public ListPayoutsResponse list(@NotNull ListPayoutsRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListPayoutsResponse list(@NotNull ListPayoutsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v2/payment/payout" + queryString, ListPayoutsResponse.class, options);
    }
}
