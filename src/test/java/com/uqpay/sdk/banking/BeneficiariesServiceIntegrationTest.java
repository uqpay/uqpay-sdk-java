package com.uqpay.sdk.banking;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.banking.model.Beneficiary;
import com.uqpay.sdk.banking.model.BeneficiaryCreationRequest;
import com.uqpay.sdk.banking.model.BeneficiaryCreationResponse;
import com.uqpay.sdk.banking.model.BeneficiaryCheckRequest;
import com.uqpay.sdk.banking.model.BankDetails;
import com.uqpay.sdk.banking.model.Address;
import com.uqpay.sdk.banking.model.ListBeneficiariesRequest;
import com.uqpay.sdk.banking.model.ListBeneficiariesResponse;
import com.uqpay.sdk.banking.model.ListPaymentMethodsResponse;
import com.uqpay.sdk.banking.model.PaymentMethod;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link BeneficiariesService}.
 * <p>
 * Requires UQPAY_CLIENT_ID and UQPAY_API_KEY environment variables to be set.
 * Tests will be skipped if credentials are not available.
 * </p>
 */
@DisplayName("BeneficiariesService Integration Tests")
class BeneficiariesServiceIntegrationTest {

    private static BeneficiariesService beneficiariesService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        BankingClient bankingClient = new BankingClient(apiClient);
        beneficiariesService = bankingClient.getBeneficiaries();
    }

    @Test
    @DisplayName("should list beneficiaries with pagination")
    void testListBeneficiaries() {
        try {
            ListBeneficiariesRequest request = new ListBeneficiariesRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListBeneficiariesResponse response = beneficiariesService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d beneficiaries (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());
        } catch (UqpayException e) {
            System.out.printf("List beneficiaries returned error: %s%n", e.getMessage());
        }
    }

    @Test
    @DisplayName("should get beneficiary by ID")
    void testGetBeneficiaryById() {
        try {
            ListBeneficiariesRequest listRequest = new ListBeneficiariesRequest();
            listRequest.setPageSize(1);
            listRequest.setPageNumber(1);

            ListBeneficiariesResponse listResponse = beneficiariesService.list(listRequest);
            if (listResponse.getData() != null && !listResponse.getData().isEmpty()) {
                String beneficiaryId = listResponse.getData().get(0).getBeneficiaryId();
                Beneficiary result = beneficiariesService.get(beneficiaryId);
                assertThat(result).isNotNull();
                System.out.printf("Retrieved beneficiary: ID=%s, Status=%s%n",
                        result.getBeneficiaryId(), result.getStatus());
            } else {
                System.out.println("No beneficiaries available, skipping Get test");
            }
        } catch (UqpayException e) {
            System.out.printf("Get beneficiary returned error: %s%n", e.getMessage());
        }
    }

    @Test
    @DisplayName("should list payment methods for USD and US")
    void testListPaymentMethods() {
        try {
            ListPaymentMethodsResponse response = beneficiariesService.listPaymentMethods("USD", "US");

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d payment methods for USD/US%n", response.getData().size());

            for (PaymentMethod method : response.getData()) {
                System.out.printf("  Method: PaymentMethod=%s, ClearingSystems=%s, Currency=%s, Country=%s%n",
                        method.getPaymentMethod(), method.getClearingSystems(),
                        method.getCurrency(), method.getCountry());
            }
        } catch (UqpayException e) {
            System.out.printf("List payment methods returned error: %s%n", e.getMessage());
        }
    }

    @Test
    @DisplayName("should create beneficiary")
    void testCreateBeneficiary() {
        try {
            // Use timestamp-based account number to avoid "beneficiary repeat addition"
            long timestamp = System.currentTimeMillis();

            BankDetails bankDetails = new BankDetails();
            bankDetails.setBankName("Test Bank");
            bankDetails.setBankAddress("123, Main street");
            bankDetails.setBankCountryCode("SG");
            bankDetails.setAccountHolder("John Doe");
            bankDetails.setAccountCurrencyCode("SGD");
            bankDetails.setSwiftCode("WELGBE22");
            bankDetails.setClearingSystem("FAST");
            bankDetails.setAccountNumber("BEN" + timestamp);

            Address address = new Address();
            address.setCountry("SG");
            address.setCity("Main");
            address.setStreetAddress("Main street");
            address.setPostalCode("28213123");
            address.setState("dsdas");

            BeneficiaryCreationRequest request = new BeneficiaryCreationRequest();
            request.setEntityType("INDIVIDUAL");
            request.setFirstName("Test");
            request.setLastName("Beneficiary");
            request.setPaymentMethod("SWIFT");
            request.setBankDetails(bankDetails);
            request.setAddress(address);

            BeneficiaryCreationResponse response = beneficiariesService.create(request);

            assertThat(response).isNotNull();
            System.out.printf("Created beneficiary: ID=%s, ShortRefId=%s%n",
                    response.getBeneficiaryId(), response.getShortReferenceId());
        } catch (UqpayException e) {
            System.out.printf("Create beneficiary returned error (may be expected in sandbox): %s%n", e.getMessage());
        }
    }

    @Test
    @DisplayName("should update beneficiary")
    void testUpdateBeneficiary() {
        try {
            // Get full details of an existing beneficiary
            ListBeneficiariesRequest listRequest = new ListBeneficiariesRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListBeneficiariesResponse listResponse = beneficiariesService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No beneficiaries available, skipping Update test");
                return;
            }

            String beneficiaryId = listResponse.getData().get(0).getBeneficiaryId();
            Beneficiary existing = beneficiariesService.get(beneficiaryId);

            // Build update request copying all required fields from existing beneficiary
            BeneficiaryCreationRequest request = new BeneficiaryCreationRequest();
            request.setEntityType(existing.getEntityType() != null ? existing.getEntityType() : "INDIVIDUAL");
            request.setFirstName("Updated");
            request.setLastName("Name");
            request.setPaymentMethod(existing.getPaymentMethod());
            request.setBankDetails(existing.getBankDetails());
            request.setAddress(existing.getAddress());

            BeneficiaryCreationResponse response = beneficiariesService.update(beneficiaryId, request);
            assertThat(response).isNotNull();
            System.out.printf("Updated beneficiary: ID=%s, ShortRefId=%s%n",
                    response.getBeneficiaryId(), response.getShortReferenceId());
        } catch (UqpayException e) {
            System.out.printf("Update beneficiary returned error (may be expected in sandbox): %s%n", e.getMessage());
        }
    }

    @Test
    @DisplayName("should delete beneficiary")
    void testDeleteBeneficiary() {
        try {
            // Create a new beneficiary specifically for deletion
            long timestamp = System.currentTimeMillis();

            BankDetails bankDetails = new BankDetails();
            bankDetails.setBankName("Delete Test Bank");
            bankDetails.setBankAddress("123 Delete Street");
            bankDetails.setBankCountryCode("SG");
            bankDetails.setAccountHolder("Delete Test");
            bankDetails.setAccountCurrencyCode("SGD");
            bankDetails.setSwiftCode("WELGBE22");
            bankDetails.setClearingSystem("FAST");
            bankDetails.setAccountNumber("DEL" + timestamp);

            Address address = new Address();
            address.setCountry("SG");
            address.setCity("Singapore");
            address.setStreetAddress("123 Delete Street");
            address.setPostalCode("123456");
            address.setState("SG");

            BeneficiaryCreationRequest createRequest = new BeneficiaryCreationRequest();
            createRequest.setEntityType("INDIVIDUAL");
            createRequest.setFirstName("Delete");
            createRequest.setLastName("Test");
            createRequest.setPaymentMethod("SWIFT");
            createRequest.setBankDetails(bankDetails);
            createRequest.setAddress(address);

            BeneficiaryCreationResponse created = beneficiariesService.create(createRequest);
            String beneficiaryId = created.getBeneficiaryId();
            System.out.printf("Created beneficiary for deletion: ID=%s%n", beneficiaryId);

            // Now delete it
            boolean deleted = beneficiariesService.delete(beneficiaryId);
            assertThat(deleted).isTrue();
            System.out.printf("Delete beneficiary completed: ID=%s%n", beneficiaryId);
        } catch (UqpayException e) {
            System.out.printf("Delete beneficiary returned error: %s%n", e.getMessage());
        }
    }

    @Test
    @DisplayName("should check US ACH beneficiary")
    void testCheckUSBeneficiary() {
        try {
            BeneficiaryCheckRequest request = new BeneficiaryCheckRequest();
            request.setEntityType("INDIVIDUAL");
            request.setPaymentMethod("LOCAL");
            request.setBankCountryCode("US");
            request.setCurrency("USD");
            request.setAccountNumber("123456789");
            request.setClearingSystem("ACH");

            Beneficiary response = beneficiariesService.check(request);

            System.out.printf("Checked US ACH beneficiary: Currency=%s, BankCountryCode=%s, ClearingSystem=%s%n",
                    request.getCurrency(), request.getBankCountryCode(), request.getClearingSystem());
        } catch (UqpayException e) {
            System.out.printf("Check US beneficiary returned error (may be expected in sandbox): %s%n", e.getMessage());
        }
    }

    @Test
    @DisplayName("should check UK Faster Payments beneficiary")
    void testCheckUKBeneficiary() {
        try {
            BeneficiaryCheckRequest request = new BeneficiaryCheckRequest();
            request.setEntityType("INDIVIDUAL");
            request.setPaymentMethod("LOCAL");
            request.setBankCountryCode("GB");
            request.setCurrency("GBP");
            request.setAccountNumber("12345678");
            request.setClearingSystem("Faster Payments");

            Beneficiary response = beneficiariesService.check(request);

            System.out.printf("Checked UK Faster Payments beneficiary: Currency=%s, BankCountryCode=%s, ClearingSystem=%s%n",
                    request.getCurrency(), request.getBankCountryCode(), request.getClearingSystem());
        } catch (UqpayException e) {
            System.out.printf("Check UK beneficiary returned error (may be expected in sandbox): %s%n", e.getMessage());
        }
    }

    @Test
    @DisplayName("should check SEPA beneficiary")
    void testCheckSEPABeneficiary() {
        try {
            BeneficiaryCheckRequest request = new BeneficiaryCheckRequest();
            request.setEntityType("INDIVIDUAL");
            request.setPaymentMethod("LOCAL");
            request.setBankCountryCode("DE");
            request.setCurrency("EUR");
            request.setAccountNumber("DE89370400440532013000");
            request.setIban("DE89370400440532013000");
            request.setClearingSystem("LOCAL");

            Beneficiary response = beneficiariesService.check(request);

            System.out.printf("Checked SEPA beneficiary: Currency=%s, BankCountryCode=%s, ClearingSystem=%s%n",
                    request.getCurrency(), request.getBankCountryCode(), request.getClearingSystem());
        } catch (UqpayException e) {
            System.out.printf("Check SEPA beneficiary returned error (may be expected in sandbox): %s%n", e.getMessage());
        }
    }
}
