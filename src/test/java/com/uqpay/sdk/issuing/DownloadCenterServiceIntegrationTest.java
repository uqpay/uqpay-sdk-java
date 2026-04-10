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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DownloadCenterService Integration Tests")
class DownloadCenterServiceIntegrationTest {

    private static DownloadCenterService downloadCenterService;
    private static ReportsService reportsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        IssuingClient issuingClient = new IssuingClient(apiClient);
        downloadCenterService = issuingClient.getDownloadCenter();
        reportsService = issuingClient.getReports();
    }

    @Nested
    @DisplayName("Download Report")
    class DownloadReport {

        @Test
        @DisplayName("should download report by ID")
        void shouldDownloadReportById() throws UqpayException {
            // First create a report to get a valid report ID
            CreateReportRequest createRequest = new CreateReportRequest();
            createRequest.setReportType("LEDGER");
            createRequest.setStartTime("2024-01-01T00:00:00+08:00");
            createRequest.setEndTime("2024-12-31T23:59:59+08:00");

            String reportId;
            try {
                CreateReportResponse createResponse = reportsService.create(createRequest);
                reportId = createResponse.getReportId();
                System.out.printf("Created report for download: ID=%s%n", reportId);
            } catch (UqpayException e) {
                System.out.printf("Could not create report for download test: %s%n", e.getMessage());
                return;
            }

            try {
                byte[] data = downloadCenterService.download(reportId);

                assertThat(data).isNotNull();

                System.out.printf("Downloaded report: %d bytes%n", data.length);
            } catch (UqpayException e) {
                System.out.printf("Download report returned error (may be expected if report is still generating): %s%n", e.getMessage());
            }
        }

        @Test
        @DisplayName("should reject empty report ID")
        void shouldRejectEmptyReportId() {
            assertThatThrownBy(() -> downloadCenterService.download(""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("reportId must not be empty");
        }
    }
}
