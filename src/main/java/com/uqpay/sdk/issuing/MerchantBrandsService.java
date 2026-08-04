package com.uqpay.sdk.issuing;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.issuing.model.MerchantBrandModels;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class MerchantBrandsService {
    private final ApiClient apiClient;

    public MerchantBrandsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public MerchantBrandModels.ListResponse list(@Nullable String displayName, @Nullable String merchantCode,
                                                  int pageNumber, int pageSize) throws UqpayException {
        return list(displayName, merchantCode, pageNumber, pageSize, null);
    }

    @NotNull
    public MerchantBrandModels.ListResponse list(@Nullable String displayName, @Nullable String merchantCode,
                                                  int pageNumber, int pageSize,
                                                  @Nullable RequestOptions options) throws UqpayException {
        String path = "/v1/issuing/merchant_brands?page_number=" + pageNumber + "&page_size=" + pageSize;
        if (displayName != null && !displayName.isEmpty()) {
            path += "&display_name=" + URLEncoder.encode(displayName, StandardCharsets.UTF_8);
        }
        if (merchantCode != null && !merchantCode.isEmpty()) {
            path += "&merchant_code=" + URLEncoder.encode(merchantCode, StandardCharsets.UTF_8);
        }
        return apiClient.get(path, MerchantBrandModels.ListResponse.class, options);
    }
}
