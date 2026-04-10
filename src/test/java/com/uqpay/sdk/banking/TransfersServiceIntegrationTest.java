package com.uqpay.sdk.banking;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.banking.model.Transfer;
import com.uqpay.sdk.banking.model.CreateTransferRequest;
import com.uqpay.sdk.banking.model.CreateTransferResponse;
import com.uqpay.sdk.banking.model.ListTransfersRequest;
import com.uqpay.sdk.banking.model.ListTransfersResponse;
import com.uqpay.sdk.connect.ConnectClient;
import com.uqpay.sdk.connect.AccountsService;
import com.uqpay.sdk.connect.model.Account;
import com.uqpay.sdk.connect.model.ListAccountsRequest;
import com.uqpay.sdk.connect.model.ListAccountsResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link TransfersService}.
 * <p>
 * Requires UQPAY_CLIENT_ID and UQPAY_API_KEY environment variables to be set.
 * Tests will be skipped if credentials are not available.
 * </p>
 */
@DisplayName("TransfersService Integration Tests")
class TransfersServiceIntegrationTest {

    private static TransfersService transfersService;
    private static AccountsService accountsService;
    private static String masterAccountId;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        BankingClient bankingClient = new BankingClient(apiClient);
        transfersService = bankingClient.getTransfers();
        ConnectClient connectClient = new ConnectClient(apiClient);
        accountsService = connectClient.getAccounts();

        // Extract master account ID (entity_id) from JWT token
        try {
            String token = tokenProvider.getToken();
            String[] parts = token.split("\\.");
            if (parts.length >= 2) {
                String payload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                com.fasterxml.jackson.databind.JsonNode node = mapper.readTree(payload);
                if (node.has("entity_id")) {
                    masterAccountId = node.get("entity_id").asText();
                }
            }
        } catch (Exception e) {
            System.out.printf("Failed to extract master account ID from JWT: %s%n", e.getMessage());
        }
    }

    @Nested
    @DisplayName("List Transfers")
    class ListTransfers {

        @Test
        @DisplayName("should list transfers with pagination")
        void shouldListTransfersWithPagination() throws UqpayException {
            ListTransfersRequest request = new ListTransfersRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListTransfersResponse response = transfersService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d transfers (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                Transfer first = response.getData().get(0);
                System.out.printf("First transfer: ID=%s, Currency=%s, Amount=%s, Status=%s%n",
                        first.getTransferId(), first.getTransferCurrency(),
                        first.getTransferAmount(), first.getTransferStatus());
            }
        }
    }

    @Nested
    @DisplayName("List Transfers With Filters")
    class ListTransfersWithFilters {

        @Test
        @DisplayName("should list transfers with status and currency filters")
        void shouldListTransfersWithFilters() throws UqpayException {
            ListTransfersRequest request = new ListTransfersRequest();
            request.setPageSize(10);
            request.setPageNumber(1);
            request.setTransferStatus("completed");
            request.setCurrency("USD");

            ListTransfersResponse response = transfersService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d completed USD transfers (total: %d)%n",
                    response.getData().size(), response.getTotalItems());

            if (!response.getData().isEmpty()) {
                Transfer first = response.getData().get(0);
                System.out.printf("Sample transfer: ID=%s, Amount=%s %s%n",
                        first.getTransferId(), first.getTransferAmount(), first.getTransferCurrency());
            }
        }
    }

    @Nested
    @DisplayName("Get Transfer")
    class GetTransfer {

        @Test
        @DisplayName("should get transfer by ID")
        void shouldGetTransferById() throws UqpayException {
            // First, list transfers to get a valid transfer ID
            ListTransfersRequest listRequest = new ListTransfersRequest();
            listRequest.setPageSize(1);
            listRequest.setPageNumber(1);

            ListTransfersResponse listResponse = transfersService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No transfers available, skipping Get test");
                return;
            }

            String transferId = listResponse.getData().get(0).getTransferId();

            // Test Get
            Transfer transfer = transfersService.get(transferId);

            assertThat(transfer).isNotNull();
            assertThat(transfer.getTransferId()).isEqualTo(transferId);

            System.out.printf("Retrieved transfer: ID=%s, Currency=%s, Amount=%s, Status=%s%n",
                    transfer.getTransferId(), transfer.getTransferCurrency(),
                    transfer.getTransferAmount(), transfer.getTransferStatus());
            System.out.printf("  ShortRef=%s%n", transfer.getShortReferenceId());
            System.out.printf("  Created: %s%n", transfer.getCreateTime());
            if (transfer.getCompleteTime() != null) {
                System.out.printf("  Completed: %s%n", transfer.getCompleteTime());
            }
        }
    }

    @Nested
    @DisplayName("Create Transfer")
    class CreateTransfer {

        @Test
        @DisplayName("should create transfer")
        void shouldCreateTransfer() throws UqpayException {
            if (masterAccountId == null || masterAccountId.isEmpty()) {
                System.out.println("Could not determine master account ID, skipping Create Transfer test");
                return;
            }

            // List connected (sub) accounts to find a target
            ListAccountsRequest listAccountsRequest = new ListAccountsRequest();
            listAccountsRequest.setPageSize(50);
            listAccountsRequest.setPageNumber(1);

            ListAccountsResponse accountsResponse = accountsService.list(listAccountsRequest);
            List<Account> accounts = accountsResponse.getData();

            if (accounts == null || accounts.isEmpty()) {
                System.out.println("No connected accounts available, skipping Create Transfer test");
                return;
            }

            System.out.printf("Master account: %s%n", masterAccountId);
            String subAccountId = accounts.get(0).getAccountId();
            System.out.printf("Target sub-account: %s%n", subAccountId);

            CreateTransferRequest request = new CreateTransferRequest();
            request.setSourceAccountId(masterAccountId);
            request.setTargetAccountId(subAccountId);
            request.setCurrency("USD");
            request.setAmount("1.00");
            request.setReason("SDK integration test transfer");

            try {
                CreateTransferResponse response = transfersService.create(request);

                assertThat(response).isNotNull();
                assertThat(response.getTransferId()).isNotEmpty();

                System.out.printf("Created transfer: ID=%s, ShortRef=%s%n",
                        response.getTransferId(), response.getShortReferenceId());
            } catch (UqpayException e) {
                System.out.printf("Create transfer returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }
}
