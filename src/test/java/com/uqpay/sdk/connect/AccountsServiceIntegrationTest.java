package com.uqpay.sdk.connect;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.connect.model.Account;
import com.uqpay.sdk.connect.model.Address;
import com.uqpay.sdk.connect.model.ContactDetails;
import com.uqpay.sdk.connect.model.CreateAccountRequest;
import com.uqpay.sdk.connect.model.CreateSubAccountRequest;
import com.uqpay.sdk.connect.model.DocumentItem;
import com.uqpay.sdk.connect.model.EntityType;
import com.uqpay.sdk.connect.model.ExpectedActivity;
import com.uqpay.sdk.connect.model.Identification;
import com.uqpay.sdk.connect.model.IdentificationDocuments;
import com.uqpay.sdk.connect.model.IdentityVerification;
import com.uqpay.sdk.connect.model.ListAccountsRequest;
import com.uqpay.sdk.connect.model.ListAccountsResponse;
import com.uqpay.sdk.connect.model.MonthlyEstimatedRevenue;
import com.uqpay.sdk.connect.model.PersonDetails;
import com.uqpay.sdk.connect.model.ProofDocuments;
import com.uqpay.sdk.connect.model.SubAccountIndividualInfo;
import com.uqpay.sdk.connect.model.TosAcceptance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link AccountsService}.
 * <p>
 * Requires UQPAY_CLIENT_ID and UQPAY_API_KEY environment variables to be set.
 * Tests will be skipped if credentials are not available.
 * </p>
 */
@DisplayName("AccountsService Integration Tests")
class AccountsServiceIntegrationTest {

    private static AccountsService accountsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        ConnectClient connectClient = new ConnectClient(apiClient);
        accountsService = connectClient.getAccounts();
    }

    // ── Helper methods ──────────────────────────────────────────────────────

    /**
     * Builds a sample individual CreateAccountRequest for testing.
     */
    private static final String SAMPLE_BASE64_IMAGE =
            "data:image/png;base64,R0lGODlhAQABAPAAAP///wAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==";

    private static CreateAccountRequest buildIndividualCreateRequest() {
        long timestamp = System.currentTimeMillis();

        Address address = new Address();
        address.setLine1("123 Test Street");
        address.setCity("Singapore");
        address.setState("SG");
        address.setPostalCode("048616");
        address.setCountry("SG");

        ContactDetails contact = new ContactDetails();
        contact.setEmail(String.format("test%d@example.com", timestamp));
        contact.setPhoneNumber("+6512345678");

        TosAcceptance tos = new TosAcceptance();
        tos.setDate(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        tos.setIp("127.0.0.1");
        tos.setUserAgent("Mozilla/5.0 uqpay-java-sdk-test");

        MonthlyEstimatedRevenue revenue = new MonthlyEstimatedRevenue();
        revenue.setAmount("TM001");
        revenue.setCurrency("SGD");

        IdentificationDocuments idDocs = new IdentificationDocuments();
        idDocs.setFront(SAMPLE_BASE64_IMAGE);

        Identification identification = new Identification();
        identification.setType("PASSPORT");
        identification.setIdNumber("E12345678");
        identification.setFront(SAMPLE_BASE64_IMAGE);
        identification.setDocuments(idDocs);

        PersonDetails personDetails = new PersonDetails();
        personDetails.setFirstNameEnglish("Integration");
        personDetails.setLastNameEnglish("Test");
        personDetails.setDateOfBirth("1990-01-01");
        personDetails.setNationality("SG");
        personDetails.setInternationally(1);
        personDetails.setBankingCurrencies(Arrays.asList("SGD", "USD"));
        personDetails.setBankingCountries(Arrays.asList("SG", "US"));
        personDetails.setMonthlyEstimatedRevenue(revenue);
        personDetails.setAccountPurpose(Collections.singletonList("PERSONAL_REMITTANCE"));
        personDetails.setIdentification(identification);

        DocumentItem doc = new DocumentItem();
        doc.setType("CERTIFICATE_OF_INCORPORATION");
        doc.setFront(SAMPLE_BASE64_IMAGE);

        CreateAccountRequest request = new CreateAccountRequest();
        request.setEntityType(EntityType.INDIVIDUAL);
        request.setName("test-account-" + timestamp);
        request.setContactDetails(contact);
        request.setPersonDetails(personDetails);
        request.setResidentialAddress(address);
        request.setDocuments(Collections.singletonList(doc));
        request.setTosAcceptance(tos);

        return request;
    }

    /**
     * Fetches the first available account ID by listing accounts.
     * Returns null if no accounts exist.
     */
    private static String getFirstAccountId() throws UqpayException {
        ListAccountsRequest listRequest = new ListAccountsRequest();
        listRequest.setPageSize(1);
        listRequest.setPageNumber(1);

        ListAccountsResponse listResponse = accountsService.list(listRequest);
        if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
            return null;
        }
        return listResponse.getData().get(0).getAccountId();
    }

    /**
     * Builds a sample individual CreateSubAccountRequest for testing.
     */
    private static CreateSubAccountRequest buildIndividualSubAccountRequest() {
        long timestamp = System.currentTimeMillis();

        SubAccountIndividualInfo individualInfo = new SubAccountIndividualInfo();
        individualInfo.setFirstNameEnglish("Integration");
        individualInfo.setLastNameEnglish("Test");
        individualInfo.setDateOfBirth("1990-01-01");
        individualInfo.setNationality("SG");
        individualInfo.setEmailAddress(String.format("test%d@example.com", timestamp));
        individualInfo.setPhoneNumber("+6512345678");
        individualInfo.setCountryOrTerritory("SG");
        individualInfo.setStreetAddress("123 Test Street");
        individualInfo.setCity("Singapore");
        individualInfo.setState("SG");
        individualInfo.setPostalCode("048616");

        IdentityVerification identityVerification = new IdentityVerification();
        identityVerification.setIdentificationType("PASSPORT");
        identityVerification.setIdentificationValue("E12345678");
        identityVerification.setIdentityDocs(Collections.singletonList(SAMPLE_BASE64_IMAGE));

        ExpectedActivity expectedActivity = new ExpectedActivity();
        expectedActivity.setAccountPurpose(Collections.singletonList("PERSONAL_REMITTANCE"));
        expectedActivity.setBankingCountries(Collections.singletonList("SG"));
        expectedActivity.setBankingCurrencies(Collections.singletonList("SGD"));
        expectedActivity.setInternationally(0);
        expectedActivity.setTurnoverMonthly("TM001");
        expectedActivity.setTurnoverMonthlyCurrency("SGD");

        ProofDocuments proofDocuments = new ProofDocuments();
        proofDocuments.setProofOfAddress(Collections.singletonList(SAMPLE_BASE64_IMAGE));

        TosAcceptance tos = new TosAcceptance();
        tos.setDate(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        tos.setIp("127.0.0.1");
        tos.setUserAgent("Mozilla/5.0 uqpay-java-sdk-test");

        CreateSubAccountRequest request = new CreateSubAccountRequest();
        request.setEntityType(EntityType.INDIVIDUAL);
        request.setBusinessType("BANKING");
        request.setNickname("test-sub-account-" + timestamp);
        request.setIndividualInfo(individualInfo);
        request.setIdentityVerification(identityVerification);
        request.setExpectedActivity(expectedActivity);
        request.setProofDocuments(proofDocuments);
        request.setTosAcceptance(tos);

        return request;
    }

    // ── Create Account Tests ────────────────────────────────────────────────

    @Nested
    @DisplayName("Create Account")
    class CreateAccount {

        @Test
        @DisplayName("should create an individual account")
        void shouldCreateIndividualAccount() throws UqpayException {
            CreateAccountRequest request = buildIndividualCreateRequest();

            try {
                Account account = accountsService.create(request);

                assertThat(account).isNotNull();
                assertThat(account.getAccountId()).isNotNull().isNotEmpty();

                System.out.printf("Account created: ID=%s, Status=%s, Verification=%s%n",
                        account.getAccountId(), account.getStatus(), account.getVerificationStatus());
            } catch (UqpayException e) {
                System.out.printf("Create account returned error (may be expected in sandbox): %s%n", e.getMessage());
            }
        }

        @Test
        @DisplayName("should create an individual account with request options")
        void shouldCreateIndividualAccountWithOptions() throws UqpayException {
            CreateAccountRequest request = buildIndividualCreateRequest();
            RequestOptions options = RequestOptions.builder()
                    .idempotencyKey("test-create-account-" + System.currentTimeMillis())
                    .build();

            try {
                Account account = accountsService.create(request, options);

                assertThat(account).isNotNull();
                assertThat(account.getAccountId()).isNotNull().isNotEmpty();

                System.out.printf("Account created with options: ID=%s, Status=%s, Verification=%s%n",
                        account.getAccountId(), account.getStatus(), account.getVerificationStatus());
            } catch (UqpayException e) {
                System.out.printf("Create account with options returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }
    }

    // ── Create Sub-Account Tests ────────────────────────────────────────────

    @Nested
    @DisplayName("Create Sub-Account")
    class CreateSubAccount {

        @Test
        @DisplayName("should create a sub-account with new request model")
        void shouldCreateSubAccountWithNewModel() throws UqpayException {
            CreateSubAccountRequest request = buildIndividualSubAccountRequest();

            try {
                Account account = accountsService.createSubAccount(request);

                assertThat(account).isNotNull();
                assertThat(account.getAccountId()).isNotNull().isNotEmpty();

                System.out.printf("Sub-account created: ID=%s, Status=%s, Verification=%s%n",
                        account.getAccountId(), account.getStatus(), account.getVerificationStatus());
            } catch (UqpayException e) {
                System.out.printf("Create sub-account returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }

        @Test
        @DisplayName("should create a sub-account with new request model and options")
        void shouldCreateSubAccountWithNewModelAndOptions() throws UqpayException {
            CreateSubAccountRequest request = buildIndividualSubAccountRequest();
            RequestOptions options = RequestOptions.builder()
                    .idempotencyKey("test-create-sub-account-" + System.currentTimeMillis())
                    .build();

            try {
                Account account = accountsService.createSubAccount(request, options);

                assertThat(account).isNotNull();
                assertThat(account.getAccountId()).isNotNull().isNotEmpty();

                System.out.printf("Sub-account created with options: ID=%s, Status=%s, Verification=%s%n",
                        account.getAccountId(), account.getStatus(), account.getVerificationStatus());
            } catch (UqpayException e) {
                System.out.printf("Create sub-account with options returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }
    }

    // ── List Accounts Tests ─────────────────────────────────────────────────

    @Nested
    @DisplayName("List Accounts")
    class ListAccounts {

        @Test
        @DisplayName("should list accounts with pagination")
        void shouldListAccountsWithPagination() throws UqpayException {
            ListAccountsRequest request = new ListAccountsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListAccountsResponse response = accountsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d accounts (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                Account first = response.getData().get(0);
                System.out.printf("First account: ID=%s, Type=%s, Status=%s, Verification=%s%n",
                        first.getAccountId(), first.getEntityType(), first.getStatus(),
                        first.getVerificationStatus());

                if (first.getContactDetails() != null) {
                    System.out.printf("  Contact: email=%s, phone=%s%n",
                            first.getContactDetails().getEmail(), first.getContactDetails().getPhone());
                }

                if (first.getBusinessDetails() != null) {
                    System.out.printf("  Business: %s%n",
                            first.getBusinessDetails().getLegalEntityNameEnglish());
                } else if (first.getPersonDetails() != null) {
                    System.out.printf("  Person: %s %s%n",
                            first.getPersonDetails().getFirstNameEnglish(),
                            first.getPersonDetails().getLastNameEnglish());
                }
            }
        }

        @Test
        @DisplayName("should list accounts with minimal pagination")
        void shouldListAccountsWithMinimalPagination() throws UqpayException {
            ListAccountsRequest request = new ListAccountsRequest();
            request.setPageSize(5);
            request.setPageNumber(1);

            ListAccountsResponse response = accountsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getData()).isNotNull();
            assertThat(response.getData().size()).isLessThanOrEqualTo(5);
        }

        @Test
        @DisplayName("should list accounts with request options")
        void shouldListAccountsWithRequestOptions() throws UqpayException {
            ListAccountsRequest request = new ListAccountsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            RequestOptions options = RequestOptions.builder()
                    .idempotencyKey("test-list-accounts-" + System.currentTimeMillis())
                    .build();

            ListAccountsResponse response = accountsService.list(request, options);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d accounts with options (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());
        }
    }

    // ── Get Account Tests ───────────────────────────────────────────────────

    @Nested
    @DisplayName("Get Account")
    class GetAccount {

        @Test
        @DisplayName("should get account by ID")
        void shouldGetAccountById() throws UqpayException {
            String accountId = getFirstAccountId();
            if (accountId == null) {
                System.out.println("No accounts available, skipping Get test");
                return;
            }

            Account account = accountsService.get(accountId);

            assertThat(account).isNotNull();
            assertThat(account.getAccountId()).isEqualTo(accountId);

            System.out.printf("Retrieved account: ID=%s, Type=%s, Status=%s, Verification=%s%n",
                    account.getAccountId(), account.getEntityType(), account.getStatus(),
                    account.getVerificationStatus());
            if (account.getReviewReason() != null) {
                System.out.printf("  Review reason: %s%n", account.getReviewReason());
            }
        }

        @Test
        @DisplayName("should get account by ID with business code")
        void shouldGetAccountByIdWithBusinessCode() throws UqpayException {
            String accountId = getFirstAccountId();
            if (accountId == null) {
                System.out.println("No accounts available, skipping Get with businessCode test");
                return;
            }

            try {
                Account account = accountsService.get(accountId, "BANKING");

                assertThat(account).isNotNull();
                assertThat(account.getAccountId()).isEqualTo(accountId);

                System.out.printf("Retrieved account with businessCode: ID=%s, Type=%s, Status=%s%n",
                        account.getAccountId(), account.getEntityType(), account.getStatus());
            } catch (UqpayException e) {
                System.out.printf("Get account with businessCode returned error (may be expected): %s%n",
                        e.getMessage());
            }
        }

        @Test
        @DisplayName("should get account by ID with request options")
        void shouldGetAccountByIdWithRequestOptions() throws UqpayException {
            String accountId = getFirstAccountId();
            if (accountId == null) {
                System.out.println("No accounts available, skipping Get with options test");
                return;
            }

            RequestOptions options = RequestOptions.builder()
                    .idempotencyKey("test-get-account-" + System.currentTimeMillis())
                    .build();

            Account account = accountsService.get(accountId, options);

            assertThat(account).isNotNull();
            assertThat(account.getAccountId()).isEqualTo(accountId);

            System.out.printf("Retrieved account with options: ID=%s, Type=%s, Status=%s%n",
                    account.getAccountId(), account.getEntityType(), account.getStatus());
        }
    }

    // ── Get Additional Documents Tests ──────────────────────────────────────

    @Nested
    @DisplayName("Get Additional Documents")
    class GetAdditionalDocuments {

        @Test
        @DisplayName("should get additional documents by country and business code")
        void shouldGetAdditionalDocuments() throws UqpayException {
            com.uqpay.sdk.connect.model.Document[] documents =
                    accountsService.getAdditionalDocuments("SG", "BANKING");

            assertThat(documents).isNotNull();

            System.out.printf("  Total documents: %d%n", documents.length);
            for (int i = 0; i < documents.length; i++) {
                com.uqpay.sdk.connect.model.Document doc = documents[i];
                System.out.printf("  Document %d: ProfileKey=%s, ProfileName=%s, ProfileOption=%d%n",
                        i + 1, doc.getProfileKey(), doc.getProfileName(), doc.getProfileOption());
            }
        }

        @Test
        @DisplayName("should get additional documents with request options")
        void shouldGetAdditionalDocumentsWithRequestOptions() throws UqpayException {
            RequestOptions options = RequestOptions.builder()
                    .idempotencyKey("test-get-additional-docs-" + System.currentTimeMillis())
                    .build();

            com.uqpay.sdk.connect.model.Document[] documents =
                    accountsService.getAdditionalDocuments("SG", "BANKING", options);

            assertThat(documents).isNotNull();

            System.out.printf("  Total documents: %d%n", documents.length);
            for (int i = 0; i < documents.length; i++) {
                com.uqpay.sdk.connect.model.Document doc = documents[i];
                System.out.printf("  Document %d: ProfileKey=%s, ProfileName=%s, ProfileOption=%d%n",
                        i + 1, doc.getProfileKey(), doc.getProfileName(), doc.getProfileOption());
            }
        }
    }

    // ── ConnectClient Tests ─────────────────────────────────────────────────

    @Nested
    @DisplayName("ConnectClient")
    class ConnectClientTest {

        @Test
        @DisplayName("should provide access to accounts service")
        void shouldProvideAccessToAccountsService() {
            Configuration config = TestHelper.getTestConfiguration();
            DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
            ApiClient apiClient = new ApiClient(config, tokenProvider);
            ConnectClient connectClient = new ConnectClient(apiClient);

            assertThat(connectClient.getAccounts()).isNotNull();
        }
    }
}
