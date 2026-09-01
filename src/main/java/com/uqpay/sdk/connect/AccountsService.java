package com.uqpay.sdk.connect;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.connect.model.Account;
import com.uqpay.sdk.connect.model.CreateAccountRequest;
import com.uqpay.sdk.connect.model.CreateSubAccountRequest;
import com.uqpay.sdk.connect.model.Document;
import com.uqpay.sdk.connect.model.EntityType;
import com.uqpay.sdk.connect.model.ListAccountsRequest;
import com.uqpay.sdk.connect.model.ListAccountsResponse;
import com.uqpay.sdk.connect.model.SubAccountBusinessDetails;
import com.uqpay.sdk.connect.model.SubAccountRepresentative;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Service for managing Connect accounts.
 * <p>
 * Provides methods for creating, retrieving, listing, and updating accounts,
 * as well as fetching additional document requirements.
 * </p>
 */
public final class AccountsService {

    private final ApiClient apiClient;

    /**
     * Creates a new AccountsService.
     *
     * @param apiClient the API client for making HTTP requests
     */
    public AccountsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    /**
     * Creates a new account using the legacy API endpoint.
     *
     * @param request the account creation request
     * @return the created account
     * @throws UqpayException       if the request fails
     * @throws IllegalArgumentException if the request violates the entity type contract
     * @deprecated Use {@link #createSubAccount(CreateSubAccountRequest)} instead
     */
    @Deprecated
    @NotNull
    public Account create(@NotNull CreateAccountRequest request) throws UqpayException {
        return create(request, null);
    }

    /**
     * Creates a new account using the legacy API endpoint with custom request options.
     *
     * @param request the account creation request
     * @param options the request options (optional)
     * @return the created account
     * @throws UqpayException       if the request fails
     * @throws IllegalArgumentException if the request violates the entity type contract
     * @deprecated Use {@link #createSubAccount(CreateSubAccountRequest, RequestOptions)} instead
     */
    @Deprecated
    @NotNull
    public Account create(@NotNull CreateAccountRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        validateCreateRequest(request);
        return apiClient.post("/v1/accounts", request, Account.class, options);
    }

    /**
     * Creates a new sub-account using the new API endpoint.
     *
     * @param request the sub-account creation request
     * @return the created sub-account
     * @throws UqpayException       if the request fails
     * @throws IllegalArgumentException if the request violates the entity type contract
     */
    @NotNull
    public Account createSubAccount(@NotNull CreateSubAccountRequest request) throws UqpayException {
        return createSubAccount(request, null);
    }

    /**
     * Creates a new sub-account using the new API endpoint with custom request options.
     *
     * @param request the sub-account creation request
     * @param options the request options (optional)
     * @return the created sub-account
     * @throws UqpayException       if the request fails
     * @throws IllegalArgumentException if the request violates the entity type contract
     */
    @NotNull
    public Account createSubAccount(@NotNull CreateSubAccountRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        validateSubAccountRequest(request);
        return apiClient.post("/v1/accounts/create_accounts", request, Account.class, options);
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param accountId the account ID
     * @return the account
     * @throws UqpayException if the request fails
     */
    @NotNull
    public Account get(@NotNull String accountId) throws UqpayException {
        return get(accountId, (RequestOptions) null);
    }

    /**
     * Retrieves an account by its ID with custom request options.
     *
     * @param accountId the account ID
     * @param options   the request options (optional)
     * @return the account
     * @throws UqpayException if the request fails
     */
    @NotNull
    public Account get(@NotNull String accountId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(accountId, "accountId must not be null");
        if (accountId.isEmpty()) {
            throw new IllegalArgumentException("accountId must not be empty");
        }
        return apiClient.get("/v1/accounts/" + accountId, Account.class, options);
    }

    /**
     * Retrieves an account by its ID with an optional business code filter.
     *
     * @param accountId    the account ID
     * @param businessCode the business code to filter by (BANKING, ACQUIRING, or ISSUING)
     * @return the account
     * @throws UqpayException if the request fails
     */
    @NotNull
    public Account get(@NotNull String accountId, @NotNull String businessCode) throws UqpayException {
        return get(accountId, businessCode, null);
    }

    /**
     * Retrieves an account by its ID with an optional business code filter and custom request options.
     *
     * @param accountId    the account ID
     * @param businessCode the business code to filter by (BANKING, ACQUIRING, or ISSUING)
     * @param options      the request options (optional)
     * @return the account
     * @throws UqpayException if the request fails
     */
    @NotNull
    public Account get(@NotNull String accountId, @NotNull String businessCode,
                       @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(accountId, "accountId must not be null");
        if (accountId.isEmpty()) {
            throw new IllegalArgumentException("accountId must not be empty");
        }
        Objects.requireNonNull(businessCode, "businessCode must not be null");
        String query = "?business_code=" + businessCode;
        return apiClient.get("/v1/accounts/" + accountId + query, Account.class, options);
    }

    /**
     * Lists accounts with optional filters.
     *
     * @param request the list request with pagination options
     * @return the paginated list of accounts
     * @throws UqpayException if the request fails
     */
    @NotNull
    public ListAccountsResponse list(@NotNull ListAccountsRequest request) throws UqpayException {
        return list(request, null);
    }

    /**
     * Lists accounts with optional filters and custom request options.
     *
     * @param request the list request with pagination options
     * @param options the request options (optional)
     * @return the paginated list of accounts
     * @throws UqpayException if the request fails
     */
    @NotNull
    public ListAccountsResponse list(@NotNull ListAccountsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/accounts" + queryString, ListAccountsResponse.class, options);
    }

    /**
     * Retrieves additional required documents based on country and business code.
     *
     * @param country      the ISO 3166-1 alpha-2 country code (e.g., "SG", "US")
     * @param businessCode the business code (BANKING or ACQUIRING)
     * @return the additional documents as an array
     * @throws UqpayException if the request fails
     */
    @NotNull
    public Document[] getAdditionalDocuments(@NotNull String country,
                                              @NotNull String businessCode) throws UqpayException {
        return getAdditionalDocuments(country, businessCode, null);
    }

    /**
     * Retrieves additional required documents based on country and business code with custom request options.
     *
     * @param country      the ISO 3166-1 alpha-2 country code (e.g., "SG", "US")
     * @param businessCode the business code (BANKING or ACQUIRING)
     * @param options      the request options (optional)
     * @return the additional documents as an array
     * @throws UqpayException if the request fails
     */
    @NotNull
    public Document[] getAdditionalDocuments(@NotNull String country,
                                              @NotNull String businessCode,
                                              @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(country, "country must not be null");
        Objects.requireNonNull(businessCode, "businessCode must not be null");
        String query = "?country=" + country + "&business_code=" + businessCode;
        return apiClient.get("/v1/accounts/get_additional" + query,
                Document[].class, options);
    }

    private void validateSubAccountRequest(@NotNull CreateSubAccountRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        if (request.getEntityType() == null) {
            throw new IllegalArgumentException("entityType must not be null");
        }
        if (request.getEntityType() == EntityType.INDIVIDUAL && request.getIndividualInfo() == null) {
            throw new IllegalArgumentException(
                    "individual_info required for INDIVIDUAL entity type");
        }
        if (request.getEntityType() == EntityType.COMPANY
                && !Integer.valueOf(1).equals(request.getInherit())) {
            validateCompanySubAccountRequest(request);
        }
    }

    private void validateCompanySubAccountRequest(@NotNull CreateSubAccountRequest request) {
        requireCompanyField(request.getCompanyInfo(), "company_info");
        requireCompanyField(request.getCompanyAddress(), "company_address");
        requireCompanyField(request.getOwnershipDetails(), "ownership_details");
        requireCompanyField(request.getBusinessDetails(), "business_details");

        if (request.getOwnershipDetails().getRepresentatives() == null) {
            throw new IllegalArgumentException(
                    "ownership_details.representatives required for COMPANY entity type when inherit != 1");
        }
        for (int index = 0; index < request.getOwnershipDetails().getRepresentatives().size(); index++) {
            SubAccountRepresentative representative = request.getOwnershipDetails().getRepresentatives().get(index);
            String path = "ownership_details.representatives[" + index + "]";
            if (representative == null) {
                throw new IllegalArgumentException(
                        path + " must not be null for COMPANY entity type when inherit != 1");
            }
            requireCompanyField(representative.getEmailAddress(), path + ".email_address");
            requireCompanyField(representative.getOwnershipPercentage(), path + ".ownership_percentage");
            requireCompanyField(representative.getDateOfBirth(), path + ".date_of_birth");
        }

        SubAccountBusinessDetails businessDetails = request.getBusinessDetails();
        requireCompanyField(businessDetails.getAccountPurpose(), "business_details.account_purpose");
        requireCompanyField(businessDetails.getBankingCurrencies(), "business_details.banking_currencies");
        requireCompanyField(businessDetails.getBankingCountries(), "business_details.banking_countries");
        requireCompanyField(businessDetails.getArticlesOfAssociation(),
                "business_details.articles_of_association");
    }

    private void requireCompanyField(@Nullable Object value, @NotNull String fieldPath) {
        if (value == null) {
            throw new IllegalArgumentException(
                    fieldPath + " required for COMPANY entity type when inherit != 1");
        }
    }

    private void validateCreateRequest(@NotNull CreateAccountRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        if (request.getEntityType() == null) {
            throw new IllegalArgumentException("entityType must not be null");
        }
        if (request.getEntityType() == EntityType.INDIVIDUAL
                && request.getPersonDetails() == null) {
            throw new IllegalArgumentException(
                    "person_details required for INDIVIDUAL entity type");
        }
    }
}
