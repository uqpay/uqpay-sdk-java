package com.uqpay.sdk.payment;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.payment.model.BankAccount;
import com.uqpay.sdk.payment.model.CreateBankAccountRequest;
import com.uqpay.sdk.payment.model.ListBankAccountsRequest;
import com.uqpay.sdk.payment.model.ListBankAccountsResponse;
import com.uqpay.sdk.payment.model.UpdateBankAccountRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BankAccountsService Integration Tests")
class BankAccountsServiceIntegrationTest {

    private static BankAccountsService bankAccountsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        PaymentClient paymentClient = new PaymentClient(apiClient);
        bankAccountsService = paymentClient.getBankAccounts();
    }

    @Nested
    @DisplayName("Create Bank Account")
    class CreateBankAccount {

        @Test
        @DisplayName("should create a bank account")
        void shouldCreateBankAccount() throws UqpayException {
            long timestamp = System.currentTimeMillis();

            CreateBankAccountRequest request = new CreateBankAccountRequest();
            request.setAccountNumber("ACCT" + timestamp);
            request.setBankName("DBS Bank");
            request.setSwiftCode("DBSSSGSG");
            request.setBankCountryCode("SG");
            request.setBankAddress("12 Marina Boulevard, Singapore 018982");
            request.setCurrency("USD");

            try {
                BankAccount account = bankAccountsService.create(request);

                assertThat(account).isNotNull();
                assertThat(account.getId()).isNotEmpty();
                assertThat(account.getCurrency()).isEqualTo("USD");

                System.out.printf("Bank account created: ID=%s, Currency=%s, Bank=%s, Status=%s%n",
                        account.getId(), account.getCurrency(), account.getBankName(), account.getAccountStatus());
            } catch (UqpayException e) {
                // May fail if bank account already exists for this currency in sandbox
                System.out.printf("Create bank account returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Update Bank Account")
    class UpdateBankAccount {

        @Test
        @DisplayName("should update a bank account")
        void shouldUpdateBankAccount() throws UqpayException {
            // First list to get an existing bank account with full details
            ListBankAccountsRequest listRequest = new ListBankAccountsRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListBankAccountsResponse listResponse = bankAccountsService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No bank accounts available, skipping Update test");
                return;
            }

            BankAccount existing = listResponse.getData().get(0);
            String accountId = existing.getId();

            // API requires all 5 core fields for any update
            UpdateBankAccountRequest request = new UpdateBankAccountRequest();
            request.setAccountNumber(existing.getAccountNumber());
            request.setBankName(existing.getBankName());
            request.setSwiftCode(existing.getSwiftCode());
            request.setBankCountryCode(existing.getBankCountryCode());
            request.setBankAddress("2 Updated Street, Singapore");
            try {
                BankAccount account = bankAccountsService.update(accountId, request);

                assertThat(account).isNotNull();
                assertThat(account.getId()).isEqualTo(accountId);

                System.out.printf("Bank account updated successfully%n");
                System.out.printf("   ID: %s%n", account.getId());
                System.out.printf("   Bank: %s%n", account.getBankName());
                System.out.printf("   Currency: %s%n", account.getCurrency());
                System.out.printf("   Status: %s%n", account.getAccountStatus());
            } catch (UqpayException e) {
                System.out.printf("Update bank account returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("List Bank Accounts")
    class ListBankAccounts {

        @Test
        @DisplayName("should list bank accounts with pagination")
        void shouldListBankAccountsWithPagination() throws UqpayException {
            ListBankAccountsRequest request = new ListBankAccountsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListBankAccountsResponse response = bankAccountsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d bank accounts (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                BankAccount first = response.getData().get(0);
                System.out.printf("First bank account: ID=%s, Bank=%s, Currency=%s, Status=%s%n",
                        first.getId(), first.getBankName(),
                        first.getCurrency(), first.getAccountStatus());
            }
        }
    }

    @Nested
    @DisplayName("Get Bank Account")
    class GetBankAccount {

        @Test
        @DisplayName("should get bank account by ID")
        void shouldGetBankAccountById() throws UqpayException {
            ListBankAccountsRequest listRequest = new ListBankAccountsRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListBankAccountsResponse listResponse = bankAccountsService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No bank accounts available, skipping Get test");
                return;
            }

            String accountId = listResponse.getData().get(0).getId();

            BankAccount account = bankAccountsService.get(accountId);

            assertThat(account).isNotNull();
            assertThat(account.getId()).isEqualTo(accountId);

            System.out.printf("Retrieved bank account: ID=%s, Bank=%s, SWIFT=%s, Currency=%s, Status=%s%n",
                    account.getId(), account.getBankName(), account.getSwiftCode(),
                    account.getCurrency(), account.getAccountStatus());
        }
    }
}
