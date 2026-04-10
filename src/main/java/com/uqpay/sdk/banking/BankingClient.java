package com.uqpay.sdk.banking;

import com.uqpay.sdk.common.ApiClient;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class BankingClient {

    private final TransfersService transfers;
    private final BalancesService balances;
    private final VirtualAccountsService virtualAccounts;
    private final DepositsService deposits;
    private final BeneficiariesService beneficiaries;
    private final PayoutsService payouts;
    private final ConversionsService conversions;
    private final ExchangeRatesService exchangeRates;

    public BankingClient(@NotNull ApiClient apiClient) {
        Objects.requireNonNull(apiClient, "apiClient must not be null");
        this.transfers = new TransfersService(apiClient);
        this.balances = new BalancesService(apiClient);
        this.virtualAccounts = new VirtualAccountsService(apiClient);
        this.deposits = new DepositsService(apiClient);
        this.beneficiaries = new BeneficiariesService(apiClient);
        this.payouts = new PayoutsService(apiClient);
        this.conversions = new ConversionsService(apiClient);
        this.exchangeRates = new ExchangeRatesService(apiClient);
    }

    @NotNull
    public TransfersService getTransfers() {
        return transfers;
    }

    @NotNull
    public BalancesService getBalances() {
        return balances;
    }

    @NotNull
    public VirtualAccountsService getVirtualAccounts() {
        return virtualAccounts;
    }

    @NotNull
    public DepositsService getDeposits() {
        return deposits;
    }

    @NotNull
    public BeneficiariesService getBeneficiaries() {
        return beneficiaries;
    }

    @NotNull
    public PayoutsService getPayouts() {
        return payouts;
    }

    @NotNull
    public ConversionsService getConversions() {
        return conversions;
    }

    @NotNull
    public ExchangeRatesService getExchangeRates() {
        return exchangeRates;
    }
}
