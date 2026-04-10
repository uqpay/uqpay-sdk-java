package com.uqpay.sdk.issuing;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.issuing.model.IssuingBalance;
import com.uqpay.sdk.issuing.model.ListIssuingBalanceTransactionsRequest;
import com.uqpay.sdk.issuing.model.ListIssuingBalanceTransactionsResponse;
import com.uqpay.sdk.issuing.model.ListIssuingBalancesRequest;
import com.uqpay.sdk.issuing.model.ListIssuingBalancesResponse;
import com.uqpay.sdk.issuing.model.RetrieveBalanceRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class BalancesService {

    private final ApiClient apiClient;

    public BalancesService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public IssuingBalance retrieve(@NotNull RetrieveBalanceRequest request) throws UqpayException {
        return retrieve(request, null);
    }

    @NotNull
    public IssuingBalance retrieve(@NotNull RetrieveBalanceRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/balances", request, IssuingBalance.class, options);
    }

    @NotNull
    public ListIssuingBalancesResponse list(@NotNull ListIssuingBalancesRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListIssuingBalancesResponse list(@NotNull ListIssuingBalancesRequest request,
                                            @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/issuing/balances" + queryString, ListIssuingBalancesResponse.class, options);
    }

    @NotNull
    public ListIssuingBalanceTransactionsResponse listTransactions(
            @NotNull ListIssuingBalanceTransactionsRequest request) throws UqpayException {
        return listTransactions(request, null);
    }

    @NotNull
    public ListIssuingBalanceTransactionsResponse listTransactions(
            @NotNull ListIssuingBalanceTransactionsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/issuing/balances/transactions" + queryString,
                ListIssuingBalanceTransactionsResponse.class, options);
    }
}
