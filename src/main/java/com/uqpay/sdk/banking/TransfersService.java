package com.uqpay.sdk.banking;

import com.uqpay.sdk.banking.model.CreateTransferRequest;
import com.uqpay.sdk.banking.model.CreateTransferResponse;
import com.uqpay.sdk.banking.model.ListTransfersRequest;
import com.uqpay.sdk.banking.model.ListTransfersResponse;
import com.uqpay.sdk.banking.model.Transfer;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class TransfersService {

    private final ApiClient apiClient;

    public TransfersService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public CreateTransferResponse create(@NotNull CreateTransferRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public CreateTransferResponse create(@NotNull CreateTransferRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/transfer", request, CreateTransferResponse.class, options);
    }

    @NotNull
    public Transfer get(@NotNull String transferId) throws UqpayException {
        return get(transferId, null);
    }

    @NotNull
    public Transfer get(@NotNull String transferId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(transferId, "transferId must not be null");
        if (transferId.isEmpty()) {
            throw new IllegalArgumentException("transferId must not be empty");
        }
        return apiClient.get("/v1/transfer/" + transferId, Transfer.class, options);
    }

    @NotNull
    public ListTransfersResponse list(@NotNull ListTransfersRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListTransfersResponse list(@NotNull ListTransfersRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/transfer" + queryString, ListTransfersResponse.class, options);
    }
}
