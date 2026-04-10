package com.uqpay.sdk.webhook;

import com.uqpay.sdk.webhook.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Event")
class EventTest {

    // =========================================================================
    // fromJson deserialization
    // =========================================================================

    @Nested
    @DisplayName("fromJson")
    class FromJsonTests {

        @Test
        @DisplayName("should deserialize event envelope fields")
        void shouldDeserializeEventEnvelope() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ONBOARDING\","
                    + "\"event_type\":\"onboarding.account.create\","
                    + "\"event_id\":\"evt_123\","
                    + "\"source_id\":\"acc_456\","
                    + "\"data\":{\"account_id\":\"acc_456\"}"
                    + "}";

            Event event = Event.fromJson(json);

            assertThat(event.getVersion()).isEqualTo("V1.6.0");
            assertThat(event.getEventName()).isEqualTo("ONBOARDING");
            assertThat(event.getEventType()).isEqualTo("onboarding.account.create");
            assertThat(event.getEventId()).isEqualTo("evt_123");
            assertThat(event.getSourceId()).isEqualTo("acc_456");
            assertThat(event.getData()).isNotNull();
        }

        @Test
        @DisplayName("should ignore unknown fields")
        void shouldIgnoreUnknownFields() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ONBOARDING\","
                    + "\"event_type\":\"onboarding.account.create\","
                    + "\"event_id\":\"evt_123\","
                    + "\"source_id\":\"acc_456\","
                    + "\"unknown_field\":\"value\","
                    + "\"data\":{}"
                    + "}";

            Event event = Event.fromJson(json);
            assertThat(event.getVersion()).isEqualTo("V1.6.0");
        }

        @Test
        @DisplayName("should throw on invalid JSON")
        void shouldThrowOnInvalidJson() {
            assertThatThrownBy(() -> Event.fromJson("not json"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Failed to parse Event from JSON");
        }
    }

    // =========================================================================
    // Category check methods
    // =========================================================================

    @Nested
    @DisplayName("Category check methods")
    class CategoryCheckTests {

        @Test
        @DisplayName("isOnboardingEvent should return true for ONBOARDING")
        void isOnboardingEvent() {
            Event event = createEvent("ONBOARDING", "onboarding.account.create");
            assertThat(event.isOnboardingEvent()).isTrue();
            assertThat(event.isAcquiringEvent()).isFalse();
            assertThat(event.isConversionEvent()).isFalse();
            assertThat(event.isIssuingEvent()).isFalse();
            assertThat(event.isBeneficiaryEvent()).isFalse();
        }

        @Test
        @DisplayName("isAcquiringEvent should return true for ACQUIRING")
        void isAcquiringEvent() {
            Event event = createEvent("ACQUIRING", "acquiring.payment_intent.created");
            assertThat(event.isAcquiringEvent()).isTrue();
            assertThat(event.isOnboardingEvent()).isFalse();
        }

        @Test
        @DisplayName("isConversionEvent should return true for CONVERSION")
        void isConversionEvent() {
            Event event = createEvent("CONVERSION", "conversion.trade.settled");
            assertThat(event.isConversionEvent()).isTrue();
        }

        @Test
        @DisplayName("isIssuingEvent should return true for ISSUING")
        void isIssuingEvent() {
            Event event = createEvent("ISSUING", "card.create.succeeded");
            assertThat(event.isIssuingEvent()).isTrue();
        }

        @Test
        @DisplayName("isBeneficiaryEvent should return true for BENEFICIARY")
        void isBeneficiaryEvent() {
            Event event = createEvent("BENEFICIARY", "beneficiary.successful");
            assertThat(event.isBeneficiaryEvent()).isTrue();
        }

        @Test
        @DisplayName("isDepositEvent should return true for DEPOSIT")
        void isDepositEvent() {
            Event event = createEvent("DEPOSIT", "deposit.completed");
            assertThat(event.isDepositEvent()).isTrue();
            assertThat(event.isAcquiringEvent()).isFalse();
            assertThat(event.isIssuingEvent()).isFalse();
        }

        @Test
        @DisplayName("isPayoutBankingEvent should return true for PAYOUT")
        void isPayoutBankingEvent() {
            Event event = createEvent("PAYOUT", "payout.completed");
            assertThat(event.isPayoutBankingEvent()).isTrue();
            assertThat(event.isAcquiringEvent()).isFalse();
        }

        @Test
        @DisplayName("isVirtualAccountEvent should return true for VIRTUAL")
        void isVirtualAccountEvent() {
            Event event = createEvent("VIRTUAL", "virtual.account.create");
            assertThat(event.isVirtualAccountEvent()).isTrue();
            assertThat(event.isOnboardingEvent()).isFalse();
        }

        @Test
        @DisplayName("isRfiEvent should return true for RFI")
        void isRfiEvent() {
            Event event = createEvent("RFI", "rfi.action_required");
            assertThat(event.isRfiEvent()).isTrue();
            assertThat(event.isIssuingEvent()).isFalse();
        }

        @Test
        @DisplayName("isTransferEvent should return true for TRANSFER")
        void isTransferEvent() {
            Event event = createEvent("TRANSFER", "transfer.completed");
            assertThat(event.isTransferEvent()).isTrue();
            assertThat(event.isConversionEvent()).isFalse();
        }

        @Test
        @DisplayName("isCardholderKycEvent should return true for cardholder.kyc.status_changed event name")
        void isCardholderKycEvent() {
            Event event = createEvent("cardholder.kyc.status_changed", "cardholder.kyc.status_changed");
            assertThat(event.isCardholderKycEvent()).isTrue();
            assertThat(event.isCardholderUpdatedEvent()).isFalse();
        }

        @Test
        @DisplayName("isCardholderUpdatedEvent should return true for cardholder.updated event name")
        void isCardholderUpdatedEvent() {
            Event event = createEvent("cardholder.updated", "cardholder.updated");
            assertThat(event.isCardholderUpdatedEvent()).isTrue();
            assertThat(event.isCardholderKycEvent()).isFalse();
        }
    }

    // =========================================================================
    // Specific type check methods
    // =========================================================================

    @Nested
    @DisplayName("Specific type check methods")
    class SpecificTypeCheckTests {

        @Test
        @DisplayName("account event checks")
        void accountEventChecks() {
            Event create = createEvent("ONBOARDING", "onboarding.account.create");
            assertThat(create.isAccountCreateEvent()).isTrue();
            assertThat(create.isAccountUpdateEvent()).isFalse();

            Event update = createEvent("ONBOARDING", "onboarding.account.update");
            assertThat(update.isAccountUpdateEvent()).isTrue();
            assertThat(update.isAccountCreateEvent()).isFalse();
        }

        @Test
        @DisplayName("payment intent event checks")
        void paymentIntentEventChecks() {
            for (String type : new String[]{
                    "acquiring.payment_intent.created",
                    "acquiring.payment_intent.succeeded",
                    "acquiring.payment_intent.failed",
                    "acquiring.payment_intent.canceled"}) {
                Event event = createEvent("ACQUIRING", type);
                assertThat(event.isPaymentIntentEvent()).as("type=%s", type).isTrue();
                assertThat(event.isPaymentAttemptEvent()).as("type=%s", type).isFalse();
            }
        }

        @Test
        @DisplayName("payment attempt event checks")
        void paymentAttemptEventChecks() {
            for (String type : new String[]{
                    "acquiring.payment_attempt.created",
                    "acquiring.payment_attempt.capture_requested",
                    "acquiring.payment_attempt.succeeded",
                    "acquiring.payment_attempt.failed",
                    "acquiring.payment_attempt.canceled"}) {
                Event event = createEvent("ACQUIRING", type);
                assertThat(event.isPaymentAttemptEvent()).as("type=%s", type).isTrue();
                assertThat(event.isPaymentIntentEvent()).as("type=%s", type).isFalse();
            }
        }

        @Test
        @DisplayName("refund event checks")
        void refundEventChecks() {
            for (String type : new String[]{
                    "acquiring.refund.created",
                    "acquiring.refund.succeeded",
                    "acquiring.refund.failed"}) {
                Event event = createEvent("ACQUIRING", type);
                assertThat(event.isRefundEvent()).as("type=%s", type).isTrue();
            }
        }

        @Test
        @DisplayName("card event checks")
        void cardEventChecks() {
            String[] cardTypes = {
                    "card.create.succeeded", "card.create.failed",
                    "card.update.succeeded", "card.update.failed",
                    "card.recharge.succeeded", "card.recharge.failed",
                    "card.activation.code",
                    "card.activated", "card.suspended", "card.closed",
                    "card.status.update.succeeded", "card.status.update.failed"
            };
            for (String type : cardTypes) {
                Event event = createEvent("ISSUING", type);
                assertThat(event.isCardEvent()).as("type=%s", type).isTrue();
            }
        }

        @Test
        @DisplayName("card create or update event checks")
        void cardCreateOrUpdateEventChecks() {
            for (String type : new String[]{
                    "card.create.succeeded", "card.create.failed",
                    "card.update.succeeded", "card.update.failed"}) {
                Event event = createEvent("ISSUING", type);
                assertThat(event.isCardCreateOrUpdateEvent()).as("type=%s", type).isTrue();
            }
            Event recharge = createEvent("ISSUING", "card.recharge.succeeded");
            assertThat(recharge.isCardCreateOrUpdateEvent()).isFalse();
        }

        @Test
        @DisplayName("card status update event checks")
        void cardStatusUpdateEventChecks() {
            Event succeeded = createEvent("ISSUING", "card.status.update.succeeded");
            assertThat(succeeded.isCardStatusUpdateEvent()).isTrue();

            Event failed = createEvent("ISSUING", "card.status.update.failed");
            assertThat(failed.isCardStatusUpdateEvent()).isTrue();

            Event create = createEvent("ISSUING", "card.create.succeeded");
            assertThat(create.isCardStatusUpdateEvent()).isFalse();
        }

        @Test
        @DisplayName("card recharge event checks")
        void cardRechargeEventChecks() {
            Event succeeded = createEvent("ISSUING", "card.recharge.succeeded");
            assertThat(succeeded.isCardRechargeEvent()).isTrue();

            Event failed = createEvent("ISSUING", "card.recharge.failed");
            assertThat(failed.isCardRechargeEvent()).isTrue();

            Event create = createEvent("ISSUING", "card.create.succeeded");
            assertThat(create.isCardRechargeEvent()).isFalse();
        }

        @Test
        @DisplayName("card activation code event check")
        void cardActivationCodeEventCheck() {
            Event event = createEvent("ISSUING", "card.activation.code");
            assertThat(event.isCardActivationCodeEvent()).isTrue();

            Event other = createEvent("ISSUING", "card.create.succeeded");
            assertThat(other.isCardActivationCodeEvent()).isFalse();
        }

        @Test
        @DisplayName("card transaction event check covers issuing.transaction.* types")
        void cardTransactionEventCheck() {
            Event feEvent = createEvent("ISSUING", "issuing.fee.card");
            assertThat(feEvent.isCardTransactionEvent()).isTrue();

            String[] issuingTransactionTypes = {
                    "issuing.transaction.authorization",
                    "issuing.transaction.reversal",
                    "issuing.transaction.refund",
                    "issuing.transaction.settlement.credit",
                    "issuing.transaction.settlement.debit",
                    "issuing.transaction.fund.collection",
                    "issuing.transaction.validation",
                    "issuing.transaction.atm.deposit",
                    "issuing.transaction.chargeback.credit",
                    "issuing.transaction.chargeback.debit"
            };
            for (String type : issuingTransactionTypes) {
                Event event = createEvent("ISSUING", type);
                assertThat(event.isCardTransactionEvent()).as("type=%s", type).isTrue();
            }

            Event other = createEvent("ISSUING", "card.create.succeeded");
            assertThat(other.isCardTransactionEvent()).isFalse();
        }

        @Test
        @DisplayName("beneficiary specific event checks")
        void beneficiarySpecificEventChecks() {
            Event successful = createEvent("BENEFICIARY", "beneficiary.successful");
            assertThat(successful.isBeneficiarySuccessfulEvent()).isTrue();
            assertThat(successful.isBeneficiaryFailedEvent()).isFalse();
            assertThat(successful.isBeneficiaryPendingEvent()).isFalse();

            Event failed = createEvent("BENEFICIARY", "beneficiary.failed");
            assertThat(failed.isBeneficiaryFailedEvent()).isTrue();

            Event pending = createEvent("BENEFICIARY", "beneficiary.pending");
            assertThat(pending.isBeneficiaryPendingEvent()).isTrue();
        }
    }

    // =========================================================================
    // parseAccountData
    // =========================================================================

    @Nested
    @DisplayName("parseAccountData")
    class ParseAccountDataTests {

        @Test
        @DisplayName("should parse account create event data")
        void shouldParseAccountCreateData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ONBOARDING\","
                    + "\"event_type\":\"onboarding.account.create\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"acc_1\","
                    + "\"data\":{"
                    + "  \"account_id\":\"acc_1\","
                    + "  \"direct_id\":\"dir_1\","
                    + "  \"short_reference_id\":\"ref_1\","
                    + "  \"email\":\"test@example.com\","
                    + "  \"account_name\":\"Test Account\","
                    + "  \"country\":\"US\","
                    + "  \"status\":\"ACTIVE\","
                    + "  \"idv_status\":\"APPROVED\","
                    + "  \"verification_status\":\"APPROVED\","
                    + "  \"entity_type\":\"COMPANY\","
                    + "  \"source\":\"api\","
                    + "  \"contact_details\":{\"email\":\"contact@example.com\",\"phone\":\"+1234567890\"},"
                    + "  \"registration_address\":{\"line1\":\"123 Main St\",\"city\":\"NYC\",\"country\":\"US\"},"
                    + "  \"representatives\":[{\"first_name\":\"John\",\"last_name\":\"Doe\",\"roles\":\"DIRECTOR\"}]"
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            AccountData account = event.parseAccountData();

            assertThat(account.getAccountId()).isEqualTo("acc_1");
            assertThat(account.getDirectId()).isEqualTo("dir_1");
            assertThat(account.getShortReferenceId()).isEqualTo("ref_1");
            assertThat(account.getEmail()).isEqualTo("test@example.com");
            assertThat(account.getAccountName()).isEqualTo("Test Account");
            assertThat(account.getCountry()).isEqualTo("US");
            assertThat(account.getStatus()).isEqualTo("ACTIVE");
            assertThat(account.getIdvStatus()).isEqualTo("APPROVED");
            assertThat(account.getVerificationStatus()).isEqualTo("APPROVED");
            assertThat(account.getEntityType()).isEqualTo("COMPANY");
            assertThat(account.getSource()).isEqualTo("api");
            assertThat(account.getContactDetails()).isNotNull();
            assertThat(account.getContactDetails().getEmail()).isEqualTo("contact@example.com");
            assertThat(account.getContactDetails().getPhone()).isEqualTo("+1234567890");
            assertThat(account.getRegistrationAddress()).isNotNull();
            assertThat(account.getRegistrationAddress().getLine1()).isEqualTo("123 Main St");
            assertThat(account.getRepresentatives()).hasSize(1);
            assertThat(account.getRepresentatives().get(0).getFirstName()).isEqualTo("John");
            assertThat(account.getRepresentatives().get(0).getRoles()).isEqualTo("DIRECTOR");
        }

        @Test
        @DisplayName("should throw on wrong event type")
        void shouldThrowOnWrongEventType() {
            Event event = createEventWithData("ACQUIRING", "acquiring.payment_intent.created",
                    "{\"payment_intent_id\":\"pi_1\"}");

            assertThatThrownBy(() -> event.parseAccountData())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("is not an account event");
        }
    }

    // =========================================================================
    // parsePaymentIntentData
    // =========================================================================

    @Nested
    @DisplayName("parsePaymentIntentData")
    class ParsePaymentIntentDataTests {

        @Test
        @DisplayName("should parse payment intent data")
        void shouldParsePaymentIntentData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ACQUIRING\","
                    + "\"event_type\":\"acquiring.payment_intent.succeeded\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"pi_1\","
                    + "\"data\":{"
                    + "  \"payment_intent_id\":\"pi_1\","
                    + "  \"amount\":\"101\","
                    + "  \"currency\":\"USD\","
                    + "  \"description\":\"Test payment\","
                    + "  \"intent_status\":\"SUCCEEDED\","
                    + "  \"merchant_order_id\":\"order_1\","
                    + "  \"metadata\":{\"key1\":\"val1\"},"
                    + "  \"create_time\":\"2024-01-01T00:00:00Z\","
                    + "  \"complete_time\":\"2024-01-01T00:01:00Z\","
                    + "  \"payment_method\":{\"type\":\"card\",\"card\":{\"brand\":\"visa\",\"last4\":\"4242\",\"exp_month\":12,\"exp_year\":2025}}"
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            PaymentIntentData intent = event.parsePaymentIntentData();

            assertThat(intent.getPaymentIntentId()).isEqualTo("pi_1");
            assertThat(intent.getAmount()).isEqualTo("101");
            assertThat(intent.getCurrency()).isEqualTo("USD");
            assertThat(intent.getDescription()).isEqualTo("Test payment");
            assertThat(intent.getIntentStatus()).isEqualTo("SUCCEEDED");
            assertThat(intent.getMerchantOrderId()).isEqualTo("order_1");
            assertThat(intent.getMetadataAsMap()).containsEntry("key1", "val1");
            assertThat(intent.getCreateTime()).isEqualTo("2024-01-01T00:00:00Z");
            assertThat(intent.getCompleteTime()).isEqualTo("2024-01-01T00:01:00Z");
            assertThat(intent.getPaymentMethod()).isNotNull();
            assertThat(intent.getPaymentMethod().getType()).isEqualTo("card");
            assertThat(intent.getPaymentMethod().getCard()).isNotNull();
            assertThat(intent.getPaymentMethod().getCard().getBrand()).isEqualTo("visa");
            assertThat(intent.getPaymentMethod().getCard().getLast4()).isEqualTo("4242");
            assertThat(intent.getPaymentMethod().getCard().getExpMonth()).isEqualTo(12);
            assertThat(intent.getPaymentMethod().getCard().getExpYear()).isEqualTo(2025);
        }

        @Test
        @DisplayName("should throw on wrong event type")
        void shouldThrowOnWrongEventType() {
            Event event = createEventWithData("ONBOARDING", "onboarding.account.create",
                    "{\"account_id\":\"acc_1\"}");

            assertThatThrownBy(() -> event.parsePaymentIntentData())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("is not a payment intent event");
        }
    }

    // =========================================================================
    // parsePaymentAttemptData
    // =========================================================================

    @Nested
    @DisplayName("parsePaymentAttemptData")
    class ParsePaymentAttemptDataTests {

        @Test
        @DisplayName("should parse payment attempt data")
        void shouldParsePaymentAttemptData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ACQUIRING\","
                    + "\"event_type\":\"acquiring.payment_attempt.succeeded\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"pa_1\","
                    + "\"data\":{"
                    + "  \"payment_attempt_id\":\"pa_1\","
                    + "  \"payment_intent_id\":\"pi_1\","
                    + "  \"amount\":\"0.01\","
                    + "  \"currency\":\"USD\","
                    + "  \"attempt_status\":\"SUCCEEDED\","
                    + "  \"captured_amount\":\"0.01\","
                    + "  \"refunded_amount\":\"0\","
                    + "  \"create_time\":\"2024-01-01T00:00:00Z\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            PaymentAttemptData attempt = event.parsePaymentAttemptData();

            assertThat(attempt.getPaymentAttemptId()).isEqualTo("pa_1");
            assertThat(attempt.getPaymentIntentId()).isEqualTo("pi_1");
            assertThat(attempt.getAmount()).isEqualTo("0.01");
            assertThat(attempt.getCurrency()).isEqualTo("USD");
            assertThat(attempt.getAttemptStatus()).isEqualTo("SUCCEEDED");
            assertThat(attempt.getCapturedAmount()).isEqualTo("0.01");
            assertThat(attempt.getRefundedAmount()).isEqualTo("0");
        }

        @Test
        @DisplayName("should throw on wrong event type")
        void shouldThrowOnWrongEventType() {
            Event event = createEventWithData("ACQUIRING", "acquiring.refund.created",
                    "{\"payment_refund_id\":\"rf_1\"}");

            assertThatThrownBy(() -> event.parsePaymentAttemptData())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("is not a payment attempt event");
        }
    }

    // =========================================================================
    // parseRefundData
    // =========================================================================

    @Nested
    @DisplayName("parseRefundData")
    class ParseRefundDataTests {

        @Test
        @DisplayName("should parse refund data")
        void shouldParseRefundData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ACQUIRING\","
                    + "\"event_type\":\"acquiring.refund.succeeded\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"rf_1\","
                    + "\"data\":{"
                    + "  \"payment_refund_id\":\"rf_1\","
                    + "  \"payment_intent_id\":\"pi_1\","
                    + "  \"payment_attempt_id\":\"pa_1\","
                    + "  \"amount\":\"0.001\","
                    + "  \"currency\":\"USD\","
                    + "  \"refund_status\":\"SUCCEEDED\","
                    + "  \"reason\":\"requested_by_customer\","
                    + "  \"metadata\":{\"ref\":\"test\"}"
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            RefundData refund = event.parseRefundData();

            assertThat(refund.getPaymentRefundId()).isEqualTo("rf_1");
            assertThat(refund.getPaymentIntentId()).isEqualTo("pi_1");
            assertThat(refund.getPaymentAttemptId()).isEqualTo("pa_1");
            assertThat(refund.getAmount()).isEqualTo("0.001");
            assertThat(refund.getRefundStatus()).isEqualTo("SUCCEEDED");
            assertThat(refund.getReason()).isEqualTo("requested_by_customer");
            assertThat(refund.getMetadataAsMap()).containsEntry("ref", "test");
        }

        @Test
        @DisplayName("should throw on wrong event type")
        void shouldThrowOnWrongEventType() {
            Event event = createEventWithData("ACQUIRING", "acquiring.payment_intent.created",
                    "{\"payment_intent_id\":\"pi_1\"}");

            assertThatThrownBy(() -> event.parseRefundData())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("is not a refund event");
        }
    }

    // =========================================================================
    // parseConversionData
    // =========================================================================

    @Nested
    @DisplayName("parseConversionData")
    class ParseConversionDataTests {

        @Test
        @DisplayName("should parse conversion data")
        void shouldParseConversionData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"CONVERSION\","
                    + "\"event_type\":\"conversion.trade.settled\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"conv_1\","
                    + "\"data\":{"
                    + "  \"account_id\":\"acc_1\","
                    + "  \"account_name\":\"Test Account\","
                    + "  \"buy_amount\":\"38.51\","
                    + "  \"buy_currency\":\"SGD\","
                    + "  \"client_rate\":\"1.35\","
                    + "  \"conversion_id\":\"conv_1\","
                    + "  \"conversion_status\":\"TRADE_SETTLED\","
                    + "  \"conversion_way\":\"API\","
                    + "  \"create_time\":\"2024-01-01T00:00:00Z\","
                    + "  \"creator\":\"admin\","
                    + "  \"direct_id\":\"dir_1\","
                    + "  \"sell_amount\":\"100\","
                    + "  \"sell_currency\":\"USD\","
                    + "  \"settle_time\":\"2024-01-02T00:00:00Z\","
                    + "  \"short_reference_id\":\"ref_1\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            ConversionData conversion = event.parseConversionData();

            assertThat(conversion.getAccountId()).isEqualTo("acc_1");
            assertThat(conversion.getAccountName()).isEqualTo("Test Account");
            assertThat(conversion.getBuyAmount()).isEqualTo("38.51");
            assertThat(conversion.getBuyCurrency()).isEqualTo("SGD");
            assertThat(conversion.getClientRate()).isEqualTo("1.35");
            assertThat(conversion.getConversionId()).isEqualTo("conv_1");
            assertThat(conversion.getConversionStatus()).isEqualTo("TRADE_SETTLED");
            assertThat(conversion.getConversionWay()).isEqualTo("API");
            assertThat(conversion.getSellAmount()).isEqualTo("100");
            assertThat(conversion.getSellCurrency()).isEqualTo("USD");
            assertThat(conversion.getSettleTime()).isEqualTo("2024-01-02T00:00:00Z");
            assertThat(conversion.getShortReferenceId()).isEqualTo("ref_1");
        }

        @Test
        @DisplayName("should throw on wrong event type")
        void shouldThrowOnWrongEventType() {
            Event event = createEventWithData("ACQUIRING", "acquiring.refund.created",
                    "{\"payment_refund_id\":\"rf_1\"}");

            assertThatThrownBy(() -> event.parseConversionData())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("is not a conversion event");
        }
    }

    // =========================================================================
    // parseCardData
    // =========================================================================

    @Nested
    @DisplayName("parseCardData")
    class ParseCardDataTests {

        @Test
        @DisplayName("should parse card create data")
        void shouldParseCardCreateData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ISSUING\","
                    + "\"event_type\":\"card.create.succeeded\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"card_1\","
                    + "\"data\":{"
                    + "  \"card_id\":\"card_1\","
                    + "  \"card_product_id\":\"cp_1\","
                    + "  \"card_number\":\"49372418****4306\","
                    + "  \"card_bin\":\"493724\","
                    + "  \"card_scheme\":\"VISA\","
                    + "  \"card_status\":\"ACTIVE\","
                    + "  \"card_currency\":\"USD\","
                    + "  \"card_limit\":\"1000\","
                    + "  \"card_available_balance\":\"500\","
                    + "  \"form_factor\":\"VIRTUAL\","
                    + "  \"mode_type\":\"MULTI\","
                    + "  \"order_status\":\"success\","
                    + "  \"cardholder\":{\"cardholder_id\":\"ch_1\",\"cardholder_status\":\"SUCCESS\",\"first_name\":\"John\",\"last_name\":\"Doe\",\"number_of_cards\":2},"
                    + "  \"spending_limits\":[{\"amount\":\"500\",\"interval\":\"DAILY\"},{\"amount\":\"100\",\"interval\":\"PER_TRANSACTION\"}],"
                    + "  \"risk_control\":{\"allow_3ds_transactions\":\"Y\",\"allow_online_transactions\":\"Y\",\"allow_atm_transactions\":\"N\"},"
                    + "  \"metadata\":{\"team\":\"engineering\"}"
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            CardData card = event.parseCardData();

            assertThat(card.getCardId()).isEqualTo("card_1");
            assertThat(card.getCardProductId()).isEqualTo("cp_1");
            assertThat(card.getCardNumber()).isEqualTo("49372418****4306");
            assertThat(card.getCardBin()).isEqualTo("493724");
            assertThat(card.getCardScheme()).isEqualTo("VISA");
            assertThat(card.getCardStatus()).isEqualTo("ACTIVE");
            assertThat(card.getCardCurrency()).isEqualTo("USD");
            assertThat(card.getCardLimit()).isEqualTo("1000");
            assertThat(card.getCardAvailableBalance()).isEqualTo("500");
            assertThat(card.getFormFactor()).isEqualTo("VIRTUAL");
            assertThat(card.getModeType()).isEqualTo("MULTI");
            assertThat(card.getOrderStatus()).isEqualTo("success");

            // Cardholder
            assertThat(card.getCardholder()).isNotNull();
            assertThat(card.getCardholder().getCardholderId()).isEqualTo("ch_1");
            assertThat(card.getCardholder().getCardholderStatus()).isEqualTo("SUCCESS");
            assertThat(card.getCardholder().getFirstName()).isEqualTo("John");
            assertThat(card.getCardholder().getNumberOfCards()).isEqualTo(2);

            // Spending limits
            assertThat(card.getSpendingLimits()).hasSize(2);
            assertThat(card.getSpendingLimits().get(0).getAmount()).isEqualTo("500");
            assertThat(card.getSpendingLimits().get(0).getInterval()).isEqualTo("DAILY");

            // Risk control
            assertThat(card.getRiskControl()).isNotNull();
            assertThat(card.getRiskControl().getAllow3dsTransactions()).isEqualTo("Y");
            assertThat(card.getRiskControl().getAllowAtmTransactions()).isEqualTo("N");

            // Metadata
            assertThat(card.getMetadata()).containsEntry("team", "engineering");

            // Helper methods
            assertThat(card.getEffectiveAvailableBalance()).isEqualTo("500");
            assertThat(card.getEffectiveSpendingLimits()).hasSize(2);
        }

        @Test
        @DisplayName("helper getEffectiveAvailableBalance prefers availableBalance")
        void helperPrefersAvailableBalance() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ISSUING\","
                    + "\"event_type\":\"card.update.succeeded\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"card_1\","
                    + "\"data\":{"
                    + "  \"card_id\":\"card_1\","
                    + "  \"card_number\":\"49372418****4306\","
                    + "  \"card_scheme\":\"VISA\","
                    + "  \"card_status\":\"ACTIVE\","
                    + "  \"form_factor\":\"VIRTUAL\","
                    + "  \"mode_type\":\"MULTI\","
                    + "  \"card_available_balance\":\"500\","
                    + "  \"available_balance\":\"600\","
                    + "  \"spending_limits\":[{\"amount\":\"100\",\"interval\":\"DAILY\"}],"
                    + "  \"spending_controls\":[{\"amount\":\"200\",\"interval\":\"MONTHLY\"}]"
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            CardData card = event.parseCardData();

            assertThat(card.getEffectiveAvailableBalance()).isEqualTo("600");
            assertThat(card.getEffectiveSpendingLimits()).hasSize(1);
            assertThat(card.getEffectiveSpendingLimits().get(0).getAmount()).isEqualTo("200");
            assertThat(card.getEffectiveSpendingLimits().get(0).getInterval()).isEqualTo("MONTHLY");
        }

        @Test
        @DisplayName("should throw on wrong event type")
        void shouldThrowOnWrongEventType() {
            Event event = createEventWithData("ISSUING", "card.recharge.succeeded",
                    "{\"card_id\":\"card_1\"}");

            assertThatThrownBy(() -> event.parseCardData())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("is not a card create/update event");
        }
    }

    // =========================================================================
    // parseCardActivationCodeData
    // =========================================================================

    @Nested
    @DisplayName("parseCardActivationCodeData")
    class ParseCardActivationCodeDataTests {

        @Test
        @DisplayName("should parse card activation code data")
        void shouldParseCardActivationCodeData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ISSUING\","
                    + "\"event_type\":\"card.activation.code\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"card_1\","
                    + "\"data\":{"
                    + "  \"card_id\":\"card_1\","
                    + "  \"card_number\":\"12345678****0000\","
                    + "  \"activation_code\":\"ABC123\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            CardActivationCodeData data = event.parseCardActivationCodeData();

            assertThat(data.getCardId()).isEqualTo("card_1");
            assertThat(data.getCardNumber()).isEqualTo("12345678****0000");
            assertThat(data.getActivationCode()).isEqualTo("ABC123");
        }

        @Test
        @DisplayName("should throw on wrong event type")
        void shouldThrowOnWrongEventType() {
            Event event = createEventWithData("ISSUING", "card.create.succeeded",
                    "{\"card_id\":\"card_1\"}");

            assertThatThrownBy(() -> event.parseCardActivationCodeData())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("is not a card activation code event");
        }
    }

    // =========================================================================
    // parseCardRechargeData
    // =========================================================================

    @Nested
    @DisplayName("parseCardRechargeData")
    class ParseCardRechargeDataTests {

        @Test
        @DisplayName("should parse card recharge data")
        void shouldParseCardRechargeData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ISSUING\","
                    + "\"event_type\":\"card.recharge.succeeded\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"card_1\","
                    + "\"data\":{"
                    + "  \"card_id\":\"card_1\","
                    + "  \"amount\":\"100\","
                    + "  \"card_currency\":\"USD\","
                    + "  \"card_available_balance\":\"600\","
                    + "  \"card_status\":\"ACTIVE\","
                    + "  \"order_status\":\"SUCCESS\","
                    + "  \"complete_time\":\"2024-01-01T00:00:00Z\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            CardRechargeData data = event.parseCardRechargeData();

            assertThat(data.getCardId()).isEqualTo("card_1");
            assertThat(data.getAmount()).isEqualTo("100");
            assertThat(data.getCardCurrency()).isEqualTo("USD");
            assertThat(data.getCardAvailableBalance()).isEqualTo("600");
            assertThat(data.getCardStatus()).isEqualTo("ACTIVE");
            assertThat(data.getOrderStatus()).isEqualTo("SUCCESS");
            assertThat(data.getCompleteTime()).isEqualTo("2024-01-01T00:00:00Z");
        }

        @Test
        @DisplayName("should throw on wrong event type")
        void shouldThrowOnWrongEventType() {
            Event event = createEventWithData("ISSUING", "card.create.succeeded",
                    "{\"card_id\":\"card_1\"}");

            assertThatThrownBy(() -> event.parseCardRechargeData())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("is not a card recharge event");
        }
    }

    // =========================================================================
    // parseCardStatusUpdateData
    // =========================================================================

    @Nested
    @DisplayName("parseCardStatusUpdateData")
    class ParseCardStatusUpdateDataTests {

        @Test
        @DisplayName("should parse card status update data")
        void shouldParseCardStatusUpdateData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ISSUING\","
                    + "\"event_type\":\"card.status.update.succeeded\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"card_1\","
                    + "\"data\":{"
                    + "  \"card_id\":\"card_1\","
                    + "  \"card_number\":\"49372418****4306\","
                    + "  \"card_status\":\"SUSPENDED\","
                    + "  \"update_reason\":\"User request\","
                    + "  \"update_time\":\"2024-01-01T00:00:00Z\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            CardStatusUpdateData data = event.parseCardStatusUpdateData();

            assertThat(data.getCardId()).isEqualTo("card_1");
            assertThat(data.getCardNumber()).isEqualTo("49372418****4306");
            assertThat(data.getCardStatus()).isEqualTo("SUSPENDED");
            assertThat(data.getUpdateReason()).isEqualTo("User request");
            assertThat(data.getUpdateTime()).isEqualTo("2024-01-01T00:00:00Z");
        }

        @Test
        @DisplayName("should throw on wrong event type")
        void shouldThrowOnWrongEventType() {
            Event event = createEventWithData("ISSUING", "card.create.succeeded",
                    "{\"card_id\":\"card_1\"}");

            assertThatThrownBy(() -> event.parseCardStatusUpdateData())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("is not a card status update event");
        }
    }

    // =========================================================================
    // parseCardTransactionData
    // =========================================================================

    @Nested
    @DisplayName("parseCardTransactionData")
    class ParseCardTransactionDataTests {

        @Test
        @DisplayName("should parse card transaction data")
        void shouldParseCardTransactionData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ISSUING\","
                    + "\"event_type\":\"issuing.fee.card\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"card_1\","
                    + "\"data\":{"
                    + "  \"card_id\":\"card_1\","
                    + "  \"card_number\":\"49372418****4306\","
                    + "  \"cardholder_id\":\"ch_1\","
                    + "  \"card_available_balance\":\"450\","
                    + "  \"transaction_amount\":\"50\","
                    + "  \"transaction_currency\":\"USD\","
                    + "  \"billing_amount\":\"50\","
                    + "  \"billing_currency\":\"USD\","
                    + "  \"transaction_status\":\"APPROVED\","
                    + "  \"transaction_type\":\"FEE\","
                    + "  \"transaction_time\":\"2024-01-01T00:00:00Z\","
                    + "  \"posted_time\":\"2024-01-01T00:01:00Z\","
                    + "  \"reference_id\":\"txn_1\","
                    + "  \"short_reference_id\":\"ref_1\","
                    + "  \"remark\":\"Monthly fee\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            CardTransactionData data = event.parseCardTransactionData();

            assertThat(data.getCardId()).isEqualTo("card_1");
            assertThat(data.getCardNumber()).isEqualTo("49372418****4306");
            assertThat(data.getCardholderId()).isEqualTo("ch_1");
            assertThat(data.getCardAvailableBalance()).isEqualTo("450");
            assertThat(data.getTransactionAmount()).isEqualTo("50");
            assertThat(data.getTransactionCurrency()).isEqualTo("USD");
            assertThat(data.getBillingAmount()).isEqualTo("50");
            assertThat(data.getBillingCurrency()).isEqualTo("USD");
            assertThat(data.getTransactionStatus()).isEqualTo("APPROVED");
            assertThat(data.getTransactionType()).isEqualTo("FEE");
            assertThat(data.getTransactionTime()).isEqualTo("2024-01-01T00:00:00Z");
            assertThat(data.getPostedTime()).isEqualTo("2024-01-01T00:01:00Z");
            assertThat(data.getReferenceId()).isEqualTo("txn_1");
            assertThat(data.getShortReferenceId()).isEqualTo("ref_1");
            assertThat(data.getRemark()).isEqualTo("Monthly fee");
        }

        @Test
        @DisplayName("should throw on wrong event type")
        void shouldThrowOnWrongEventType() {
            Event event = createEventWithData("ISSUING", "card.create.succeeded",
                    "{\"card_id\":\"card_1\"}");

            assertThatThrownBy(() -> event.parseCardTransactionData())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("is not a card transaction event");
        }
    }

    // =========================================================================
    // parseBeneficiaryData
    // =========================================================================

    @Nested
    @DisplayName("parseBeneficiaryData")
    class ParseBeneficiaryDataTests {

        @Test
        @DisplayName("should parse beneficiary data with helper methods")
        void shouldParseBeneficiaryData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"BENEFICIARY\","
                    + "\"event_type\":\"beneficiary.successful\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"ben_1\","
                    + "\"data\":{"
                    + "  \"account_id\":\"acc_1\","
                    + "  \"account_number\":\"1234567890\","
                    + "  \"account_currency_code\":\"USD\","
                    + "  \"beneficiary_id\":\"ben_1\","
                    + "  \"beneficiary_first_name\":\"Jane\","
                    + "  \"beneficiary_last_name\":\"Smith\","
                    + "  \"beneficiary_nickname\":\"jsmith\","
                    + "  \"beneficiary_company_name\":\"\","
                    + "  \"beneficiary_email\":\"jane@example.com\","
                    + "  \"beneficiary_entity_type\":\"INDIVIDUAL\","
                    + "  \"beneficiary_status\":\"ACTIVE\","
                    + "  \"beneficiary_address\":\"{\\\"nationality\\\":\\\"US\\\",\\\"city\\\":\\\"New York\\\",\\\"postal_code\\\":\\\"10001\\\"}\","
                    + "  \"beneficiary_bank_details\":\"{\\\"bank_name\\\":\\\"Chase\\\",\\\"account_number\\\":\\\"9876543210\\\",\\\"swift_code\\\":\\\"CHASUS33\\\"}\","
                    + "  \"bank_country_code\":\"US\","
                    + "  \"payment_type\":\"LOCAL\","
                    + "  \"short_reference_id\":\"ref_1\","
                    + "  \"direct_id\":\"dir_1\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            BeneficiaryData ben = event.parseBeneficiaryData();

            assertThat(ben.getAccountId()).isEqualTo("acc_1");
            assertThat(ben.getBeneficiaryId()).isEqualTo("ben_1");
            assertThat(ben.getBeneficiaryFirstName()).isEqualTo("Jane");
            assertThat(ben.getBeneficiaryLastName()).isEqualTo("Smith");
            assertThat(ben.getBeneficiaryEntityType()).isEqualTo("INDIVIDUAL");
            assertThat(ben.getBeneficiaryStatus()).isEqualTo("ACTIVE");
            assertThat(ben.getPaymentType()).isEqualTo("LOCAL");

            // Helper methods
            assertThat(ben.getFullName()).isEqualTo("Jane Smith");
            assertThat(ben.isIndividual()).isTrue();
            assertThat(ben.isCompany()).isFalse();
            assertThat(ben.isActive()).isTrue();
            assertThat(ben.isLocalPayment()).isTrue();
            assertThat(ben.isInternationalPayment()).isFalse();

            // Parsed address
            BeneficiaryAddress address = ben.getBeneficiaryAddressParsed();
            assertThat(address).isNotNull();
            assertThat(address.getNationality()).isEqualTo("US");
            assertThat(address.getCity()).isEqualTo("New York");
            assertThat(address.getPostalCode()).isEqualTo("10001");

            // Parsed bank details
            BeneficiaryBankDetails bank = ben.getBeneficiaryBankDetailsParsed();
            assertThat(bank).isNotNull();
            assertThat(bank.getBankName()).isEqualTo("Chase");
            assertThat(bank.getAccountNumber()).isEqualTo("9876543210");
            assertThat(bank.getSwiftCode()).isEqualTo("CHASUS33");
        }

        @Test
        @DisplayName("getFullName should handle missing names")
        void getFullNameHandlesMissingNames() {
            BeneficiaryData ben = new BeneficiaryData();

            // Both empty
            assertThat(ben.getFullName()).isEqualTo("");

            // Only first name
            ben.setBeneficiaryFirstName("Jane");
            assertThat(ben.getFullName()).isEqualTo("Jane");

            // Only last name
            ben.setBeneficiaryFirstName(null);
            ben.setBeneficiaryLastName("Smith");
            assertThat(ben.getFullName()).isEqualTo("Smith");
        }

        @Test
        @DisplayName("parsed helpers return null for empty strings")
        void parsedHelpersReturnNullForEmpty() {
            BeneficiaryData ben = new BeneficiaryData();
            assertThat(ben.getBeneficiaryAddressParsed()).isNull();
            assertThat(ben.getBeneficiaryBankDetailsParsed()).isNull();
        }

        @Test
        @DisplayName("should throw on wrong event type")
        void shouldThrowOnWrongEventType() {
            Event event = createEventWithData("ACQUIRING", "acquiring.payment_intent.created",
                    "{\"payment_intent_id\":\"pi_1\"}");

            assertThatThrownBy(() -> event.parseBeneficiaryData())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("is not a beneficiary event");
        }
    }

    // =========================================================================
    // parseDepositData
    // =========================================================================

    @Nested
    @DisplayName("parseDepositData")
    class ParseDepositDataTests {

        @Test
        @DisplayName("should parse deposit event data")
        void shouldParseDepositData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"DEPOSIT\","
                    + "\"event_type\":\"deposit.completed\","
                    + "\"event_id\":\"evt_1\","
                    + "\"data\":{"
                    + "  \"deposit_id\":\"dep_123\","
                    + "  \"currency\":\"USD\","
                    + "  \"amount\":\"100.00\","
                    + "  \"deposit_status\":\"COMPLETED\","
                    + "  \"short_reference_id\":\"ref_dep_1\","
                    + "  \"sender_name\":\"Alice\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            DepositEventData data = event.parseDepositData();

            assertThat(data.getDepositId()).isEqualTo("dep_123");
            assertThat(data.getCurrency()).isEqualTo("USD");
            assertThat(data.getAmount()).isEqualTo("100.00");
            assertThat(data.getDepositStatus()).isEqualTo("COMPLETED");
            assertThat(data.getShortReferenceId()).isEqualTo("ref_dep_1");
            assertThat(data.getSenderName()).isEqualTo("Alice");
        }
    }

    // =========================================================================
    // parsePayoutBankingData
    // =========================================================================

    @Nested
    @DisplayName("parsePayoutBankingData")
    class ParsePayoutBankingDataTests {

        @Test
        @DisplayName("should parse payout banking event data")
        void shouldParsePayoutBankingData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"PAYOUT\","
                    + "\"event_type\":\"payout.completed\","
                    + "\"event_id\":\"evt_1\","
                    + "\"data\":{"
                    + "  \"payout_id\":\"pyo_123\","
                    + "  \"amount\":\"500.00\","
                    + "  \"currency\":\"USD\","
                    + "  \"payout_status\":\"COMPLETED\","
                    + "  \"beneficiary_id\":\"ben_456\","
                    + "  \"short_reference_id\":\"ref_pyo_1\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            PayoutEventData data = event.parsePayoutBankingData();

            assertThat(data.getPayoutId()).isEqualTo("pyo_123");
            assertThat(data.getAmount()).isEqualTo("500.00");
            assertThat(data.getCurrency()).isEqualTo("USD");
            assertThat(data.getPayoutStatus()).isEqualTo("COMPLETED");
            assertThat(data.getBeneficiaryId()).isEqualTo("ben_456");
            assertThat(data.getShortReferenceId()).isEqualTo("ref_pyo_1");
        }
    }

    // =========================================================================
    // parseVirtualAccountData
    // =========================================================================

    @Nested
    @DisplayName("parseVirtualAccountData")
    class ParseVirtualAccountDataTests {

        @Test
        @DisplayName("should parse virtual account event data")
        void shouldParseVirtualAccountData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"VIRTUAL\","
                    + "\"event_type\":\"virtual.account.create\","
                    + "\"event_id\":\"evt_1\","
                    + "\"data\":{"
                    + "  \"virtual_account_id\":\"va_789\","
                    + "  \"account_name\":\"Main Account\","
                    + "  \"currency\":\"SGD\","
                    + "  \"status\":\"ACTIVE\","
                    + "  \"payment_method\":\"LOCAL\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            VirtualAccountEventData data = event.parseVirtualAccountData();

            assertThat(data.getVirtualAccountId()).isEqualTo("va_789");
            assertThat(data.getAccountName()).isEqualTo("Main Account");
            assertThat(data.getCurrency()).isEqualTo("SGD");
            assertThat(data.getStatus()).isEqualTo("ACTIVE");
            assertThat(data.getPaymentMethod()).isEqualTo("LOCAL");
        }
    }

    // =========================================================================
    // parseTransferData
    // =========================================================================

    @Nested
    @DisplayName("parseTransferData")
    class ParseTransferDataTests {

        @Test
        @DisplayName("should parse transfer event data")
        void shouldParseTransferData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"TRANSFER\","
                    + "\"event_type\":\"transfer.completed\","
                    + "\"event_id\":\"evt_1\","
                    + "\"data\":{"
                    + "  \"transfer_id\":\"txf_001\","
                    + "  \"short_reference_id\":\"ref_txf_1\","
                    + "  \"source_account_id\":\"src_acc_1\","
                    + "  \"destination_account_id\":\"dst_acc_2\","
                    + "  \"amount\":\"200.00\","
                    + "  \"currency\":\"USD\","
                    + "  \"transfer_status\":\"COMPLETED\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            TransferEventData data = event.parseTransferData();

            assertThat(data.getTransferId()).isEqualTo("txf_001");
            assertThat(data.getShortReferenceId()).isEqualTo("ref_txf_1");
            assertThat(data.getSourceAccountId()).isEqualTo("src_acc_1");
            assertThat(data.getDestinationAccountId()).isEqualTo("dst_acc_2");
            assertThat(data.getAmount()).isEqualTo("200.00");
            assertThat(data.getCurrency()).isEqualTo("USD");
            assertThat(data.getTransferStatus()).isEqualTo("COMPLETED");
        }
    }

    // =========================================================================
    // parseCardholderData
    // =========================================================================

    @Nested
    @DisplayName("parseCardholderData")
    class ParseCardholderDataTests {

        @Test
        @DisplayName("should parse cardholder event data")
        void shouldParseCardholderData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"cardholder.kyc.status_changed\","
                    + "\"event_type\":\"cardholder.kyc.status_changed\","
                    + "\"event_id\":\"evt_1\","
                    + "\"data\":{"
                    + "  \"cardholder_id\":\"ch_abc\","
                    + "  \"cardholder_status\":\"APPROVED\","
                    + "  \"first_name\":\"Bob\","
                    + "  \"last_name\":\"Jones\","
                    + "  \"email\":\"bob@example.com\","
                    + "  \"phone_number\":\"+15550001234\","
                    + "  \"country_code\":\"US\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            CardholderData data = event.parseCardholderData();

            assertThat(data.getCardholderId()).isEqualTo("ch_abc");
            assertThat(data.getCardholderStatus()).isEqualTo("APPROVED");
            assertThat(data.getFirstName()).isEqualTo("Bob");
            assertThat(data.getLastName()).isEqualTo("Jones");
            assertThat(data.getEmail()).isEqualTo("bob@example.com");
            assertThat(data.getPhoneNumber()).isEqualTo("+15550001234");
            assertThat(data.getCountryCode()).isEqualTo("US");
        }
    }

    // =========================================================================
    // parseCardWithdrawData
    // =========================================================================

    @Nested
    @DisplayName("parseCardWithdrawData")
    class ParseCardWithdrawDataTests {

        @Test
        @DisplayName("should parse card withdraw event data")
        void shouldParseCardWithdrawData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ISSUING\","
                    + "\"event_type\":\"card.withdraw.succeeded\","
                    + "\"event_id\":\"evt_1\","
                    + "\"data\":{"
                    + "  \"card_id\":\"card_w1\","
                    + "  \"card_order_id\":\"ord_w1\","
                    + "  \"order_type\":\"WITHDRAW\","
                    + "  \"amount\":\"50.00\","
                    + "  \"card_currency\":\"USD\","
                    + "  \"create_time\":\"2024-06-01T10:00:00Z\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            CardWithdrawData data = event.parseCardWithdrawData();

            assertThat(data.getCardId()).isEqualTo("card_w1");
            assertThat(data.getCardOrderId()).isEqualTo("ord_w1");
            assertThat(data.getOrderType()).isEqualTo("WITHDRAW");
            assertThat(data.getAmount()).isEqualTo("50.00");
            assertThat(data.getCardCurrency()).isEqualTo("USD");
            assertThat(data.getCreateTime()).isEqualTo("2024-06-01T10:00:00Z");
        }
    }

    // =========================================================================
    // parseCardOtpData
    // =========================================================================

    @Nested
    @DisplayName("parseCardOtpData")
    class ParseCardOtpDataTests {

        @Test
        @DisplayName("should parse card OTP event data")
        void shouldParseCardOtpData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ISSUING\","
                    + "\"event_type\":\"card.3ds.otp\","
                    + "\"event_id\":\"evt_1\","
                    + "\"data\":{"
                    + "  \"card_id\":\"card_otp1\","
                    + "  \"cardholder_id\":\"ch_otp1\","
                    + "  \"transaction_id\":\"txn_otp1\","
                    + "  \"otp\":\"847291\","
                    + "  \"otp_type\":\"3DS\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            CardOtpData data = event.parseCardOtpData();

            assertThat(data.getCardId()).isEqualTo("card_otp1");
            assertThat(data.getCardholderId()).isEqualTo("ch_otp1");
            assertThat(data.getTransactionId()).isEqualTo("txn_otp1");
            assertThat(data.getOtp()).isEqualTo("847291");
            assertThat(data.getOtpType()).isEqualTo("3DS");
        }
    }

    // =========================================================================
    // parseRfiData
    // =========================================================================

    @Nested
    @DisplayName("parseRfiData")
    class ParseRfiDataTests {

        @Test
        @DisplayName("should parse RFI event data")
        void shouldParseRfiData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"RFI\","
                    + "\"event_type\":\"rfi.action_required\","
                    + "\"event_id\":\"evt_1\","
                    + "\"data\":{"
                    + "  \"account_id\":\"acc_rfi_1\","
                    + "  \"rfi_type\":\"KYC\","
                    + "  \"rfi_status\":\"PENDING\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            RfiEventData data = event.parseRfiData();

            assertThat(data.getAccountId()).isEqualTo("acc_rfi_1");
            assertThat(data.getRfiType()).isEqualTo("KYC");
            assertThat(data.getRfiStatus()).isEqualTo("PENDING");
        }
    }

    // =========================================================================
    // parseSettlementData
    // =========================================================================

    @Nested
    @DisplayName("parseSettlementData")
    class ParseSettlementDataTests {

        @Test
        @DisplayName("should parse settlement event data")
        void shouldParseSettlementData() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"ACQUIRING\","
                    + "\"event_type\":\"acquiring.payment.settlement\","
                    + "\"event_id\":\"evt_1\","
                    + "\"data\":{"
                    + "  \"settlement_batch_id\":\"batch_001\","
                    + "  \"settled_amount\":\"9999.99\","
                    + "  \"currency\":\"USD\","
                    + "  \"settled_time\":\"2024-07-01T08:00:00Z\""
                    + "}"
                    + "}";

            Event event = Event.fromJson(json);
            SettlementEventData data = event.parseSettlementData();

            assertThat(data.getSettlementBatchId()).isEqualTo("batch_001");
            assertThat(data.getSettledAmount()).isEqualTo("9999.99");
            assertThat(data.getCurrency()).isEqualTo("USD");
            assertThat(data.getSettledTime()).isEqualTo("2024-07-01T08:00:00Z");
        }
    }

    // =========================================================================
    // Generic parseData
    // =========================================================================

    @Nested
    @DisplayName("parseData")
    class ParseDataTests {

        @Test
        @DisplayName("should parse data with generic method")
        void shouldParseWithGenericMethod() {
            String json = "{"
                    + "\"version\":\"V1.6.0\","
                    + "\"event_name\":\"CONVERSION\","
                    + "\"event_type\":\"conversion.trade.settled\","
                    + "\"event_id\":\"evt_1\","
                    + "\"source_id\":\"conv_1\","
                    + "\"data\":{\"conversion_id\":\"conv_1\",\"conversion_status\":\"TRADE_SETTLED\"}"
                    + "}";

            Event event = Event.fromJson(json);
            ConversionData data = event.parseData(ConversionData.class);

            assertThat(data.getConversionId()).isEqualTo("conv_1");
            assertThat(data.getConversionStatus()).isEqualTo("TRADE_SETTLED");
        }

        @Test
        @DisplayName("should throw when data is null")
        void shouldThrowWhenDataIsNull() {
            Event event = new Event();
            assertThatThrownBy(() -> event.parseData(ConversionData.class))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Event data is null");
        }
    }

    // =========================================================================
    // Helpers
    // =========================================================================

    private Event createEvent(String eventName, String eventType) {
        String json = "{"
                + "\"event_name\":\"" + eventName + "\","
                + "\"event_type\":\"" + eventType + "\","
                + "\"event_id\":\"evt_test\","
                + "\"source_id\":\"src_test\","
                + "\"data\":{}"
                + "}";
        return Event.fromJson(json);
    }

    private Event createEventWithData(String eventName, String eventType, String dataJson) {
        String json = "{"
                + "\"event_name\":\"" + eventName + "\","
                + "\"event_type\":\"" + eventType + "\","
                + "\"event_id\":\"evt_test\","
                + "\"source_id\":\"src_test\","
                + "\"data\":" + dataJson
                + "}";
        return Event.fromJson(json);
    }
}
