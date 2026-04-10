package com.uqpay.sdk.simulator;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Environment;
import com.uqpay.sdk.simulator.model.SimulateAuthorizationRequest;
import com.uqpay.sdk.simulator.model.SimulateAuthorizationResponse;
import com.uqpay.sdk.simulator.model.SimulateReversalRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class SimulatorIssuingService {

    private final ApiClient apiClient;
    private final Environment environment;

    public SimulatorIssuingService(@NotNull ApiClient apiClient, @NotNull Environment environment) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
        this.environment = Objects.requireNonNull(environment, "environment must not be null");
    }

    @NotNull
    public SimulateAuthorizationResponse authorize(@NotNull SimulateAuthorizationRequest request)
            throws UqpayException {
        return authorize(request, null);
    }

    @NotNull
    public SimulateAuthorizationResponse authorize(@NotNull SimulateAuthorizationRequest request,
            @Nullable RequestOptions options) throws UqpayException {
        assertSandbox();
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/simulation/issuing/authorization", request,
                SimulateAuthorizationResponse.class, options);
    }

    @NotNull
    public SimulateAuthorizationResponse reverse(@NotNull SimulateReversalRequest request)
            throws UqpayException {
        return reverse(request, null);
    }

    @NotNull
    public SimulateAuthorizationResponse reverse(@NotNull SimulateReversalRequest request,
            @Nullable RequestOptions options) throws UqpayException {
        assertSandbox();
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/simulation/issuing/reversal", request,
                SimulateAuthorizationResponse.class, options);
    }

    private void assertSandbox() throws UqpayException {
        if (environment != Environment.SANDBOX) {
            throw new UqpayException(
                    "Simulator endpoints are only available in the SANDBOX environment.");
        }
    }
}
