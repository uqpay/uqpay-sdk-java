# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
