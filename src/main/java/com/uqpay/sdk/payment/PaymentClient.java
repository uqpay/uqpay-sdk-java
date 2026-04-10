package com.uqpay.sdk.payment;

import com.uqpay.sdk.common.ApiClient;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class PaymentClient {

    private final PaymentIntentsService paymentIntents;
    private final PaymentAttemptsService paymentAttempts;
    private final RefundsService refunds;
    private final ReportsService reports;
    private final BalancesService balances;
    private final PayoutsService payouts;
    private final BankAccountsService bankAccounts;

    public PaymentClient(@NotNull ApiClient apiClient) {
        Objects.requireNonNull(apiClient, "apiClient must not be null");
        this.paymentIntents = new PaymentIntentsService(apiClient);
        this.paymentAttempts = new PaymentAttemptsService(apiClient);
        this.refunds = new RefundsService(apiClient);
        this.reports = new ReportsService(apiClient);
        this.balances = new BalancesService(apiClient);
        this.payouts = new PayoutsService(apiClient);
        this.bankAccounts = new BankAccountsService(apiClient);
    }

    @NotNull
    public PaymentIntentsService getPaymentIntents() {
        return paymentIntents;
    }

    @NotNull
    public PaymentAttemptsService getPaymentAttempts() {
        return paymentAttempts;
    }

    @NotNull
    public RefundsService getRefunds() {
        return refunds;
    }

    @NotNull
    public ReportsService getReports() {
        return reports;
    }

    @NotNull
    public BalancesService getBalances() {
        return balances;
    }

    @NotNull
    public PayoutsService getPayouts() {
        return payouts;
    }

    @NotNull
    public BankAccountsService getBankAccounts() {
        return bankAccounts;
    }

}
