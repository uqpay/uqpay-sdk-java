package com.uqpay.sdk.issuing;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.issuing.model.CreateIssuingTransferRequest;
import com.uqpay.sdk.issuing.model.CreateIssuingTransferResponse;
import com.uqpay.sdk.issuing.model.IssuingTransfer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class TransfersService {

    private final ApiClient apiClient;

    public TransfersService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public CreateIssuingTransferResponse create(@NotNull CreateIssuingTransferRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public CreateIssuingTransferResponse create(@NotNull CreateIssuingTransferRequest request,
                                                @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/transfers", request, CreateIssuingTransferResponse.class, options);
    }

    @NotNull
    public IssuingTransfer retrieve(@NotNull String transferId) throws UqpayException {
        return retrieve(transferId, null);
    }

    @NotNull
    public IssuingTransfer retrieve(@NotNull String transferId, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(transferId, "transferId must not be null");
        if (transferId.isEmpty()) {
            throw new IllegalArgumentException("transferId must not be empty");
        }
        return apiClient.get("/v1/issuing/transfers/" + transferId, IssuingTransfer.class, options);
    }
}
