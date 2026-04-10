package com.uqpay.sdk.issuing;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.issuing.model.Cardholder;
import com.uqpay.sdk.issuing.model.CreateCardholderRequest;
import com.uqpay.sdk.issuing.model.CreateCardholderResponse;
import com.uqpay.sdk.issuing.model.ListCardholdersRequest;
import com.uqpay.sdk.issuing.model.ListCardholdersResponse;
import com.uqpay.sdk.issuing.model.UpdateCardholderRequest;
import com.uqpay.sdk.issuing.model.UpdateCardholderResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class CardholdersService {

    private final ApiClient apiClient;

    public CardholdersService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public CreateCardholderResponse create(@NotNull CreateCardholderRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public CreateCardholderResponse create(@NotNull CreateCardholderRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/cardholders", request, CreateCardholderResponse.class, options);
    }

    @NotNull
    public Cardholder get(@NotNull String cardholderId) throws UqpayException {
        return get(cardholderId, null);
    }

    @NotNull
    public Cardholder get(@NotNull String cardholderId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(cardholderId, "cardholderId must not be null");
        if (cardholderId.isEmpty()) {
            throw new IllegalArgumentException("cardholderId must not be empty");
        }
        return apiClient.get("/v1/issuing/cardholders/" + cardholderId, Cardholder.class, options);
    }

    @NotNull
    public UpdateCardholderResponse update(@NotNull String cardholderId, @NotNull UpdateCardholderRequest request)
            throws UqpayException {
        return update(cardholderId, request, null);
    }

    @NotNull
    public UpdateCardholderResponse update(@NotNull String cardholderId, @NotNull UpdateCardholderRequest request,
                                           @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(cardholderId, "cardholderId must not be null");
        if (cardholderId.isEmpty()) {
            throw new IllegalArgumentException("cardholderId must not be empty");
        }
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/cardholders/" + cardholderId, request,
                UpdateCardholderResponse.class, options);
    }

    @NotNull
    public ListCardholdersResponse list(@NotNull ListCardholdersRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListCardholdersResponse list(@NotNull ListCardholdersRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/issuing/cardholders" + queryString, ListCardholdersResponse.class, options);
    }
}
