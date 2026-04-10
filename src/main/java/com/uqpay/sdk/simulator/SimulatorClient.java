package com.uqpay.sdk.simulator;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.config.Environment;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class SimulatorClient {

    private final SimulatorIssuingService issuing;
    private final SimulatorDepositsService deposits;

    public SimulatorClient(@NotNull ApiClient apiClient, @NotNull Environment environment) {
        Objects.requireNonNull(apiClient, "apiClient must not be null");
        Objects.requireNonNull(environment, "environment must not be null");
        this.issuing = new SimulatorIssuingService(apiClient, environment);
        this.deposits = new SimulatorDepositsService(apiClient, environment);
    }

    @NotNull
    public SimulatorIssuingService getIssuing() {
        return issuing;
    }

    @NotNull
    public SimulatorDepositsService getDeposits() {
        return deposits;
    }
}
