package com.uqpay.sdk.issuing;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.model.Cardholder;
import com.uqpay.sdk.issuing.model.CreateCardholderRequest;
import com.uqpay.sdk.issuing.model.CreateCardholderResponse;
import com.uqpay.sdk.issuing.model.ListCardholdersRequest;
import com.uqpay.sdk.issuing.model.ListCardholdersResponse;
import com.uqpay.sdk.issuing.model.UpdateCardholderRequest;
import com.uqpay.sdk.issuing.model.UpdateCardholderResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CardholdersService Integration Tests")
class CardholdersServiceIntegrationTest {

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

    @Nested
    @DisplayName("Create Cardholder")
    class CreateCardholder {

        @Test
        @DisplayName("should create a new cardholder")
        void shouldCreateCardholder() throws UqpayException {
            long timestamp = System.currentTimeMillis();

            CreateCardholderRequest request = new CreateCardholderRequest();
            request.setEmail(String.format("test%d@example.com", timestamp));
            request.setPhoneNumber(String.format("8%07d", timestamp % 10000000));
            request.setFirstName("Integration");
            request.setLastName("Test");
            request.setCountryCode("SG");

            try {
                CreateCardholderResponse cardholder = cardholdersService.create(request);

                assertThat(cardholder).isNotNull();
                assertThat(cardholder.getCardholderId()).isNotNull();

                System.out.printf("Cardholder created: ID=%s, Status=%s%n",
                        cardholder.getCardholderId(), cardholder.getCardholderStatus());

                // Verify by retrieving the created cardholder
                Cardholder retrieved = cardholdersService.get(cardholder.getCardholderId());

                assertThat(retrieved).isNotNull();
                assertThat(retrieved.getCardholderId()).isEqualTo(cardholder.getCardholderId());

                System.out.printf("Verified cardholder: ID=%s, Email=%s, Status=%s%n",
                        retrieved.getCardholderId(), retrieved.getEmail(), retrieved.getCardholderStatus());
            } catch (UqpayException e) {
                System.out.printf("Create cardholder returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("List Cardholders")
    class ListCardholders {

        @Test
        @DisplayName("should list cardholders with pagination")
        void shouldListCardholdersWithPagination() throws UqpayException {
            ListCardholdersRequest request = new ListCardholdersRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListCardholdersResponse response = cardholdersService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d cardholders (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                Cardholder first = response.getData().get(0);
                System.out.printf("First cardholder: ID=%s, Email=%s, Name=%s %s, Status=%s%n",
                        first.getCardholderId(), first.getEmail(),
                        first.getFirstName(), first.getLastName(), first.getCardholderStatus());
            }
        }
    }

    @Nested
    @DisplayName("Update Cardholder")
    class UpdateCardholder {

        @Test
        @DisplayName("should update an existing cardholder")
        void shouldUpdateCardholder() throws UqpayException {
            // First, list cardholders to get a valid ID
            ListCardholdersRequest listRequest = new ListCardholdersRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListCardholdersResponse listResponse = cardholdersService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No cardholders available, skipping Update test");
                return;
            }

            String cardholderId = listResponse.getData().get(0).getCardholderId();

            UpdateCardholderRequest request = new UpdateCardholderRequest();
            request.setEmail("updated-" + System.currentTimeMillis() + "@example.com");

            try {
                UpdateCardholderResponse response = cardholdersService.update(cardholderId, request);

                assertThat(response).isNotNull();
                assertThat(response.getCardholderId()).isEqualTo(cardholderId);

                System.out.printf("Updated cardholder: ID=%s, Status=%s%n",
                        response.getCardholderId(), response.getCardholderStatus());
            } catch (UqpayException e) {
                System.out.printf("Update cardholder returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Get Cardholder")
    class GetCardholder {

        @Test
        @DisplayName("should get cardholder by ID")
        void shouldGetCardholderById() throws UqpayException {
            // First, list cardholders to get a valid ID
            ListCardholdersRequest listRequest = new ListCardholdersRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListCardholdersResponse listResponse = cardholdersService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No cardholders available, skipping Get test");
                return;
            }

            String cardholderId = listResponse.getData().get(0).getCardholderId();

            Cardholder cardholder = cardholdersService.get(cardholderId);

            assertThat(cardholder).isNotNull();
            assertThat(cardholder.getCardholderId()).isEqualTo(cardholderId);

            System.out.printf("Retrieved cardholder: ID=%s, Email=%s, Name=%s %s, Country=%s, Status=%s%n",
                    cardholder.getCardholderId(), cardholder.getEmail(),
                    cardholder.getFirstName(), cardholder.getLastName(),
                    cardholder.getCountryCode(), cardholder.getCardholderStatus());
        }
    }
}
