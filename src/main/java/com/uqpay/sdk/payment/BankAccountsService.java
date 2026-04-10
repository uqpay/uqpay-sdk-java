package com.uqpay.sdk.payment;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.payment.model.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class BankAccountsService {

    private final ApiClient apiClient;

    public BankAccountsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public BankAccount create(@NotNull CreateBankAccountRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public BankAccount create(@NotNull CreateBankAccountRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v2/payment/bankaccount/create", request, BankAccount.class, options);
    }

    @NotNull
    public BankAccount get(@NotNull String id) throws UqpayException {
        return get(id, null);
    }

    @NotNull
    public BankAccount get(@NotNull String id, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(id, "id must not be null");
        if (id.isEmpty()) {
            throw new IllegalArgumentException("id must not be empty");
        }
        return apiClient.get("/v2/payment/bankaccount/" + id, BankAccount.class, options);
    }

    @NotNull
    public BankAccount update(@NotNull String id, @NotNull UpdateBankAccountRequest request) throws UqpayException {
        return update(id, request, null);
    }

    @NotNull
    public BankAccount update(@NotNull String id, @NotNull UpdateBankAccountRequest request,
                              @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(id, "id must not be null");
        if (id.isEmpty()) {
            throw new IllegalArgumentException("id must not be empty");
        }
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v2/payment/bankaccount/" + id, request, BankAccount.class, options);
    }

    @NotNull
    public ListBankAccountsResponse list(@NotNull ListBankAccountsRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListBankAccountsResponse list(@NotNull ListBankAccountsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v2/payment/bankaccount" + queryString, ListBankAccountsResponse.class, options);
    }
}
