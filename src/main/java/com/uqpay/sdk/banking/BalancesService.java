package com.uqpay.sdk.banking;

import com.uqpay.sdk.banking.model.Balance;
import com.uqpay.sdk.banking.model.ListBalanceTransactionsRequest;
import com.uqpay.sdk.banking.model.ListBalanceTransactionsResponse;
import com.uqpay.sdk.banking.model.ListBalancesRequest;
import com.uqpay.sdk.banking.model.ListBalancesResponse;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class BalancesService {

    private final ApiClient apiClient;

    public BalancesService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    // Retrieve balance by ISO 4217 currency code (e.g., "USD")
    @NotNull
    public Balance get(@NotNull String currency) throws UqpayException {
        return get(currency, null);
    }

    @NotNull
    public Balance get(@NotNull String currency, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(currency, "currency must not be null");
        if (currency.isEmpty()) {
            throw new IllegalArgumentException("currency must not be empty");
        }
        return apiClient.get("/v1/balances/" + currency, Balance.class, options);
    }

    @NotNull
    public ListBalancesResponse list(@NotNull ListBalancesRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListBalancesResponse list(@NotNull ListBalancesRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/balances" + queryString, ListBalancesResponse.class, options);
    }

    @NotNull
    public ListBalanceTransactionsResponse listTransactions(@NotNull ListBalanceTransactionsRequest request)
            throws UqpayException {
        return listTransactions(request, null);
    }

    @NotNull
    public ListBalanceTransactionsResponse listTransactions(@NotNull ListBalanceTransactionsRequest request,
                                                            @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/balances/transactions" + queryString,
                ListBalanceTransactionsResponse.class, options);
    }
}
