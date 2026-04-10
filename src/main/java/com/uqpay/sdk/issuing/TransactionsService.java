package com.uqpay.sdk.issuing;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.issuing.model.ListTransactionsRequest;
import com.uqpay.sdk.issuing.model.ListTransactionsResponse;
import com.uqpay.sdk.issuing.model.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class TransactionsService {

    private final ApiClient apiClient;

    public TransactionsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public Transaction get(@NotNull String transactionId) throws UqpayException {
        return get(transactionId, null);
    }

    @NotNull
    public Transaction get(@NotNull String transactionId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(transactionId, "transactionId must not be null");
        if (transactionId.isEmpty()) {
            throw new IllegalArgumentException("transactionId must not be empty");
        }
        return apiClient.get("/v1/issuing/transactions/" + transactionId, Transaction.class, options);
    }

    @NotNull
    public ListTransactionsResponse list(@NotNull ListTransactionsRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListTransactionsResponse list(@NotNull ListTransactionsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/issuing/transactions" + queryString, ListTransactionsResponse.class, options);
    }
}
