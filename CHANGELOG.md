# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed

- Replaced the Virtual Account Create request/response contract with the application contract:
  required `country`, single-value `currency`, optional `LOCAL`/`SWIFT` method and nickname,
  explicit idempotency and connected-account request options, and the complete application DTO.
- Virtual Account application webhooks now parse the same application DTO for
  `virtual.account.create`, `virtual.account.update`, and `virtual.account.closed`; consumers can
  reconcile out-of-order delivery with `application_id` and `public_version`.

### Added

- List and Retrieve Virtual Account Application operations and precise application summary,
  result, error, bank-detail, clearing-system, status, and payment-method models.
- Strict three-field Virtual Account API errors expose `type`, `code`, and `message`, including
  the intentional HTTP 400 missing/cross-account application contract.

The existing List Virtual Accounts operation remains available and distinct from List Applications.

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
