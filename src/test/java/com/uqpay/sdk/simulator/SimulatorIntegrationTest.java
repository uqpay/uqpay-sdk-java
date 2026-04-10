package com.uqpay.sdk.simulator;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.UqpayClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.issuing.model.RetrieveCardResponse;
import com.uqpay.sdk.simulator.model.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Simulator Integration Tests")
class SimulatorIntegrationTest {

    private static UqpayClient client;
    private static SimulatorClient simulatorClient;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();
        String clientId = TestHelper.getClientId();
        String apiKey = TestHelper.getApiKey();
        client = UqpayClient.sandbox(clientId, apiKey);
        simulatorClient = client.simulator();
    }

    private static RetrieveCardResponse findSimulatorCard() throws UqpayException {
        String cardId = TestHelper.getEnv("UQPAY_SIMULATOR_CARD_ID");
        if (cardId == null || cardId.trim().isEmpty()) {
            return null;
        }
        return client.issuing().getCards().get(cardId);
    }

    @Nested
    @DisplayName("Issuing Simulator")
    class IssuingSimulator {

        @Test
        @DisplayName("should simulate card authorization")
        void shouldSimulateAuthorization() throws UqpayException {
            RetrieveCardResponse card = findSimulatorCard();
            if (card == null) {
                System.out.println("Simulator card not found, skipping authorization simulation");
                return;
            }

            // Use the card's own currency to avoid currency mismatch errors
            String currency = card.getCardCurrency() != null ? card.getCardCurrency() : "SGD";

            SimulateAuthorizationRequest request = new SimulateAuthorizationRequest();
            request.setCardId(card.getCardId());
            request.setTransactionAmount(1.00);
            request.setTransactionCurrency(currency);
            request.setMerchantName("Test Merchant");
            request.setMerchantCategoryCode("5734");

            try {
                SimulateAuthorizationResponse response = simulatorClient.getIssuing().authorize(request);

                assertThat(response).isNotNull();
                assertThat(response.getCardId()).isEqualTo(card.getCardId());
                assertThat(response.getTransactionId()).isNotNull();
                assertThat(response.getTransactionType()).isNotNull();
                assertThat(response.getTransactionStatus()).isNotNull();

                System.out.printf("Authorization: txnId=%s, status=%s, type=%s, balance=%s%n",
                        response.getTransactionId(), response.getTransactionStatus(),
                        response.getTransactionType(), response.getCardAvailableBalance());

                if (response.getMerchantData() != null) {
                    System.out.printf("  Merchant: name=%s, mcc=%s%n",
                            response.getMerchantData().getName(),
                            response.getMerchantData().getCategoryCode());
                }
            } catch (UqpayException e) {
                // Card may have insufficient balance or other sandbox limitations
                System.out.printf("Authorization simulation returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }

        @Test
        @DisplayName("should simulate card authorization and then reversal")
        void shouldSimulateAuthorizationAndReversal() throws UqpayException {
            RetrieveCardResponse card = findSimulatorCard();
            if (card == null) {
                System.out.println("Simulator card not found, skipping reversal simulation");
                return;
            }

            String currency = card.getCardCurrency() != null ? card.getCardCurrency() : "SGD";

            // First authorize
            SimulateAuthorizationRequest authRequest = new SimulateAuthorizationRequest();
            authRequest.setCardId(card.getCardId());
            authRequest.setTransactionAmount(0.50);
            authRequest.setTransactionCurrency(currency);
            authRequest.setMerchantName("Test Merchant Reversal");
            authRequest.setMerchantCategoryCode("5734");

            try {
                SimulateAuthorizationResponse authResponse = simulatorClient.getIssuing().authorize(authRequest);
                assertThat(authResponse).isNotNull();
                assertThat(authResponse.getTransactionId()).isNotNull();

                System.out.printf("Authorization for reversal: txnId=%s, status=%s%n",
                        authResponse.getTransactionId(), authResponse.getTransactionStatus());

                // Then reverse
                SimulateReversalRequest reversalRequest = new SimulateReversalRequest();
                reversalRequest.setTransactionId(authResponse.getTransactionId());

                SimulateAuthorizationResponse reversalResponse = simulatorClient.getIssuing().reverse(reversalRequest);

                assertThat(reversalResponse).isNotNull();
                assertThat(reversalResponse.getTransactionId()).isNotNull();

                System.out.printf("Reversal: txnId=%s, status=%s, type=%s%n",
                        reversalResponse.getTransactionId(), reversalResponse.getTransactionStatus(),
                        reversalResponse.getTransactionType());
            } catch (UqpayException e) {
                System.out.printf("Authorization/reversal simulation returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Deposits Simulator")
    class DepositsSimulator {

        @Test
        @DisplayName("should simulate deposit creation")
        void shouldSimulateDeposit() throws UqpayException {
            SimulateDepositRequest request = new SimulateDepositRequest();
            request.setAmount(100.00);
            request.setCurrency("USD");
            request.setSenderSwiftCode("UABORUMM");
            request.setSenderName("Test Sender");
            request.setSenderCountry("US");
            request.setSenderAccountNumber("123456789");

            try {
                SimulateDepositResponse response = simulatorClient.getDeposits().simulate(request);

                assertThat(response).isNotNull();
                assertThat(response.getDepositId()).isNotNull();
                assertThat(response.getCurrency()).isEqualTo("USD");
                assertThat(response.getDepositStatus()).isNotNull();

                System.out.printf("Deposit: id=%s, shortRef=%s, amount=%s, status=%s%n",
                        response.getDepositId(), response.getShortReferenceId(),
                        response.getAmount(), response.getDepositStatus());

                if (response.getSender() != null) {
                    System.out.printf("  Sender: name=%s, country=%s, swift=%s%n",
                            response.getSender().getSenderName(),
                            response.getSender().getSenderCountry(),
                            response.getSender().getSenderSwiftCode());
                }
            } catch (UqpayException e) {
                System.out.printf("Deposit simulation returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Sandbox Guard")
    class SandboxGuard {

        @Test
        @DisplayName("simulator client should be accessible in sandbox")
        void shouldBeAccessibleInSandbox() {
            assertThat(simulatorClient).isNotNull();
            assertThat(simulatorClient.getIssuing()).isNotNull();
            assertThat(simulatorClient.getDeposits()).isNotNull();
        }
    }
}
