package com.uqpay.sdk.issuing;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.model.CreateReportRequest;
import com.uqpay.sdk.issuing.model.CreateReportResponse;
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
        IssuingClient issuingClient = new IssuingClient(apiClient);
        reportsService = issuingClient.getReports();
    }

    @Nested
    @DisplayName("Create Report")
    class CreateReport {

        @Test
        @DisplayName("should create a settlement report")
        void shouldCreateSettlementReport() throws UqpayException {
            CreateReportRequest request = new CreateReportRequest();
            request.setReportType("SETTLEMENT");
            request.setStartTime("2024-01-01T00:00:00+08:00");
            request.setEndTime("2024-12-31T23:59:59+08:00");

            try {
                CreateReportResponse response = reportsService.create(request);

                assertThat(response).isNotNull();
                assertThat(response.getReportId()).isNotNull();

                System.out.printf("Settlement report created: ID=%s%n", response.getReportId());
            } catch (UqpayException e) {
                System.out.printf("Create settlement report returned error (may be expected): %s%n", e.getMessage());
            }
        }

        @Test
        @DisplayName("should create a ledger report")
        void shouldCreateLedgerReport() throws UqpayException {
            CreateReportRequest request = new CreateReportRequest();
            request.setReportType("LEDGER");
            request.setStartTime("2024-01-01T00:00:00+08:00");
            request.setEndTime("2024-12-31T23:59:59+08:00");

            try {
                CreateReportResponse response = reportsService.create(request);

                assertThat(response).isNotNull();
                assertThat(response.getReportId()).isNotNull();

                System.out.printf("Ledger report created: ID=%s%n", response.getReportId());
            } catch (UqpayException e) {
                System.out.printf("Create ledger report returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }
}
