package com.uqpay.sdk.payment;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.payment.model.ListSettlementsRequest;
import com.uqpay.sdk.payment.model.ListSettlementsResponse;
import com.uqpay.sdk.payment.model.Settlement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReportsService Integration Tests")
class ReportsServiceIntegrationTest {

    private static ReportsService reportsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        PaymentClient paymentClient = new PaymentClient(apiClient);
        reportsService = paymentClient.getReports();
    }

    @Nested
    @DisplayName("List Settlements")
    class ListSettlements {

        @Test
        @DisplayName("should list settlements with pagination")
        void shouldListSettlementsWithPagination() throws UqpayException {
            ListSettlementsRequest request = new ListSettlementsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListSettlementsResponse response = reportsService.listSettlements(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d settlements (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                Settlement first = response.getData().get(0);
                System.out.printf("First settlement: ID=%s, Amount=%s %s, Status=%s%n",
                        first.getSettlementId(), first.getSettlementAmount(),
                        first.getSettlementCurrency(), first.getSettlementStatus());
            }
        }
    }
}
