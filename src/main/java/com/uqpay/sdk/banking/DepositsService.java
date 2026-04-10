package com.uqpay.sdk.banking;

import com.uqpay.sdk.banking.model.Deposit;
import com.uqpay.sdk.banking.model.ListDepositsRequest;
import com.uqpay.sdk.banking.model.ListDepositsResponse;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class DepositsService {

    private final ApiClient apiClient;

    public DepositsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public ListDepositsResponse list(@NotNull ListDepositsRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListDepositsResponse list(@NotNull ListDepositsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/deposit" + queryString, ListDepositsResponse.class, options);
    }

    // Retrieve a single deposit by its UUID
    @NotNull
    public Deposit get(@NotNull String depositId) throws UqpayException {
        return get(depositId, null);
    }

    @NotNull
    public Deposit get(@NotNull String depositId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(depositId, "depositId must not be null");
        if (depositId.isEmpty()) {
            throw new IllegalArgumentException("depositId must not be empty");
        }
        return apiClient.get("/v1/deposit/" + depositId, Deposit.class, options);
    }
}
