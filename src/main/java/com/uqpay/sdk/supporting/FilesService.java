package com.uqpay.sdk.supporting;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.supporting.model.DownloadLinksRequest;
import com.uqpay.sdk.supporting.model.DownloadLinksResponse;
import com.uqpay.sdk.supporting.model.UploadFileResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;

public final class FilesService {

    private final ApiClient apiClient;

    public FilesService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public UploadFileResponse upload(@NotNull File file) throws UqpayException {
        return upload(file, null, null);
    }

    @NotNull
    public UploadFileResponse upload(@NotNull File file, @Nullable String notes) throws UqpayException {
        return upload(file, notes, null);
    }

    @NotNull
    public UploadFileResponse upload(@NotNull File file, @Nullable String notes, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(file, "file must not be null");
        return apiClient.postMultipart("/v1/files/upload", file, notes, UploadFileResponse.class, options);
    }

    @NotNull
    public DownloadLinksResponse getDownloadLinks(@NotNull DownloadLinksRequest request) throws UqpayException {
        return getDownloadLinks(request, null);
    }

    @NotNull
    public DownloadLinksResponse getDownloadLinks(@NotNull DownloadLinksRequest request,
                                                   @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/files/download_links", request, DownloadLinksResponse.class, options);
    }
}
