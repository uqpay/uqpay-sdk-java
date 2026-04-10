package com.uqpay.sdk;

import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.banking.BankingClient;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.connect.ConnectClient;
import com.uqpay.sdk.issuing.IssuingClient;
import com.uqpay.sdk.payment.PaymentClient;
import com.uqpay.sdk.simulator.SimulatorClient;
import com.uqpay.sdk.supporting.SupportingClient;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Top-level client for the UQPAY SDK.
 * <p>
 * Provides access to all UQPAY API modules: Issuing, Banking, Connect, Payment, and Supporting.
 * </p>
 *
 * <pre>{@code
 * UqpayClient client = UqpayClient.sandbox("client-id", "api-key");
 *
 * // Access modules
 * client.issuing().getProducts().list(...);
 * client.banking().getBalances().list(...);
 * client.connect().getAccounts().list(...);
 * client.payment().getBalances().list(...);
 * client.supporting().getFiles().getDownloadLinks(...);
 * }</pre>
 */
public final class UqpayClient {

    private final IssuingClient issuing;
    private final BankingClient banking;
    private final ConnectClient connect;
    private final PaymentClient payment;
    private final SupportingClient supporting;
    private final SimulatorClient simulator;

    /**
     * Creates a new UQPAY client with the specified configuration.
     *
     * @param config the SDK configuration
     */
    public UqpayClient(@NotNull Configuration config) {
        Objects.requireNonNull(config, "config must not be null");

        // Main API client
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        this.issuing = new IssuingClient(apiClient);
        this.banking = new BankingClient(apiClient);
        this.connect = new ConnectClient(apiClient);
        this.payment = new PaymentClient(apiClient);
        this.simulator = new SimulatorClient(apiClient, config.getEnvironment());

        // Files API client (separate base URL + token provider)
        Configuration filesConfig = Configuration.builder()
                .clientId(config.getClientId())
                .apiKey(config.getApiKey())
                .environment(config.getEnvironment())
                .httpClient(config.getHttpClient())
                .baseUrlOverride(config.getEnvironment().getFilesBaseUrl())
                .build();
        DefaultTokenProvider filesTokenProvider = new DefaultTokenProvider(
                config.getEnvironment().getFilesBaseUrl(),
                config.getClientId(),
                config.getApiKey(),
                config.getHttpClient()
        );
        ApiClient filesApiClient = new ApiClient(filesConfig, filesTokenProvider);
        this.supporting = new SupportingClient(filesApiClient);
    }

    /**
     * Creates a new UQPAY client for the sandbox environment.
     *
     * @param clientId the client ID
     * @param apiKey   the API key
     * @return a new UQPAY client
     */
    @NotNull
    public static UqpayClient sandbox(@NotNull String clientId, @NotNull String apiKey) {
        return new UqpayClient(Configuration.sandbox(clientId, apiKey));
    }

    /**
     * Creates a new UQPAY client for the production environment.
     *
     * @param clientId the client ID
     * @param apiKey   the API key
     * @return a new UQPAY client
     */
    @NotNull
    public static UqpayClient production(@NotNull String clientId, @NotNull String apiKey) {
        return new UqpayClient(Configuration.production(clientId, apiKey));
    }

    /**
     * Returns the Issuing module client.
     *
     * @return the Issuing client
     */
    @NotNull
    public IssuingClient issuing() {
        return issuing;
    }

    /**
     * Returns the Banking module client.
     *
     * @return the Banking client
     */
    @NotNull
    public BankingClient banking() {
        return banking;
    }

    /**
     * Returns the Connect module client.
     *
     * @return the Connect client
     */
    @NotNull
    public ConnectClient connect() {
        return connect;
    }

    /**
     * Returns the Payment module client.
     *
     * @return the Payment client
     */
    @NotNull
    public PaymentClient payment() {
        return payment;
    }

    /**
     * Returns the Supporting module client.
     *
     * @return the Supporting client
     */
    @NotNull
    public SupportingClient supporting() {
        return supporting;
    }

    /**
     * Returns the Simulator module client.
     *
     * @return the Simulator client
     */
    @NotNull
    public SimulatorClient simulator() {
        return simulator;
    }
}
