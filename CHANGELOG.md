# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [3.0.0]

This major release aligns Account Center Create SubAccount COMPANY requests
with the contract that takes effect in Production on 2026-09-17.

### Breaking

- For `entity_type=COMPANY` with `inherit=-1`, representatives now require
  `emailAddress`, `dateOfBirth`, and string-valued `ownershipPercentage`; use
  `"0"` when a representative has no ownership.
- `SubAccountBusinessDetails` now requires `accountPurpose`,
  `bankingCurrencies`, `bankingCountries`, and `articlesOfAssociation`.
- `accountPurpose` uses `SubAccountCompanyPurpose` and accepts only the eight
  company-purpose enum values introduced in this release.

See the [Account Center Changelog](https://developers.uqpay.com/changelog) for
the rollout timeline and migration details.

### Migration and distribution

- Download `uqpay-sdk-java-3.0.0.jar` from the GitHub `v3.0.0` Release, update
  affected COMPANY payloads, and validate them in Sandbox before the Production
  cutover. The artifact remains a thin JAR and is not available from Maven
  Central.

## [2.1.0]

### Changed

- Account Center Create SubAccount COMPANY requests may omit
  `ownership_details.representatives[].date_of_birth`. When supplied, the
  value remains an ISO `YYYY-MM-DD` date. INDIVIDUAL requests still require
  `individual_info.date_of_birth`.

## [2.0.0]

This major release replaces the previous Virtual Account Create and webhook
contracts with the Virtual Account application lifecycle contract. Existing
Virtual Account integrations must migrate before adopting this version.

### Fixed

- Restored the required `account_id` and `direct_id` correlation fields on
  `VirtualAccountEventData` for `V1.5.1`, `V1.5.2`, and `V1.6.0` Virtual Account application
  events. Typed parsing rejects events missing either field; generic raw event data remains
  available for retaining historical/retried payloads that predate them.

### Changed

- Webhook freshness validation accepts Webhook Hub's Unix-millisecond
  `x-wk-timestamp` while retaining Unix-second compatibility and signing the
  unmodified header value.
- Replaced the Virtual Account Create request/response contract with the application contract:
  required `country`, single-value `currency`, optional `LOCAL`/`SWIFT` method and nickname,
  continued `x-idempotency-key` and `x-on-behalf-of` request-option passthrough, and the complete
  application DTO.
- Added the required application-owner correlation fields to successful REST responses:
  `account_id` and string `direct_id` on Create/Retrieve details and List summaries. `direct_id`
  is `"0"` for main-account applications and the main account ID for connected-account applications.
- Virtual Account application webhooks now parse the same application DTO for
  `virtual.account.create`, `virtual.account.update`, and `virtual.account.closed`; consumers can
  reconcile out-of-order delivery with `application_id` and `public_version`.

### Added

- List and Retrieve Virtual Account Application operations and precise application summary,
  result, error, bank-detail, clearing-system, status, and payment-method models.
- Strict three-field Virtual Account API errors expose `type`, `code`, and `message`, including
  the intentional HTTP 400 missing/cross-account application contract.

The existing List Virtual Accounts operation remains available and distinct from List Applications.

### Breaking

- Existing Create Virtual Account callers must add `country`, replace a currency
  collection with one `currency`, and consume the application response instead
  of the previous request acknowledgement.
- Virtual Account webhook consumers must correlate by `application_id`, process
  complete application data, and use `public_version` for ordering.

### Migration and distribution

- Download `uqpay-sdk-java-2.0.0.jar` from the GitHub `v2.0.0` Release and follow
  the [Virtual Account migration guide](https://developers.uqpay.com/global-account/v1.6/guide/migrate-to-virtual-account-applications).
- The artifact remains a thin JAR and is not available from Maven Central. Install
  it into a local repository and declare Jackson, OkHttp, and Bouncy Castle in the
  consuming project.

## [1.2.0]

This bootstrap alignment release establishes the shared stable `1.2` capability
baseline used by all five UQPAY customer SDKs. It covers all 98 callable operations
in the current business API contract; Ramp remains outside the SDK product scope.

### Added

- Connect RFI list, retrieve, and answer services.
- Issuing card limit, risk, PIN, ART, merchant-brand, and unsolicited-refund
  release operations.
- Payment terminal registration and PIN-key operations.
- Automatic `x-client-id` transport headers and DELETE requests with JSON bodies
  for operations that require them.

### Distribution

- Java 11 remains the minimum runtime and bytecode target.
- The SDK remains a thin JAR distributed through GitHub Releases. It is not
  published to Maven Central; download the JAR, install it locally, and declare
  the Jackson, OkHttp, and Bouncy Castle dependencies described in `pom.xml`.

## [1.1.0]

### Fixed

- **Create SubAccount (`POST /v1/accounts/create_accounts`) for individuals.**
  `SubAccountIndividualInfo` was missing fields the Account Center API now
  requires for `entity_type: INDIVIDUAL`, so the SDK could not express a valid
  individual sub-account and the API rejected requests. Added the fields the API
  requires:
  - `gender` (`MALE` | `FEMALE`) and `annual_income` — required effective 2026-07-02.
  - `employment_status`, `industry`, `job_title`, `company_name` — required effective 2026-03-19.

### Added

- `SubAccountIndividualInfo` getters/setters for `gender`, `annual_income`,
  `employment_status`, `industry`, `job_title`, and `company_name`, each
  serialized with its snake_case JSON key.

### Notes

- All new fields are typed as `String`, consistent with existing string-valued
  fields on the model (e.g. `businessType`). `gender` accepts `MALE` / `FEMALE`;
  `employment_status` accepts one of `Employed`, `Self-Employed`, `Unemployed`,
  `Student`, `Retired`, `Homemaker`, `Other`. `industry` and `job_title` take
  values from the external Enum Reference.
- `state` was already required on the model; `apartment_suite_or_floor` (optional)
  was already present.
- This is an additive, backward-compatible change (new fields and accessors only),
  hence a minor version bump (1.0.0 → 1.1.0).
