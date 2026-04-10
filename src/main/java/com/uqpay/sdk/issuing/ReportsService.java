package com.uqpay.sdk.issuing;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.issuing.model.CreateReportRequest;
import com.uqpay.sdk.issuing.model.CreateReportResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class ReportsService {

    private final ApiClient apiClient;

    public ReportsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public CreateReportResponse create(@NotNull CreateReportRequest request) throws UqpayException {
        return create(request, null);
    }

    // Creates a report. Requires x-idempotency-key header (UUID) in RequestOptions.
    // Use the returned report_id to download via GET /v1/issuing/reports/{id}.
    @NotNull
    public CreateReportResponse create(@NotNull CreateReportRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/reports", request, CreateReportResponse.class, options);
    }
}
