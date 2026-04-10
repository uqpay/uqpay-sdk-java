package com.uqpay.sdk.banking;

import com.uqpay.sdk.banking.model.ListRatesRequest;
import com.uqpay.sdk.banking.model.ListRatesResponse;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class ExchangeRatesService {

    private final ApiClient apiClient;

    public ExchangeRatesService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public ListRatesResponse list(@NotNull ListRatesRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListRatesResponse list(@NotNull ListRatesRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/exchange/rates" + queryString, ListRatesResponse.class, options);
    }
}
