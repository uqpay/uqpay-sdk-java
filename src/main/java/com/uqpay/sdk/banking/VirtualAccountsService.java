package com.uqpay.sdk.banking;

import com.uqpay.sdk.banking.model.CreateVirtualAccountRequest;
import com.uqpay.sdk.banking.model.CreateVirtualAccountResponse;
import com.uqpay.sdk.banking.model.ListVirtualAccountsRequest;
import com.uqpay.sdk.banking.model.ListVirtualAccountsResponse;
import com.uqpay.sdk.banking.model.ListVirtualAccountApplicationsRequest;
import com.uqpay.sdk.banking.model.ListVirtualAccountApplicationsResponse;
import com.uqpay.sdk.banking.model.RetrieveVirtualAccountApplicationResponse;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class VirtualAccountsService {

    private final ApiClient apiClient;

    public VirtualAccountsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public CreateVirtualAccountResponse create(@NotNull CreateVirtualAccountRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public CreateVirtualAccountResponse create(@NotNull CreateVirtualAccountRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        request.validate();
        validateCreateOptions(options);
        return apiClient.post("/v1/virtual/accounts", request, CreateVirtualAccountResponse.class, options);
    }

    @NotNull
    public ListVirtualAccountsResponse list(@NotNull ListVirtualAccountsRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListVirtualAccountsResponse list(@NotNull ListVirtualAccountsRequest request,
                                             @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/virtual/accounts" + queryString, ListVirtualAccountsResponse.class, options);
    }

    @NotNull
    public ListVirtualAccountApplicationsResponse listApplications(
            @NotNull ListVirtualAccountApplicationsRequest request) throws UqpayException {
        return listApplications(request, null);
    }

    @NotNull
    public ListVirtualAccountApplicationsResponse listApplications(
            @NotNull ListVirtualAccountApplicationsRequest request,
            @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.get("/v1/virtual/applications" + request.toQueryString(),
                ListVirtualAccountApplicationsResponse.class, options);
    }

    @NotNull
    public RetrieveVirtualAccountApplicationResponse retrieveApplication(
            @NotNull String applicationId) throws UqpayException {
        return retrieveApplication(applicationId, null);
    }

    @NotNull
    public RetrieveVirtualAccountApplicationResponse retrieveApplication(
            @NotNull String applicationId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(applicationId, "applicationId must not be null");
        String normalized = applicationId.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("applicationId must not be blank");
        }
        return apiClient.get("/v1/virtual/applications/" + normalized,
                RetrieveVirtualAccountApplicationResponse.class, options);
    }

    private static void validateCreateOptions(@Nullable RequestOptions options) {
        if (options == null || options.getIdempotencyKey() == null) {
            return;
        }
        String key = options.getIdempotencyKey();
        if (key.trim().isEmpty() || key.length() > 64) {
            throw new IllegalArgumentException("idempotencyKey must be 1 to 64 characters");
        }
    }
}
