package com.uqpay.sdk.banking;

import com.uqpay.sdk.banking.model.CreatePayoutRequest;
import com.uqpay.sdk.banking.model.CreatePayoutResponse;
import com.uqpay.sdk.banking.model.ListPayoutsRequest;
import com.uqpay.sdk.banking.model.ListPayoutsResponse;
import com.uqpay.sdk.banking.model.PayoutDetailResponse;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class PayoutsService {

    private final ApiClient apiClient;

    public PayoutsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public CreatePayoutResponse create(@NotNull CreatePayoutRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public CreatePayoutResponse create(@NotNull CreatePayoutRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/payouts", request, CreatePayoutResponse.class, options);
    }

    @NotNull
    public PayoutDetailResponse get(@NotNull String payoutId) throws UqpayException {
        return get(payoutId, null);
    }

    @NotNull
    public PayoutDetailResponse get(@NotNull String payoutId, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(payoutId, "payoutId must not be null");
        if (payoutId.isEmpty()) {
            throw new IllegalArgumentException("payoutId must not be empty");
        }
        return apiClient.get("/v1/payouts/" + payoutId, PayoutDetailResponse.class, options);
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
        return apiClient.get("/v1/payouts" + queryString, ListPayoutsResponse.class, options);
    }
}
