package com.uqpay.sdk.payment;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.payment.model.CreateRefundRequest;
import com.uqpay.sdk.payment.model.ListRefundsRequest;
import com.uqpay.sdk.payment.model.ListRefundsResponse;
import com.uqpay.sdk.payment.model.Refund;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class RefundsService {

    private final ApiClient apiClient;

    public RefundsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public Refund create(@NotNull CreateRefundRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public Refund create(@NotNull CreateRefundRequest request, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v2/payment/refunds", request, Refund.class, options);
    }

    @NotNull
    public Refund get(@NotNull String refundId) throws UqpayException {
        return get(refundId, null);
    }

    @NotNull
    public Refund get(@NotNull String refundId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(refundId, "refundId must not be null");
        if (refundId.isEmpty()) {
            throw new IllegalArgumentException("refundId must not be empty");
        }
        return apiClient.get("/v2/payment/refunds/" + refundId, Refund.class, options);
    }

    @NotNull
    public ListRefundsResponse list(@NotNull ListRefundsRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListRefundsResponse list(@NotNull ListRefundsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v2/payment/refunds" + queryString, ListRefundsResponse.class, options);
    }
}
