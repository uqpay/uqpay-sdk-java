package com.uqpay.sdk.connect;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.connect.model.RfiModels;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class RfisService {
    private final ApiClient apiClient;

    public RfisService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public RfiModels.ListResponse list(int pageSize, int pageNumber, @Nullable String status,
                                       @Nullable RequestOptions options) throws UqpayException {
        String path = "/v1/rfis?page_size=" + pageSize + "&page_number=" + pageNumber;
        if (status != null && !status.isEmpty()) {
            path += "&status=" + URLEncoder.encode(status, StandardCharsets.UTF_8);
        }
        return apiClient.get(path, RfiModels.ListResponse.class, options);
    }

    @NotNull
    public RfiModels.ListResponse list(int pageSize, int pageNumber, @Nullable String status)
            throws UqpayException {
        return list(pageSize, pageNumber, status, null);
    }

    @NotNull
    public RfiModels.Rfi get(@NotNull String rfiId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(rfiId, "rfiId must not be null");
        return apiClient.get("/v1/rfis/" + rfiId, RfiModels.Rfi.class, options);
    }

    @NotNull
    public RfiModels.Rfi get(@NotNull String rfiId) throws UqpayException {
        return get(rfiId, null);
    }

    @NotNull
    public RfiModels.Rfi answer(@NotNull RfiModels.AnswerRequest request,
                                @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/rfis/answer", request, RfiModels.Rfi.class, options);
    }

    @NotNull
    public RfiModels.Rfi answer(@NotNull RfiModels.AnswerRequest request) throws UqpayException {
        return answer(request, null);
    }
}
