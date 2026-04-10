package com.uqpay.sdk.issuing;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.issuing.model.ListProductsRequest;
import com.uqpay.sdk.issuing.model.ListProductsResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class ProductsService {

    private final ApiClient apiClient;

    public ProductsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public ListProductsResponse list(@NotNull ListProductsRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListProductsResponse list(@NotNull ListProductsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/issuing/products" + queryString, ListProductsResponse.class, options);
    }
}
