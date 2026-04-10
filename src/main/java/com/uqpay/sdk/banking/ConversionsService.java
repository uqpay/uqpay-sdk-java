package com.uqpay.sdk.banking;

import com.uqpay.sdk.banking.model.Conversion;
import com.uqpay.sdk.banking.model.ConversionDate;
import com.uqpay.sdk.banking.model.CreateConversionRequest;
import com.uqpay.sdk.banking.model.CreateConversionResponse;
import com.uqpay.sdk.banking.model.CreateQuoteRequest;
import com.uqpay.sdk.banking.model.CreateQuoteResponse;
import com.uqpay.sdk.banking.model.ListConversionsRequest;
import com.uqpay.sdk.banking.model.ListConversionsResponse;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class ConversionsService {

    private final ApiClient apiClient;

    public ConversionsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public CreateConversionResponse create(@NotNull CreateConversionRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public CreateConversionResponse create(@NotNull CreateConversionRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/conversion", request, CreateConversionResponse.class, options);
    }

    @NotNull
    public Conversion get(@NotNull String conversionId) throws UqpayException {
        return get(conversionId, null);
    }

    @NotNull
    public Conversion get(@NotNull String conversionId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(conversionId, "conversionId must not be null");
        if (conversionId.isEmpty()) {
            throw new IllegalArgumentException("conversionId must not be empty");
        }
        return apiClient.get("/v1/conversion/" + conversionId, Conversion.class, options);
    }

    @NotNull
    public ListConversionsResponse list(@NotNull ListConversionsRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListConversionsResponse list(@NotNull ListConversionsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/conversion" + queryString, ListConversionsResponse.class, options);
    }

    @NotNull
    public CreateQuoteResponse createQuote(@NotNull CreateQuoteRequest request) throws UqpayException {
        return createQuote(request, null);
    }

    @NotNull
    public CreateQuoteResponse createQuote(@NotNull CreateQuoteRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/conversion/quote", request, CreateQuoteResponse.class, options);
    }

    @NotNull
    public ConversionDate[] listConversionDates(@NotNull String currencyFrom, @NotNull String currencyTo)
            throws UqpayException {
        return listConversionDates(currencyFrom, currencyTo, null);
    }

    @NotNull
    public ConversionDate[] listConversionDates(@NotNull String currencyFrom, @NotNull String currencyTo,
                                                 @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(currencyFrom, "currencyFrom must not be null");
        Objects.requireNonNull(currencyTo, "currencyTo must not be null");
        String query = "?currency_from=" + currencyFrom + "&currency_to=" + currencyTo;
        return apiClient.get("/v1/conversion/conversion_dates" + query,
                ConversionDate[].class, options);
    }
}
