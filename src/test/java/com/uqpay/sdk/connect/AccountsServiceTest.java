package com.uqpay.sdk.connect;

import com.uqpay.sdk.connect.model.CreateAccountRequest;
import com.uqpay.sdk.connect.model.EntityType;
import com.uqpay.sdk.connect.model.ListAccountsRequest;
import com.uqpay.sdk.connect.model.PersonDetails;
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
}
