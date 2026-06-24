package com.uqpay.sdk.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uqpay.sdk.connect.model.CreateAccountRequest;
import com.uqpay.sdk.connect.model.EntityType;
import com.uqpay.sdk.connect.model.ListAccountsRequest;
import com.uqpay.sdk.connect.model.PersonDetails;
import com.uqpay.sdk.connect.model.SubAccountIndividualInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for Connect module classes.
 */
@DisplayName("Connect Module Unit Tests")
class AccountsServiceTest {

    @Nested
    @DisplayName("AccountsService construction")
    class Construction {

        @Test
        @DisplayName("should reject null apiClient")
        void shouldRejectNullApiClient() {
            assertThatThrownBy(() -> new AccountsService(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("apiClient must not be null");
        }
    }

    @Nested
    @DisplayName("ConnectClient construction")
    class ConnectClientConstruction {

        @Test
        @DisplayName("should reject null apiClient")
        void shouldRejectNullApiClient() {
            assertThatThrownBy(() -> new ConnectClient(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("apiClient must not be null");
        }
    }

    @Nested
    @DisplayName("ListAccountsRequest query string")
    class ListAccountsRequestQueryString {

        @Test
        @DisplayName("should return empty string with no filters")
        void shouldReturnEmptyStringWithNoFilters() {
            ListAccountsRequest request = new ListAccountsRequest();
            assertThat(request.toQueryString()).isEmpty();
        }

        @Test
        @DisplayName("should include page_size")
        void shouldIncludePageSize() {
            ListAccountsRequest request = new ListAccountsRequest();
            request.setPageSize(10);
            assertThat(request.toQueryString()).isEqualTo("?page_size=10");
        }

        @Test
        @DisplayName("should include page_number")
        void shouldIncludePageNumber() {
            ListAccountsRequest request = new ListAccountsRequest();
            request.setPageNumber(2);
            assertThat(request.toQueryString()).isEqualTo("?page_number=2");
        }

        @Test
        @DisplayName("should combine all filters")
        void shouldCombineAllFilters() {
            ListAccountsRequest request = new ListAccountsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);
            assertThat(request.toQueryString()).isEqualTo("?page_size=10&page_number=1");
        }
    }

    @Nested
    @DisplayName("EntityType")
    class EntityTypeTest {

        @Test
        @DisplayName("should have INDIVIDUAL and COMPANY values")
        void shouldHaveValues() {
            assertThat(EntityType.values()).containsExactly(EntityType.INDIVIDUAL, EntityType.COMPANY);
        }

        @Test
        @DisplayName("should serialize to correct JSON values")
        void shouldSerializeCorrectly() {
            assertThat(EntityType.INDIVIDUAL.getValue()).isEqualTo("INDIVIDUAL");
            assertThat(EntityType.COMPANY.getValue()).isEqualTo("COMPANY");
        }
    }

    @Nested
    @DisplayName("CreateAccountRequest validation")
    class CreateAccountRequestValidation {

        @Test
        @DisplayName("should set and get entity type")
        void shouldSetAndGetEntityType() {
            CreateAccountRequest request = new CreateAccountRequest();
            request.setEntityType(EntityType.INDIVIDUAL);
            assertThat(request.getEntityType()).isEqualTo(EntityType.INDIVIDUAL);
        }

        @Test
        @DisplayName("should set and get person details")
        void shouldSetAndGetPersonDetails() {
            CreateAccountRequest request = new CreateAccountRequest();
            PersonDetails personDetails = new PersonDetails();
            personDetails.setFirstNameEnglish("John");
            personDetails.setLastNameEnglish("Doe");
            request.setPersonDetails(personDetails);

            assertThat(request.getPersonDetails()).isNotNull();
            assertThat(request.getPersonDetails().getFirstNameEnglish()).isEqualTo("John");
            assertThat(request.getPersonDetails().getLastNameEnglish()).isEqualTo("Doe");
        }
    }

    @Nested
    @DisplayName("SubAccountIndividualInfo required-field serialization")
    class SubAccountIndividualInfoSerialization {

        private final ObjectMapper mapper = new ObjectMapper();

        @Test
        @DisplayName("should serialize the breaking-change individual_info fields with snake_case keys")
        void shouldSerializeNewRequiredFields() throws Exception {
            SubAccountIndividualInfo info = new SubAccountIndividualInfo();
            // Required effective 2026-03-19
            info.setEmploymentStatus("Employed");
            info.setIndustry("Information Technology/IT");
            info.setJobTitle("Business and administration professionals");
            info.setCompanyName("Acme Corp.");
            // Required effective 2026-07-02
            info.setGender("MALE");
            info.setAnnualIncome("85000");
            // state is required by spec; apartment_suite_or_floor is optional
            info.setState("Singapore");
            info.setApartmentSuiteOrFloor("Unit 1");

            String json = mapper.writeValueAsString(info);

            assertThat(json)
                    .contains("\"employment_status\":\"Employed\"")
                    .contains("\"industry\":\"Information Technology/IT\"")
                    .contains("\"job_title\":\"Business and administration professionals\"")
                    .contains("\"company_name\":\"Acme Corp.\"")
                    .contains("\"gender\":\"MALE\"")
                    .contains("\"annual_income\":\"85000\"")
                    .contains("\"state\":\"Singapore\"")
                    .contains("\"apartment_suite_or_floor\":\"Unit 1\"");
        }
    }
}
