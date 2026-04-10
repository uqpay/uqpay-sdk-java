package com.uqpay.sdk.payment;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.payment.model.ListPaymentBalancesRequest;
import com.uqpay.sdk.payment.model.ListPaymentBalancesResponse;
import com.uqpay.sdk.payment.model.PaymentBalance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class BalancesService {

    private final ApiClient apiClient;

    public BalancesService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public PaymentBalance get(@NotNull String currency) throws UqpayException { // currency: ISO 4217 code, e.g. "SGD"
        return get(currency, null);
    }

    @NotNull
    public PaymentBalance get(@NotNull String currency, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(currency, "currency must not be null");
        if (currency.isEmpty()) {
            throw new IllegalArgumentException("currency must not be empty");
        }
        return apiClient.get("/v2/payment/balances/" + currency, PaymentBalance.class, options);
    }

    @NotNull
    public ListPaymentBalancesResponse list(@NotNull ListPaymentBalancesRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListPaymentBalancesResponse list(@NotNull ListPaymentBalancesRequest request, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.get("/v2/payment/balances" + request.toQueryString(), ListPaymentBalancesResponse.class, options);
    }
}
