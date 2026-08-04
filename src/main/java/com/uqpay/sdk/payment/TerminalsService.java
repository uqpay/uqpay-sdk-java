package com.uqpay.sdk.payment;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.payment.model.TerminalModels;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class TerminalsService {
    private final ApiClient apiClient;

    public TerminalsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public TerminalModels.RegisterResponse register(@NotNull TerminalModels.RegisterRequest request,
                                                     @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v2/terminal/register", request, TerminalModels.RegisterResponse.class, options);
    }

    @NotNull
    public TerminalModels.RegisterResponse register(@NotNull TerminalModels.RegisterRequest request)
            throws UqpayException {
        return register(request, null);
    }

    @NotNull
    public TerminalModels.GetPinKeyResponse getPinKey(@NotNull TerminalModels.GetPinKeyRequest request,
                                                       @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v2/terminal/getPinKey", request, TerminalModels.GetPinKeyResponse.class, options);
    }

    @NotNull
    public TerminalModels.GetPinKeyResponse getPinKey(@NotNull TerminalModels.GetPinKeyRequest request)
            throws UqpayException {
        return getPinKey(request, null);
    }
}
