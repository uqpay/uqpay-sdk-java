package com.uqpay.sdk.issuing;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class DownloadCenterService {

    private final ApiClient apiClient;

    public DownloadCenterService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public byte[] download(@NotNull String reportId) throws UqpayException {
        return download(reportId, null);
    }

    @NotNull
    public byte[] download(@NotNull String reportId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(reportId, "reportId must not be null");
        if (reportId.isEmpty()) {
            throw new IllegalArgumentException("reportId must not be empty");
        }
        return apiClient.getRaw("/v1/issuing/reports/" + reportId, options);
    }
}
