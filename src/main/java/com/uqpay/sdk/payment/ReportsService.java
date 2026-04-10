package com.uqpay.sdk.payment;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.payment.model.ListSettlementsRequest;
import com.uqpay.sdk.payment.model.ListSettlementsResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class ReportsService {

    private final ApiClient apiClient;

    public ReportsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public ListSettlementsResponse listSettlements(@NotNull ListSettlementsRequest request) throws UqpayException {
        return listSettlements(request, null);
    }

    @NotNull
    public ListSettlementsResponse listSettlements(@NotNull ListSettlementsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v2/payment/settlements" + queryString, ListSettlementsResponse.class, options);
    }
}
