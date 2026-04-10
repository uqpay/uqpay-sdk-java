package com.uqpay.sdk.simulator;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Environment;
import com.uqpay.sdk.simulator.model.SimulateDepositRequest;
import com.uqpay.sdk.simulator.model.SimulateDepositResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class SimulatorDepositsService {

    private final ApiClient apiClient;
    private final Environment environment;

    public SimulatorDepositsService(@NotNull ApiClient apiClient, @NotNull Environment environment) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
        this.environment = Objects.requireNonNull(environment, "environment must not be null");
    }

    @NotNull
    public SimulateDepositResponse simulate(@NotNull SimulateDepositRequest request)
            throws UqpayException {
        return simulate(request, null);
    }

    @NotNull
    public SimulateDepositResponse simulate(@NotNull SimulateDepositRequest request,
            @Nullable RequestOptions options) throws UqpayException {
        assertSandbox();
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/simulation/deposit", request, SimulateDepositResponse.class, options);
    }

    private void assertSandbox() throws UqpayException {
        if (environment != Environment.SANDBOX) {
            throw new UqpayException(
                    "Simulator endpoints are only available in the SANDBOX environment.");
        }
    }
}
