package com.uqpay.sdk.banking;

import com.uqpay.sdk.banking.model.Beneficiary;
import com.uqpay.sdk.banking.model.BeneficiaryCheckRequest;
import com.uqpay.sdk.banking.model.BeneficiaryCreationRequest;
import com.uqpay.sdk.banking.model.BeneficiaryCreationResponse;
import com.uqpay.sdk.banking.model.ListBeneficiariesRequest;
import com.uqpay.sdk.banking.model.ListBeneficiariesResponse;
import com.uqpay.sdk.banking.model.ListPaymentMethodsResponse;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class BeneficiariesService {

    private final ApiClient apiClient;

    public BeneficiariesService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    @NotNull
    public BeneficiaryCreationResponse create(@NotNull BeneficiaryCreationRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public BeneficiaryCreationResponse create(@NotNull BeneficiaryCreationRequest request,
                                               @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/beneficiaries", request, BeneficiaryCreationResponse.class, options);
    }

    @NotNull
    public Beneficiary get(@NotNull String beneficiaryId) throws UqpayException {
        return get(beneficiaryId, null);
    }

    @NotNull
    public Beneficiary get(@NotNull String beneficiaryId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(beneficiaryId, "beneficiaryId must not be null");
        if (beneficiaryId.isEmpty()) {
            throw new IllegalArgumentException("beneficiaryId must not be empty");
        }
        return apiClient.get("/v1/beneficiaries/" + beneficiaryId, Beneficiary.class, options);
    }

    @NotNull
    public ListBeneficiariesResponse list(@NotNull ListBeneficiariesRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListBeneficiariesResponse list(@NotNull ListBeneficiariesRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/beneficiaries" + queryString, ListBeneficiariesResponse.class, options);
    }

    @NotNull
    public BeneficiaryCreationResponse update(@NotNull String beneficiaryId, @NotNull BeneficiaryCreationRequest request)
            throws UqpayException {
        return update(beneficiaryId, request, null);
    }

    @NotNull
    public BeneficiaryCreationResponse update(@NotNull String beneficiaryId, @NotNull BeneficiaryCreationRequest request,
                               @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(beneficiaryId, "beneficiaryId must not be null");
        if (beneficiaryId.isEmpty()) {
            throw new IllegalArgumentException("beneficiaryId must not be empty");
        }
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/beneficiaries/" + beneficiaryId, request, BeneficiaryCreationResponse.class, options);
    }

    public boolean delete(@NotNull String beneficiaryId) throws UqpayException {
        return delete(beneficiaryId, null);
    }

    public boolean delete(@NotNull String beneficiaryId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(beneficiaryId, "beneficiaryId must not be null");
        if (beneficiaryId.isEmpty()) {
            throw new IllegalArgumentException("beneficiaryId must not be empty");
        }
        apiClient.post("/v1/beneficiaries/" + beneficiaryId + "/delete", null, Beneficiary.class, options);
        return true;
    }

    @NotNull
    public ListPaymentMethodsResponse listPaymentMethods(@NotNull String currency, @NotNull String country)
            throws UqpayException {
        return listPaymentMethods(currency, country, null);
    }

    @NotNull
    public ListPaymentMethodsResponse listPaymentMethods(@NotNull String currency, @NotNull String country,
                                                          @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(currency, "currency must not be null");
        Objects.requireNonNull(country, "country must not be null");
        String query;
        try {
            query = "?currency=" + URLEncoder.encode(currency, StandardCharsets.UTF_8.name())
                    + "&country=" + URLEncoder.encode(country, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            query = "?currency=" + currency + "&country=" + country;
        }
        return apiClient.get("/v1/beneficiaries/paymentmethods" + query,
                ListPaymentMethodsResponse.class, options);
    }

    @NotNull
    public Beneficiary check(@NotNull BeneficiaryCheckRequest request) throws UqpayException {
        return check(request, null);
    }

    @NotNull
    public Beneficiary check(@NotNull BeneficiaryCheckRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/beneficiaries/check", request, Beneficiary.class, options);
    }
}
