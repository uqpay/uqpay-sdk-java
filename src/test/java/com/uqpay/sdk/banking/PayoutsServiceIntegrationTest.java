package com.uqpay.sdk.banking;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.banking.model.Address;
import com.uqpay.sdk.banking.model.BankDetails;
import com.uqpay.sdk.banking.model.Beneficiary;
import com.uqpay.sdk.banking.model.BeneficiaryCreationRequest;
import com.uqpay.sdk.banking.model.CreatePayoutRequest;
import com.uqpay.sdk.banking.model.CreatePayoutResponse;
import com.uqpay.sdk.banking.model.ListBeneficiariesRequest;
import com.uqpay.sdk.banking.model.ListBeneficiariesResponse;
import com.uqpay.sdk.banking.model.Payout;
import com.uqpay.sdk.banking.model.PayoutDetailResponse;
import com.uqpay.sdk.banking.model.ListPayoutsRequest;
import com.uqpay.sdk.banking.model.ListPayoutsResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link PayoutsService}.
 * <p>
 * Requires UQPAY_CLIENT_ID and UQPAY_API_KEY environment variables to be set.
 * Tests will be skipped if credentials are not available.
 * </p>
 */
@DisplayName("PayoutsService Integration Tests")
class PayoutsServiceIntegrationTest {

    private static PayoutsService payoutsService;
    private static BeneficiariesService beneficiariesService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        BankingClient bankingClient = new BankingClient(apiClient);
        payoutsService = bankingClient.getPayouts();
        beneficiariesService = bankingClient.getBeneficiaries();
    }

    @Nested
    @DisplayName("List Payouts")
    class ListPayouts {

        @Test
        @DisplayName("should list payouts with pagination")
        void shouldListPayoutsWithPagination() throws UqpayException {
            ListPayoutsRequest request = new ListPayoutsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListPayoutsResponse response = payoutsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d payouts (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                Payout first = response.getData().get(0);
                System.out.printf("First payout: ID=%s, Currency=%s, Amount=%s, Status=%s%n",
                        first.getPayoutId(), first.getPayoutCurrency(),
                        first.getPayoutAmount(), first.getPayoutStatus());
                if (first.getFeeAmount() != null) {
                    System.out.printf("  Fee: %s%n", first.getFeeAmount());
                }
                if (first.getPurposeCode() != null) {
                    System.out.printf("  Purpose: %s%n", first.getPurposeCode());
                }
            }
        }
    }

    @Nested
    @DisplayName("List Payouts With Filters")
    class ListPayoutsWithFilters {

        @Test
        @DisplayName("should list payouts with status and currency filters")
        void shouldListPayoutsWithFilters() throws UqpayException {
            ListPayoutsRequest request = new ListPayoutsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);
            request.setPayoutStatus("COMPLETED");

            ListPayoutsResponse response = payoutsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d completed payouts (total: %d)%n",
                    response.getData().size(), response.getTotalItems());

            for (Payout payout : response.getData()) {
                System.out.printf("  Payout: %s %s, Status=%s%n",
                        payout.getPayoutAmount(), payout.getPayoutCurrency(), payout.getPayoutStatus());
            }
        }

        @Test
        @DisplayName("should list payouts by status")
        void shouldListPayoutsByStatus() throws UqpayException {
            String[] statuses = {"READY_TO_SEND", "PENDING", "REJECTED", "FAILED", "COMPLETED"};

            for (String status : statuses) {
                ListPayoutsRequest request = new ListPayoutsRequest();
                request.setPageSize(10);
                request.setPageNumber(1);
                request.setPayoutStatus(status);

                ListPayoutsResponse response = payoutsService.list(request);

                assertThat(response).isNotNull();

                System.out.printf("%s payouts: %d found%n", status, response.getTotalItems());
            }
        }
    }

    @Nested
    @DisplayName("Get Payout")
    class GetPayout {

        @Test
        @DisplayName("should get payout by ID")
        void shouldGetPayoutById() throws UqpayException {
            // First, list payouts to get a valid payout ID
            ListPayoutsRequest listRequest = new ListPayoutsRequest();
            listRequest.setPageSize(1);
            listRequest.setPageNumber(1);

            ListPayoutsResponse listResponse = payoutsService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No payouts available, skipping Get test");
                return;
            }

            String payoutId = listResponse.getData().get(0).getPayoutId();

            // Test Get
            PayoutDetailResponse payout = payoutsService.get(payoutId);

            assertThat(payout).isNotNull();
            assertThat(payout.getPayoutId()).isEqualTo(payoutId);

            System.out.printf("Retrieved payout: ID=%s, Currency=%s, Amount=%s, Status=%s%n",
                    payout.getPayoutId(), payout.getPayoutCurrency(),
                    payout.getPayoutAmount(), payout.getPayoutStatus());
            System.out.printf("  ShortRef=%s%n", payout.getShortReferenceId());
            System.out.printf("  Created: %s%n", payout.getCreateTime());
            if (payout.getCompleteTime() != null) {
                System.out.printf("  Completed: %s%n", payout.getCompleteTime());
            }
            if (payout.getFeeAmount() != null) {
                System.out.printf("  Fee: %s%n", payout.getFeeAmount());
            }
            if (payout.getFailureReason() != null) {
                System.out.printf("  Failure reason: %s%n", payout.getFailureReason());
            }
            if (payout.getBeneficiary() != null) {
                System.out.printf("  Beneficiary: %s%n", payout.getBeneficiary());
            }
            if (payout.getPayoutMethod() != null) {
                System.out.printf("  Payout method: %s%n", payout.getPayoutMethod());
            }
        }
    }

    @Nested
    @DisplayName("Create Payout")
    class CreatePayout {

        @Test
        @DisplayName("should create payout with beneficiary ID")
        void shouldCreatePayout() throws UqpayException {
            // Fetch an active beneficiary
            ListBeneficiariesRequest listRequest = new ListBeneficiariesRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);
            ListBeneficiariesResponse listResponse = beneficiariesService.list(listRequest);

            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No beneficiaries available, skipping create payout test");
                return;
            }

            Beneficiary beneficiary = listResponse.getData().get(0);
            String beneficiaryId = beneficiary.getBeneficiaryId();
            String currency = beneficiary.getBankDetails() != null
                    ? beneficiary.getBankDetails().getAccountCurrencyCode() : "USD";
            System.out.printf("Using beneficiary: ID=%s, Type=%s, Method=%s, Currency=%s%n",
                    beneficiaryId, beneficiary.getEntityType(), beneficiary.getPaymentMethod(), currency);

            String payoutDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);

            CreatePayoutRequest request = new CreatePayoutRequest();
            request.setBeneficiaryId(beneficiaryId);
            request.setCurrency(currency);
            request.setAmount("10.00");
            request.setPurposeCode("GOODS_PURCHASED");
            request.setPayoutReference("SDK-TEST-REF-001");
            request.setFeePaidBy("OURS");
            request.setPayoutDate(payoutDate);

            CreatePayoutResponse response = payoutsService.create(request);

            assertThat(response).isNotNull();
            assertThat(response.getPayoutId()).isNotEmpty();
            assertThat(response.getShortReferenceId()).isNotEmpty();
            assertThat(response.getPayoutStatus()).isNotEmpty();

            System.out.printf("Created payout: ID=%s, ShortRef=%s, Status=%s%n",
                    response.getPayoutId(), response.getShortReferenceId(), response.getPayoutStatus());
        }

        @Test
        @DisplayName("should create payout with inline beneficiary")
        void shouldCreatePayoutWithInlineBeneficiary() throws UqpayException {
            String uniqueAcct = "TEST" + System.currentTimeMillis();

            BankDetails bankDetails = new BankDetails();
            bankDetails.setBankName("Bank of America");
            bankDetails.setBankAddress("123 Main St");
            bankDetails.setBankCountryCode("US");
            bankDetails.setAccountHolder("Test Beneficiary");
            bankDetails.setAccountCurrencyCode("USD");
            bankDetails.setAccountNumber(uniqueAcct);
            bankDetails.setSwiftCode("BOFAUS3N");
            bankDetails.setClearingSystem("SWIFT");

            Address address = new Address();
            address.setCountry("US");
            address.setCity("New York");
            address.setStreetAddress("123 Test St");
            address.setPostalCode("10001");
            address.setState("NY");

            BeneficiaryCreationRequest beneficiary = new BeneficiaryCreationRequest();
            beneficiary.setEntityType("INDIVIDUAL");
            beneficiary.setFirstName("Test");
            beneficiary.setLastName("Beneficiary");
            beneficiary.setPaymentMethod("SWIFT");
            beneficiary.setBankDetails(bankDetails);
            beneficiary.setAddress(address);

            String payoutDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);

            CreatePayoutRequest request = new CreatePayoutRequest();
            request.setBeneficiary(beneficiary);
            request.setCurrency("USD");
            request.setAmount("10.00");
            request.setPurposeCode("GOODS_PURCHASED");
            request.setPayoutReference("SDK-TEST-REF-002");
            request.setFeePaidBy("OURS");
            request.setPayoutDate(payoutDate);

            CreatePayoutResponse response = payoutsService.create(request);

            assertThat(response).isNotNull();
            assertThat(response.getPayoutId()).isNotEmpty();
            assertThat(response.getShortReferenceId()).isNotEmpty();
            assertThat(response.getPayoutStatus()).isNotEmpty();

            System.out.printf("Created payout with inline beneficiary: ID=%s, ShortRef=%s, Status=%s%n",
                    response.getPayoutId(), response.getShortReferenceId(), response.getPayoutStatus());
        }
    }
}
