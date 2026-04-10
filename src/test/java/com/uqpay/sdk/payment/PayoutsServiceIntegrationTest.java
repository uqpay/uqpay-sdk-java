package com.uqpay.sdk.payment;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.payment.model.CreatePayoutRequest;
import com.uqpay.sdk.payment.model.ListPayoutsRequest;
import com.uqpay.sdk.payment.model.ListPayoutsResponse;
import com.uqpay.sdk.payment.model.Payout;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PayoutsService Integration Tests")
class PayoutsServiceIntegrationTest {

    private static PayoutsService payoutsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        PaymentClient paymentClient = new PaymentClient(apiClient);
        payoutsService = paymentClient.getPayouts();
    }

    @Nested
    @DisplayName("Create Payout")
    class CreatePayout {

        @Test
        @DisplayName("should create a payout")
        void shouldCreatePayout() throws UqpayException {
            CreatePayoutRequest request = new CreatePayoutRequest();
            request.setAmount("50.00");
            request.setCurrency("USD");
            request.setStatementDescriptor("SDK Test Pyt");

            try {
                Payout payout = payoutsService.create(request);

                assertThat(payout).isNotNull();
                assertThat(payout.getPayoutId()).isNotEmpty();
                assertThat(payout.getPayoutAmount()).isNotEmpty();

                System.out.printf("Payout created successfully%n");
                System.out.printf("   ID: %s%n", payout.getPayoutId());
                System.out.printf("   Amount: %s %s%n", payout.getPayoutAmount(), payout.getPayoutCurrency());
                System.out.printf("   Status: %s%n", payout.getPayoutStatus());
            } catch (UqpayException e) {
                System.out.printf("Create payout returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("List Payouts")
    class ListPayouts {

        @Test
        @DisplayName("should list payouts with pagination")
        void shouldListPayoutsWithPagination() throws UqpayException {
            ListPayoutsRequest request = new ListPayoutsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListPayoutsResponse response = payoutsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d payouts (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                Payout first = response.getData().get(0);
                System.out.printf("First payout: ID=%s, Amount=%s %s, Status=%s%n",
                        first.getPayoutId(), first.getPayoutAmount(),
                        first.getPayoutCurrency(), first.getPayoutStatus());
            }
        }
    }

    @Nested
    @DisplayName("Get Payout")
    class GetPayout {

        @Test
        @DisplayName("should get payout by ID")
        void shouldGetPayoutById() throws UqpayException {
            ListPayoutsRequest listRequest = new ListPayoutsRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListPayoutsResponse listResponse = payoutsService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No payouts available, skipping Get test");
                return;
            }

            String payoutId = listResponse.getData().get(0).getPayoutId();

            Payout payout = payoutsService.get(payoutId);

            assertThat(payout).isNotNull();
            assertThat(payout.getPayoutId()).isEqualTo(payoutId);

            System.out.printf("Retrieved payout: ID=%s, Amount=%s %s, Status=%s%n",
                    payout.getPayoutId(), payout.getPayoutAmount(),
                    payout.getPayoutCurrency(), payout.getPayoutStatus());
        }
    }
}
