package com.uqpay.sdk.issuing;

import com.uqpay.sdk.common.ApiClient;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class IssuingClient {

    private final CardsService cards;
    private final CardholdersService cardholders;
    private final TransactionsService transactions;
    private final ProductsService products;
    private final BalancesService balances;
    private final TransfersService transfers;
    private final ReportsService reports;
    private final DownloadCenterService downloadCenter;
    private final AuthDecisionService authDecision;

    public IssuingClient(@NotNull ApiClient apiClient) {
        Objects.requireNonNull(apiClient, "apiClient must not be null");
        this.cards = new CardsService(apiClient);
        this.cardholders = new CardholdersService(apiClient);
        this.transactions = new TransactionsService(apiClient);
        this.products = new ProductsService(apiClient);
        this.balances = new BalancesService(apiClient);
        this.transfers = new TransfersService(apiClient);
        this.reports = new ReportsService(apiClient);
        this.downloadCenter = new DownloadCenterService(apiClient);
        this.authDecision = new AuthDecisionService();
    }

    @NotNull
    public CardsService getCards() {
        return cards;
    }

    @NotNull
    public CardholdersService getCardholders() {
        return cardholders;
    }

    @NotNull
    public TransactionsService getTransactions() {
        return transactions;
    }

    @NotNull
    public ProductsService getProducts() {
        return products;
    }

    @NotNull
    public BalancesService getBalances() {
        return balances;
    }

    @NotNull
    public TransfersService getTransfers() {
        return transfers;
    }

    @NotNull
    public ReportsService getReports() {
        return reports;
    }

    @NotNull
    public DownloadCenterService getDownloadCenter() {
        return downloadCenter;
    }

    @NotNull
    public AuthDecisionService getAuthDecision() {
        return authDecision;
    }
}
