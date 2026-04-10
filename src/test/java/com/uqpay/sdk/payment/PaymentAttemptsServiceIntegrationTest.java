package com.uqpay.sdk.payment;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.payment.model.ListPaymentAttemptsRequest;
import com.uqpay.sdk.payment.model.ListPaymentAttemptsResponse;
import com.uqpay.sdk.payment.model.PaymentAttempt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PaymentAttemptsService Integration Tests")
class PaymentAttemptsServiceIntegrationTest {

    private static PaymentAttemptsService paymentAttemptsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        PaymentClient paymentClient = new PaymentClient(apiClient);
        paymentAttemptsService = paymentClient.getPaymentAttempts();
    }

    @Nested
    @DisplayName("List Payment Attempts")
    class ListPaymentAttempts {

        @Test
        @DisplayName("should list payment attempts with pagination")
        void shouldListPaymentAttemptsWithPagination() throws UqpayException {
            ListPaymentAttemptsRequest request = new ListPaymentAttemptsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListPaymentAttemptsResponse response = paymentAttemptsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d payment attempts (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                PaymentAttempt first = response.getData().get(0);
                System.out.printf("First attempt: ID=%s, Amount=%s %s, Status=%s%n",
                        first.getAttemptId(), first.getAmount(),
                        first.getCurrency(), first.getAttemptStatus());
            }
        }
    }

    @Nested
    @DisplayName("Get Payment Attempt")
    class GetPaymentAttempt {

        @Test
        @DisplayName("should get payment attempt by ID")
        void shouldGetPaymentAttemptById() throws UqpayException {
            ListPaymentAttemptsRequest listRequest = new ListPaymentAttemptsRequest();
            listRequest.setPageSize(10);
            listRequest.setPageNumber(1);

            ListPaymentAttemptsResponse listResponse = paymentAttemptsService.list(listRequest);
            if (listResponse.getData() == null || listResponse.getData().isEmpty()) {
                System.out.println("No payment attempts available, skipping Get test");
                return;
            }

            String attemptId = listResponse.getData().get(0).getAttemptId();

            PaymentAttempt attempt = paymentAttemptsService.get(attemptId);

            assertThat(attempt).isNotNull();
            assertThat(attempt.getAttemptId()).isEqualTo(attemptId);

            System.out.printf("Retrieved attempt: ID=%s, Amount=%s %s, Status=%s%n",
                    attempt.getAttemptId(), attempt.getAmount(),
                    attempt.getCurrency(), attempt.getAttemptStatus());
        }
    }
}
