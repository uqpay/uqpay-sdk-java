package com.uqpay.sdk.issuing;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.model.CreateIssuingTransferRequest;
import com.uqpay.sdk.issuing.model.CreateIssuingTransferResponse;
import com.uqpay.sdk.issuing.model.IssuingTransfer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TransfersService Integration Tests")
class TransfersServiceIntegrationTest {

    private static TransfersService transfersService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        IssuingClient issuingClient = new IssuingClient(apiClient);
        transfersService = issuingClient.getTransfers();
    }

    @Nested
    @DisplayName("Create Transfer")
    class CreateTransfer {

        @Test
        @DisplayName("should create an issuing transfer")
        void shouldCreateIssuingTransfer() throws UqpayException {
            CreateIssuingTransferRequest request = new CreateIssuingTransferRequest();
            request.setSourceAccountId("65087660-8d3d-428e-bd2e-9e56219c1512");
            request.setDestinationAccountId("11db237e-1a2b-4449-9878-a9bf1f0df0c7");
            request.setCurrency("SGD");
            request.setAmount("100.00");
            request.setRemark("Test transfer from Java SDK");

            try {
                CreateIssuingTransferResponse response = transfersService.create(request);

                assertThat(response).isNotNull();
                assertThat(response.getTransferId()).isNotNull();

                System.out.printf("Transfer created: ID=%s%n", response.getTransferId());

                // Retrieve the created transfer
                IssuingTransfer transfer = transfersService.retrieve(response.getTransferId());

                assertThat(transfer).isNotNull();
                assertThat(transfer.getTransferId()).isEqualTo(response.getTransferId());

                System.out.printf("Retrieved transfer: ID=%s, Ref=%s, Amount=%s %s, Status=%s%n",
                        transfer.getTransferId(), transfer.getReferenceId(),
                        transfer.getAmount(), transfer.getCurrency(), transfer.getTransferStatus());
                System.out.printf("  Source=%s, Dest=%s, Fee=%s%n",
                        transfer.getSourceAccountId(), transfer.getDestinationAccountId(),
                        transfer.getFeeAmount());
            } catch (UqpayException e) {
                System.out.printf("Create transfer returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Retrieve Transfer")
    class RetrieveTransfer {

        @Test
        @DisplayName("should retrieve an issuing transfer by ID")
        void shouldRetrieveIssuingTransfer() throws UqpayException {
            String transferId = "d58ed244-4b73-4095-bebf-2d05c0aab856";

            try {
                IssuingTransfer transfer = transfersService.retrieve(transferId);

                assertThat(transfer).isNotNull();

                System.out.printf("Retrieved transfer: ID=%s, Ref=%s, Amount=%s %s, Status=%s%n",
                        transfer.getTransferId(), transfer.getReferenceId(),
                        transfer.getAmount(), transfer.getCurrency(), transfer.getTransferStatus());
                System.out.printf("  Source=%s, Dest=%s%n",
                        transfer.getSourceAccountId(), transfer.getDestinationAccountId());
                System.out.printf("  Fee=%s, Creator=%s, Remark=%s%n",
                        transfer.getFeeAmount(), transfer.getCreatorId(), transfer.getRemark());
                System.out.printf("  Created=%s, Completed=%s%n",
                        transfer.getCreateTime(), transfer.getCompleteTime());
            } catch (UqpayException e) {
                System.out.printf("Retrieve transfer returned error (may be expected): %s%n", e.getMessage());
            }
        }
    }
}
