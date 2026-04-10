package com.uqpay.sdk.supporting;

import com.uqpay.sdk.common.ApiClient;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class SupportingClient {

    private final FilesService files;

    public SupportingClient(@NotNull ApiClient apiClient) {
        Objects.requireNonNull(apiClient, "apiClient must not be null");
        this.files = new FilesService(apiClient);
    }

    @NotNull
    public FilesService getFiles() {
        return files;
    }
}
