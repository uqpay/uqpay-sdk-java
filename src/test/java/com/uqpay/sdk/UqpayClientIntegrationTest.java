package com.uqpay.sdk;

import com.uqpay.sdk.banking.model.ListBalancesRequest;
import com.uqpay.sdk.banking.model.ListBalancesResponse;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.connect.model.ListAccountsRequest;
import com.uqpay.sdk.connect.model.ListAccountsResponse;
import com.uqpay.sdk.issuing.model.ListProductsRequest;
import com.uqpay.sdk.issuing.model.ListProductsResponse;
import com.uqpay.sdk.payment.model.ListPaymentBalancesRequest;
import com.uqpay.sdk.payment.model.ListPaymentBalancesResponse;
import com.uqpay.sdk.supporting.model.DownloadLinksRequest;
import com.uqpay.sdk.supporting.model.DownloadLinksResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UqpayClient Integration Tests")
class UqpayClientIntegrationTest {

    private static UqpayClient client;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        String clientId = TestHelper.getClientId();
        String apiKey = TestHelper.getApiKey();
        client = UqpayClient.sandbox(clientId, apiKey);
    }

    @Test
    @DisplayName("should access banking module")
    void shouldAccessBankingModule() throws UqpayException {
        ListBalancesRequest request = new ListBalancesRequest();
        request.setPageSize(1);
        request.setPageNumber(1);

        ListBalancesResponse response = client.banking().getBalances().list(request);

        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        System.out.printf("Banking balances: %d items%n", response.getData().size());
    }

    @Test
    @DisplayName("should access issuing module")
    void shouldAccessIssuingModule() throws UqpayException {
        ListProductsRequest request = new ListProductsRequest();
        request.setPageSize(10);
        request.setPageNumber(1);

        ListProductsResponse response = client.issuing().getProducts().list(request);

        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        System.out.printf("Issuing products: %d items%n", response.getData().size());
    }

    @Test
    @DisplayName("should access connect module")
    void shouldAccessConnectModule() throws UqpayException {
        ListAccountsRequest request = new ListAccountsRequest();
        request.setPageSize(1);
        request.setPageNumber(1);

        ListAccountsResponse response = client.connect().getAccounts().list(request);

        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        System.out.printf("Connect accounts: %d items%n", response.getData().size());
    }

    @Test
    @DisplayName("should access payment module")
    void shouldAccessPaymentModule() throws UqpayException {
        ListPaymentBalancesRequest request = new ListPaymentBalancesRequest();
        request.setPageSize(1);
        request.setPageNumber(1);

        ListPaymentBalancesResponse response = client.payment().getBalances().list(request);

        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        System.out.printf("Payment balances: %d items%n", response.getData().size());
    }

    @Test
    @DisplayName("should access supporting module")
    void shouldAccessSupportingModule() throws UqpayException {
        DownloadLinksRequest request = new DownloadLinksRequest(Collections.singletonList("test-id"));

        DownloadLinksResponse response = client.supporting().getFiles().getDownloadLinks(request);

        assertThat(response).isNotNull();
        System.out.printf("Supporting files: files=%s, absentFiles=%s%n",
                response.getFiles(), response.getAbsentFiles());
    }
}
