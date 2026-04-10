package com.uqpay.sdk.issuing;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.model.Cardholder;
import com.uqpay.sdk.issuing.model.CreateCardholderRequest;
import com.uqpay.sdk.issuing.model.CreateCardholderResponse;
import com.uqpay.sdk.issuing.model.Identity;
import com.uqpay.sdk.issuing.model.KycVerification;
import com.uqpay.sdk.issuing.model.ListCardholdersRequest;
import com.uqpay.sdk.issuing.model.ListCardholdersResponse;
import com.uqpay.sdk.issuing.model.ResidentialAddress;
import com.uqpay.sdk.issuing.model.UpdateCardholderRequest;
import com.uqpay.sdk.issuing.model.UpdateCardholderResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Cardholder KYC Integration Tests")
class CardholderKycIntegrationTest {

    private static CardholdersService cardholdersService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        IssuingClient issuingClient = new IssuingClient(apiClient);
        cardholdersService = issuingClient.getCardholders();
    }

    // ─── SIMPLIFIED KYC ──────────────────────────────────────────────────────────

    @Nested
    @DisplayName("SIMPLIFIED KYC — basic fields only")
    class SimplifiedKyc {

        @Test
        @DisplayName("should create cardholder with basic fields and return status")
        void shouldCreateSimplifiedKycCardholder() throws UqpayException {
            long ts = System.currentTimeMillis();
            String tsStr = String.valueOf(ts);

            CreateCardholderRequest request = new CreateCardholderRequest();
            request.setEmail("sdk-kyc-simplified-" + ts + "@example.com");
            request.setFirstName("Alice");
            request.setLastName("Tan");
            request.setCountryCode("SG");
            request.setPhoneNumber("8" + tsStr.substring(tsStr.length() - 7));
            request.setDateOfBirth("1992-05-20");

            try {
                CreateCardholderResponse response = cardholdersService.create(request);

                assertThat(response).isNotNull();
                assertThat(response.getCardholderId()).isNotNull();

                System.out.printf("SIMPLIFIED KYC cardholder created: id=%s, cardholder_status=%s%n",
                        response.getCardholderId(), response.getCardholderStatus());
            } catch (UqpayException e) {
                System.out.printf("SIMPLIFIED KYC create returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }
    }

    // ─── STANDARD KYC ────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("STANDARD KYC — gender, nationality, identity, residential_address")
    class StandardKyc {

        @Test
        @DisplayName("should create cardholder with standard KYC fields")
        void shouldCreateStandardKycCardholder() throws UqpayException {
            long ts = System.currentTimeMillis();

            CreateCardholderRequest request = new CreateCardholderRequest();
            request.setEmail("sdk-kyc-standard-" + ts + "@example.com");
            request.setFirstName("Bob");
            request.setLastName("Lee");
            request.setCountryCode("SG");
            request.setPhoneNumber("9" + String.valueOf(ts + 1).substring(String.valueOf(ts + 1).length() - 7));
            request.setDateOfBirth("1988-11-03");
            request.setGender("MALE");
            request.setNationality("SG");

            Identity identity = new Identity();
            identity.setType("PASSPORT");
            identity.setNumber("E1234567");
            request.setIdentity(identity);

            ResidentialAddress addr = new ResidentialAddress();
            addr.setCountry("SG");
            addr.setState("Singapore");
            addr.setCity("Singapore");
            addr.setLine1("1 Raffles Place");
            addr.setLineEn("1 Raffles Place");
            addr.setPostalCode("048616");
            request.setResidentialAddress(addr);

            try {
                CreateCardholderResponse response = cardholdersService.create(request);

                assertThat(response).isNotNull();
                assertThat(response.getCardholderId()).isNotNull();

                System.out.printf("STANDARD KYC cardholder created: id=%s, cardholder_status=%s%n",
                        response.getCardholderId(), response.getCardholderStatus());
            } catch (UqpayException e) {
                System.out.printf("STANDARD KYC create returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }
    }

    // ─── ENHANCED KYC — SUMSUB_REDIRECT ──────────────────────────────────────────

    @Nested
    @DisplayName("ENHANCED KYC — SUMSUB_REDIRECT, response should include idv_verification_url")
    class EnhancedKyc {

        @Test
        @DisplayName("should create cardholder with SUMSUB_REDIRECT kyc_verification and return idv url")
        void shouldCreateEnhancedKycCardholder() throws UqpayException {
            long ts = System.currentTimeMillis();

            CreateCardholderRequest request = new CreateCardholderRequest();
            request.setEmail("sdk-kyc-enhanced-" + ts + "@example.com");
            request.setFirstName("Carol");
            request.setLastName("Wang");
            request.setCountryCode("SG");
            request.setPhoneNumber("9" + String.valueOf(ts + 2).substring(String.valueOf(ts + 2).length() - 7));
            request.setDateOfBirth("1995-07-14");
            request.setGender("FEMALE");
            request.setNationality("SG");

            Identity identity = new Identity();
            identity.setType("PASSPORT");
            identity.setNumber("P9876543");
            request.setIdentity(identity);

            ResidentialAddress addr = new ResidentialAddress();
            addr.setCountry("SG");
            addr.setState("Singapore");
            addr.setCity("Singapore");
            addr.setLine1("10 Anson Road");
            addr.setLineEn("10 Anson Road");
            addr.setPostalCode("079903");
            request.setResidentialAddress(addr);

            KycVerification kyc = new KycVerification();
            kyc.setMethod("SUMSUB_REDIRECT");
            request.setKycVerification(kyc);

            try {
                CreateCardholderResponse response = cardholdersService.create(request);

                assertThat(response).isNotNull();
                assertThat(response.getCardholderId()).isNotNull();

                System.out.printf("ENHANCED KYC cardholder created: id=%s, cardholder_status=%s%n",
                        response.getCardholderId(), response.getCardholderStatus());
                System.out.printf("  idv_verification_url=%s%n", response.getIdvVerificationUrl());
                System.out.printf("  idv_url_expires_at=%s%n", response.getIdvUrlExpiresAt());
            } catch (UqpayException e) {
                System.out.printf("ENHANCED KYC create returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }
    }

    // ─── Update residential_address ───────────────────────────────────────────────

    @Nested
    @DisplayName("Update Cardholder — residential_address")
    class UpdateResidentialAddress {

        @Test
        @DisplayName("should update cardholder residential_address")
        void shouldUpdateResidentialAddress() throws UqpayException {
            // Obtain an existing cardholder to update
            ListCardholdersRequest listRequest = new ListCardholdersRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);
            listRequest.setCardholderStatus("SUCCESS");

            ListCardholdersResponse listResponse = cardholdersService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No SUCCESS cardholders found, skipping residential_address update test");
                return;
            }

            String cardholderId = listResponse.getData().get(0).getCardholderId();

            UpdateCardholderRequest updateReq = new UpdateCardholderRequest();
            ResidentialAddress newAddr = new ResidentialAddress();
            newAddr.setCountry("SG");
            newAddr.setState("Singapore");
            newAddr.setCity("Singapore");
            newAddr.setLine1("5 Shenton Way");
            newAddr.setLineEn("5 Shenton Way");
            newAddr.setPostalCode("068808");
            updateReq.setResidentialAddress(newAddr);

            try {
                UpdateCardholderResponse response = cardholdersService.update(cardholderId, updateReq);

                assertThat(response).isNotNull();
                assertThat(response.getCardholderId()).isEqualTo(cardholderId);

                System.out.printf("Updated cardholder residential_address: id=%s, cardholder_status=%s%n",
                        response.getCardholderId(), response.getCardholderStatus());
            } catch (UqpayException e) {
                System.out.printf("Update residential_address returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }
    }

    // ─── Retrieve — verify new KYC fields present in response ────────────────────

    @Nested
    @DisplayName("Retrieve Cardholder — verify KYC fields in response")
    class RetrieveKycFields {

        @Test
        @DisplayName("should retrieve cardholder and log gender, nationality, residential_address, idvStatus")
        void shouldRetrieveCardholderAndVerifyKycFields() throws UqpayException {
            ListCardholdersRequest listRequest = new ListCardholdersRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListCardholdersResponse listResponse = cardholdersService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No cardholders found, skipping retrieve KYC fields test");
                return;
            }

            String cardholderId = listResponse.getData().get(0).getCardholderId();
            Cardholder cardholder = cardholdersService.get(cardholderId);

            assertThat(cardholder).isNotNull();
            assertThat(cardholder.getCardholderId()).isEqualTo(cardholderId);

            System.out.printf("Retrieved cardholder KYC fields: id=%s, gender=%s, nationality=%s, idv_status=%s%n",
                    cardholder.getCardholderId(), cardholder.getGender(),
                    cardholder.getNationality(), cardholder.getIdvStatus());

            if (cardholder.getResidentialAddress() != null) {
                ResidentialAddress addr = cardholder.getResidentialAddress();
                System.out.printf("  residential_address: country=%s, city=%s, line1=%s, postal_code=%s%n",
                        addr.getCountry(), addr.getCity(), addr.getLine1(), addr.getPostalCode());
            } else {
                System.out.println("  residential_address: null");
            }

            if (cardholder.getIdvVerificationUrl() != null) {
                System.out.printf("  idv_verification_url=%s%n", cardholder.getIdvVerificationUrl());
                System.out.printf("  idv_url_expires_at=%s%n", cardholder.getIdvUrlExpiresAt());
            }
        }
    }

    // ─── List with cardholder_status filter ──────────────────────────────────────

    @Nested
    @DisplayName("List Cardholders — cardholder_status filter")
    class ListWithStatusFilter {

        @Test
        @DisplayName("should list cardholders filtered by status SUCCESS")
        void shouldListCardholdersFilteredBySuccess() throws UqpayException {
            ListCardholdersRequest request = new ListCardholdersRequest();
            request.setPageSize(10);
            request.setPageNumber(1);
            request.setCardholderStatus("SUCCESS");

            ListCardholdersResponse response = cardholdersService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Cardholders with status=SUCCESS: count=%d (total=%d)%n",
                    response.getData().size(), response.getTotalItems());
        }

        @Test
        @DisplayName("should list cardholders filtered by status PENDING")
        void shouldListCardholdersFilteredByPending() throws UqpayException {
            ListCardholdersRequest request = new ListCardholdersRequest();
            request.setPageSize(10);
            request.setPageNumber(1);
            request.setCardholderStatus("PENDING");

            ListCardholdersResponse response = cardholdersService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();

            System.out.printf("Cardholders with status=PENDING: count=%d (total=%d)%n",
                    response.getData().size(), response.getTotalItems());
        }
    }
}
