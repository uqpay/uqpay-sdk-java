package com.uqpay.sdk.banking;

import com.uqpay.sdk.banking.model.CreateVirtualAccountRequest;
import com.uqpay.sdk.banking.model.CreateVirtualAccountResponse;
import com.uqpay.sdk.banking.model.ListVirtualAccountsRequest;
import com.uqpay.sdk.banking.model.ListVirtualAccountsResponse;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class VirtualAccountsService {

    private final ApiClient apiClient;

    public VirtualAccountsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public CreateVirtualAccountResponse create(@NotNull CreateVirtualAccountRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public CreateVirtualAccountResponse create(@NotNull CreateVirtualAccountRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/virtual/accounts", request, CreateVirtualAccountResponse.class, options);
    }

    @NotNull
    public ListVirtualAccountsResponse list(@NotNull ListVirtualAccountsRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListVirtualAccountsResponse list(@NotNull ListVirtualAccountsRequest request,
                                             @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/virtual/accounts" + queryString, ListVirtualAccountsResponse.class, options);
    }
}
