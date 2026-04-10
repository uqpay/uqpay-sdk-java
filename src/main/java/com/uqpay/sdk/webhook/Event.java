package com.uqpay.sdk.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import com.uqpay.sdk.webhook.model.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

    // =========================================================================
    // ObjectMapper
    // =========================================================================

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    // =========================================================================
    // Event name constants
    // =========================================================================

    public static final String EVENT_NAME_ONBOARDING = "ONBOARDING";
    public static final String EVENT_NAME_ACQUIRING = "ACQUIRING";
    public static final String EVENT_NAME_CONVERSION = "CONVERSION";
    public static final String EVENT_NAME_ISSUING = "ISSUING";
    public static final String EVENT_NAME_BENEFICIARY = "BENEFICIARY";
    public static final String EVENT_NAME_DEPOSIT = "DEPOSIT";
    public static final String EVENT_NAME_PAYOUT = "PAYOUT";
    public static final String EVENT_NAME_VIRTUAL = "VIRTUAL";
    public static final String EVENT_NAME_RFI = "RFI";
    public static final String EVENT_NAME_TRANSFER = "TRANSFER";
    public static final String EVENT_NAME_WITHDRAW = "WITHDRAW";
    public static final String EVENT_NAME_CARDHOLDER_KYC = "cardholder.kyc.status_changed";
    public static final String EVENT_NAME_CARDHOLDER_UPDATED = "cardholder.updated";

    // =========================================================================
    // Event type constants - Onboarding
    // =========================================================================

    public static final String EVENT_TYPE_ACCOUNT_CREATE = "onboarding.account.create";
    public static final String EVENT_TYPE_ACCOUNT_UPDATE = "onboarding.account.update";

    // =========================================================================
    // Event type constants - Acquiring (Payment Intents)
    // =========================================================================

    public static final String EVENT_TYPE_PAYMENT_INTENT_CREATED = "acquiring.payment_intent.created";
    public static final String EVENT_TYPE_PAYMENT_INTENT_SUCCEEDED = "acquiring.payment_intent.succeeded";
    public static final String EVENT_TYPE_PAYMENT_INTENT_FAILED = "acquiring.payment_intent.failed";
    public static final String EVENT_TYPE_PAYMENT_INTENT_CANCELED = "acquiring.payment_intent.canceled";

    // =========================================================================
    // Event type constants - Acquiring (Payment Attempts)
    // =========================================================================

    public static final String EVENT_TYPE_PAYMENT_ATTEMPT_CREATED = "acquiring.payment_attempt.created";
    public static final String EVENT_TYPE_PAYMENT_ATTEMPT_CAPTURE_REQUESTED = "acquiring.payment_attempt.capture_requested";
    public static final String EVENT_TYPE_PAYMENT_ATTEMPT_SUCCEEDED = "acquiring.payment_attempt.succeeded";
    public static final String EVENT_TYPE_PAYMENT_ATTEMPT_FAILED = "acquiring.payment_attempt.failed";
    public static final String EVENT_TYPE_PAYMENT_ATTEMPT_CANCELED = "acquiring.payment_attempt.canceled";

    // =========================================================================
    // Event type constants - Acquiring (Refunds)
    // =========================================================================

    public static final String EVENT_TYPE_REFUND_CREATED = "acquiring.refund.created";
    public static final String EVENT_TYPE_REFUND_SUCCEEDED = "acquiring.refund.succeeded";
    public static final String EVENT_TYPE_REFUND_FAILED = "acquiring.refund.failed";

    // =========================================================================
    // Event type constants - Conversion
    // =========================================================================

    public static final String EVENT_TYPE_CONVERSION_TRADE_SETTLED = "conversion.trade.settled";
    public static final String EVENT_TYPE_CONVERSION_FUNDS_AWAITING = "conversion.funds.awaiting";
    public static final String EVENT_TYPE_CONVERSION_FUNDS_ARRIVED = "conversion.funds.arrived";

    // =========================================================================
    // Event type constants - Issuing (Card)
    // =========================================================================

    public static final String EVENT_TYPE_CARD_CREATE_SUCCEEDED = "card.create.succeeded";
    public static final String EVENT_TYPE_CARD_CREATE_FAILED = "card.create.failed";
    public static final String EVENT_TYPE_CARD_UPDATE_SUCCEEDED = "card.update.succeeded";
    public static final String EVENT_TYPE_CARD_UPDATE_FAILED = "card.update.failed";
    public static final String EVENT_TYPE_CARD_RECHARGE_SUCCEEDED = "card.recharge.succeeded";
    public static final String EVENT_TYPE_CARD_RECHARGE_FAILED = "card.recharge.failed";
    public static final String EVENT_TYPE_CARD_ACTIVATION_CODE = "card.activation.code";
    public static final String EVENT_TYPE_CARD_ACTIVATED = "card.activated";
    public static final String EVENT_TYPE_CARD_SUSPENDED = "card.suspended";
    public static final String EVENT_TYPE_CARD_CLOSED = "card.closed";
    public static final String EVENT_TYPE_CARD_STATUS_UPDATE_SUCCEEDED = "card.status.update.succeeded";
    public static final String EVENT_TYPE_CARD_STATUS_UPDATE_FAILED = "card.status.update.failed";

    // =========================================================================
    // Event type constants - Issuing (Transaction)
    // =========================================================================

    public static final String EVENT_TYPE_ISSUING_FEE_CARD = "issuing.fee.card";

    // =========================================================================
    // Event type constants - Beneficiary
    // =========================================================================

    public static final String EVENT_TYPE_BENEFICIARY_SUCCESSFUL = "beneficiary.successful";
    public static final String EVENT_TYPE_BENEFICIARY_FAILED = "beneficiary.failed";
    public static final String EVENT_TYPE_BENEFICIARY_PENDING = "beneficiary.pending";

    // =========================================================================
    // Event type constants - Issuing (Card additional)
    // =========================================================================

    public static final String EVENT_TYPE_CARD_3DS_OTP = "card.3ds.otp";
    public static final String EVENT_TYPE_CARD_ACTIVATION_STATUS = "card.activation.status";
    public static final String EVENT_TYPE_CARD_WITHDRAW_SUCCEEDED = "card.withdraw.succeeded";
    public static final String EVENT_TYPE_CARD_VERIFICATION_OTP = "card.verification.otp";
    public static final String EVENT_TYPE_CARD_PIN_RETRY_SOFT_BLOCK = "card.PIN.retry.exceed.soft.block";

    // =========================================================================
    // Event type constants - Issuing (Transaction types)
    // =========================================================================

    public static final String EVENT_TYPE_ISSUING_REPORT_CREATED = "issuing.report.created";
    public static final String EVENT_TYPE_ISSUING_TRANSACTION_AUTHORIZATION = "issuing.transaction.authorization";
    public static final String EVENT_TYPE_ISSUING_TRANSACTION_REVERSAL = "issuing.transaction.reversal";
    public static final String EVENT_TYPE_ISSUING_TRANSACTION_REFUND = "issuing.transaction.refund";
    public static final String EVENT_TYPE_ISSUING_TRANSACTION_SETTLEMENT_CREDIT = "issuing.transaction.settlement.credit";
    public static final String EVENT_TYPE_ISSUING_TRANSACTION_SETTLEMENT_DEBIT = "issuing.transaction.settlement.debit";
    public static final String EVENT_TYPE_ISSUING_TRANSACTION_FUND_COLLECTION = "issuing.transaction.fund.collection";
    public static final String EVENT_TYPE_ISSUING_TRANSACTION_VALIDATION = "issuing.transaction.validation";
    public static final String EVENT_TYPE_ISSUING_TRANSACTION_ATM_DEPOSIT = "issuing.transaction.atm.deposit";
    public static final String EVENT_TYPE_ISSUING_TRANSACTION_CHARGEBACK_CREDIT = "issuing.transaction.chargeback.credit";
    public static final String EVENT_TYPE_ISSUING_TRANSACTION_CHARGEBACK_DEBIT = "issuing.transaction.chargeback.debit";

    // =========================================================================
    // Event type constants - Acquiring (additional)
    // =========================================================================

    public static final String EVENT_TYPE_ACQUIRING_CANCEL_SUCCEEDED = "acquiring.cancel.succeeded";
    public static final String EVENT_TYPE_ACQUIRING_PAYMENT_SETTLEMENT = "acquiring.payment.settlement";
    public static final String EVENT_TYPE_ACQUIRING_CHARGEBACK_ALERT_CREATED = "acquiring.chargeback.alert.created";
    public static final String EVENT_TYPE_ACQUIRING_PAYOUT_SUCCEEDED = "acquiring.payout.succeeded";

    // =========================================================================
    // Event type constants - Deposit
    // =========================================================================

    public static final String EVENT_TYPE_DEPOSIT_PENDING = "deposit.pending";
    public static final String EVENT_TYPE_DEPOSIT_COMPLIANCE_REJECTED = "deposit.compliance.rejected";
    public static final String EVENT_TYPE_DEPOSIT_COMPLETED = "deposit.completed";

    // =========================================================================
    // Event type constants - Payout (Banking)
    // =========================================================================

    public static final String EVENT_TYPE_PAYOUT_READY_SEND = "payout.ready.send";
    public static final String EVENT_TYPE_PAYOUT_COMPLIANCE_REJECTED = "payout.compliance.rejected";
    public static final String EVENT_TYPE_PAYOUT_COMPLETED = "payout.completed";
    public static final String EVENT_TYPE_PAYOUT_FAILED = "payout.failed";

    // =========================================================================
    // Event type constants - Virtual Account
    // =========================================================================

    public static final String EVENT_TYPE_VIRTUAL_ACCOUNT_CREATE = "virtual.account.create";
    public static final String EVENT_TYPE_VIRTUAL_ACCOUNT_UPDATE = "virtual.account.update";
    public static final String EVENT_TYPE_VIRTUAL_ACCOUNT_CLOSED = "virtual.account.closed";

    // =========================================================================
    // Event type constants - RFI
    // =========================================================================

    public static final String EVENT_TYPE_RFI_ACTION_REQUIRED = "rfi.action_required";

    // =========================================================================
    // Event type constants - Cardholder
    // =========================================================================

    public static final String EVENT_TYPE_CARDHOLDER_KYC_STATUS_CHANGED = "cardholder.kyc.status_changed";
    public static final String EVENT_TYPE_CARDHOLDER_UPDATED = "cardholder.updated";

    // =========================================================================
    // Fields
    // =========================================================================

    @JsonProperty("version")
    private String version;

    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("source_id")
    private String sourceId;

    @JsonProperty("data")
    private JsonNode data;

    public Event() {
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    // =========================================================================
    // Event category check methods
    // =========================================================================

    public boolean isOnboardingEvent() {
        return EVENT_NAME_ONBOARDING.equals(eventName);
    }

    public boolean isAcquiringEvent() {
        return EVENT_NAME_ACQUIRING.equals(eventName);
    }

    public boolean isConversionEvent() {
        return EVENT_NAME_CONVERSION.equals(eventName);
    }

    public boolean isConversionTradeSettledEvent() {
        return EVENT_TYPE_CONVERSION_TRADE_SETTLED.equals(eventType);
    }

    public boolean isIssuingEvent() {
        return EVENT_NAME_ISSUING.equals(eventName);
    }

    public boolean isBeneficiaryEvent() {
        return EVENT_NAME_BENEFICIARY.equals(eventName);
    }

    // =========================================================================
    // Specific event type check methods
    // =========================================================================

    public boolean isAccountCreateEvent() {
        return EVENT_TYPE_ACCOUNT_CREATE.equals(eventType);
    }

    public boolean isAccountUpdateEvent() {
        return EVENT_TYPE_ACCOUNT_UPDATE.equals(eventType);
    }

    public boolean isPaymentIntentEvent() {
        return EVENT_TYPE_PAYMENT_INTENT_CREATED.equals(eventType)
                || EVENT_TYPE_PAYMENT_INTENT_SUCCEEDED.equals(eventType)
                || EVENT_TYPE_PAYMENT_INTENT_FAILED.equals(eventType)
                || EVENT_TYPE_PAYMENT_INTENT_CANCELED.equals(eventType);
    }

    public boolean isPaymentAttemptEvent() {
        return EVENT_TYPE_PAYMENT_ATTEMPT_CREATED.equals(eventType)
                || EVENT_TYPE_PAYMENT_ATTEMPT_CAPTURE_REQUESTED.equals(eventType)
                || EVENT_TYPE_PAYMENT_ATTEMPT_SUCCEEDED.equals(eventType)
                || EVENT_TYPE_PAYMENT_ATTEMPT_FAILED.equals(eventType)
                || EVENT_TYPE_PAYMENT_ATTEMPT_CANCELED.equals(eventType);
    }

    public boolean isRefundEvent() {
        return EVENT_TYPE_REFUND_CREATED.equals(eventType)
                || EVENT_TYPE_REFUND_SUCCEEDED.equals(eventType)
                || EVENT_TYPE_REFUND_FAILED.equals(eventType);
    }

    public boolean isCardEvent() {
        return EVENT_TYPE_CARD_CREATE_SUCCEEDED.equals(eventType)
                || EVENT_TYPE_CARD_CREATE_FAILED.equals(eventType)
                || EVENT_TYPE_CARD_UPDATE_SUCCEEDED.equals(eventType)
                || EVENT_TYPE_CARD_UPDATE_FAILED.equals(eventType)
                || EVENT_TYPE_CARD_RECHARGE_SUCCEEDED.equals(eventType)
                || EVENT_TYPE_CARD_RECHARGE_FAILED.equals(eventType)
                || EVENT_TYPE_CARD_ACTIVATION_CODE.equals(eventType)
                || EVENT_TYPE_CARD_ACTIVATED.equals(eventType)
                || EVENT_TYPE_CARD_SUSPENDED.equals(eventType)
                || EVENT_TYPE_CARD_CLOSED.equals(eventType)
                || EVENT_TYPE_CARD_STATUS_UPDATE_SUCCEEDED.equals(eventType)
                || EVENT_TYPE_CARD_STATUS_UPDATE_FAILED.equals(eventType);
    }

    public boolean isCardCreateOrUpdateEvent() {
        return EVENT_TYPE_CARD_CREATE_SUCCEEDED.equals(eventType)
                || EVENT_TYPE_CARD_CREATE_FAILED.equals(eventType)
                || EVENT_TYPE_CARD_UPDATE_SUCCEEDED.equals(eventType)
                || EVENT_TYPE_CARD_UPDATE_FAILED.equals(eventType);
    }

    public boolean isCardStatusUpdateEvent() {
        return EVENT_TYPE_CARD_STATUS_UPDATE_SUCCEEDED.equals(eventType)
                || EVENT_TYPE_CARD_STATUS_UPDATE_FAILED.equals(eventType);
    }

    public boolean isCardRechargeEvent() {
        return EVENT_TYPE_CARD_RECHARGE_SUCCEEDED.equals(eventType)
                || EVENT_TYPE_CARD_RECHARGE_FAILED.equals(eventType);
    }

    public boolean isCardActivationCodeEvent() {
        return EVENT_TYPE_CARD_ACTIVATION_CODE.equals(eventType);
    }

    public boolean isCardTransactionEvent() {
        return EVENT_TYPE_ISSUING_FEE_CARD.equals(eventType)
                || EVENT_TYPE_ISSUING_TRANSACTION_AUTHORIZATION.equals(eventType)
                || EVENT_TYPE_ISSUING_TRANSACTION_REVERSAL.equals(eventType)
                || EVENT_TYPE_ISSUING_TRANSACTION_REFUND.equals(eventType)
                || EVENT_TYPE_ISSUING_TRANSACTION_SETTLEMENT_CREDIT.equals(eventType)
                || EVENT_TYPE_ISSUING_TRANSACTION_SETTLEMENT_DEBIT.equals(eventType)
                || EVENT_TYPE_ISSUING_TRANSACTION_FUND_COLLECTION.equals(eventType)
                || EVENT_TYPE_ISSUING_TRANSACTION_VALIDATION.equals(eventType)
                || EVENT_TYPE_ISSUING_TRANSACTION_ATM_DEPOSIT.equals(eventType)
                || EVENT_TYPE_ISSUING_TRANSACTION_CHARGEBACK_CREDIT.equals(eventType)
                || EVENT_TYPE_ISSUING_TRANSACTION_CHARGEBACK_DEBIT.equals(eventType);
    }

    public boolean isBeneficiarySuccessfulEvent() {
        return EVENT_TYPE_BENEFICIARY_SUCCESSFUL.equals(eventType);
    }

    public boolean isBeneficiaryFailedEvent() {
        return EVENT_TYPE_BENEFICIARY_FAILED.equals(eventType);
    }

    public boolean isBeneficiaryPendingEvent() {
        return EVENT_TYPE_BENEFICIARY_PENDING.equals(eventType);
    }

    public boolean isDepositEvent() {
        return EVENT_NAME_DEPOSIT.equals(eventName);
    }

    public boolean isPayoutBankingEvent() {
        return EVENT_NAME_PAYOUT.equals(eventName);
    }

    public boolean isVirtualAccountEvent() {
        return EVENT_NAME_VIRTUAL.equals(eventName);
    }

    public boolean isRfiEvent() {
        return EVENT_NAME_RFI.equals(eventName);
    }

    public boolean isTransferEvent() {
        return EVENT_NAME_TRANSFER.equals(eventName);
    }

    public boolean isCardholderKycEvent() {
        return EVENT_NAME_CARDHOLDER_KYC.equals(eventName);
    }

    public boolean isCardholderUpdatedEvent() {
        return EVENT_NAME_CARDHOLDER_UPDATED.equals(eventName);
    }

    // =========================================================================
    // Static factory
    // =========================================================================

    public static Event fromJson(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, Event.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse Event from JSON: " + e.getMessage(), e);
        }
    }

    // =========================================================================
    // Generic parse
    // =========================================================================

    public <T> T parseData(Class<T> type) {
        if (data == null) {
            throw new IllegalStateException("Event data is null");
        }
        try {
            return OBJECT_MAPPER.treeToValue(data, type);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse event data as " + type.getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    // =========================================================================
    // Typed parse methods (placeholders — wired up after model classes exist)
    // =========================================================================

    public AccountData parseAccountData() {
        if (!isAccountCreateEvent() && !isAccountUpdateEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not an account event");
        }
        return parseData(AccountData.class);
    }

    public PaymentIntentData parsePaymentIntentData() {
        if (!isPaymentIntentEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a payment intent event");
        }
        return parseData(PaymentIntentData.class);
    }

    public PaymentAttemptData parsePaymentAttemptData() {
        if (!isPaymentAttemptEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a payment attempt event");
        }
        return parseData(PaymentAttemptData.class);
    }

    public RefundData parseRefundData() {
        if (!isRefundEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a refund event");
        }
        return parseData(RefundData.class);
    }

    public ConversionData parseConversionData() {
        if (!isConversionEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a conversion event");
        }
        return parseData(ConversionData.class);
    }

    public CardData parseCardData() {
        if (!isCardCreateOrUpdateEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a card create/update event");
        }
        return parseData(CardData.class);
    }

    public CardActivationCodeData parseCardActivationCodeData() {
        if (!isCardActivationCodeEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a card activation code event");
        }
        return parseData(CardActivationCodeData.class);
    }

    public CardRechargeData parseCardRechargeData() {
        if (!isCardRechargeEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a card recharge event");
        }
        return parseData(CardRechargeData.class);
    }

    public CardStatusUpdateData parseCardStatusUpdateData() {
        if (!isCardStatusUpdateEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a card status update event");
        }
        return parseData(CardStatusUpdateData.class);
    }

    public CardTransactionData parseCardTransactionData() {
        if (!isCardTransactionEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a card transaction event");
        }
        return parseData(CardTransactionData.class);
    }

    public BeneficiaryData parseBeneficiaryData() {
        if (!isBeneficiaryEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a beneficiary event");
        }
        return parseData(BeneficiaryData.class);
    }

    public DepositEventData parseDepositData() {
        if (!isDepositEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a deposit event");
        }
        return parseData(DepositEventData.class);
    }

    public PayoutEventData parsePayoutBankingData() {
        if (!isPayoutBankingEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a payout event");
        }
        return parseData(PayoutEventData.class);
    }

    public VirtualAccountEventData parseVirtualAccountData() {
        if (!isVirtualAccountEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a virtual account event");
        }
        return parseData(VirtualAccountEventData.class);
    }

    public TransferEventData parseTransferData() {
        if (!isTransferEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a transfer event");
        }
        return parseData(TransferEventData.class);
    }

    public CardholderData parseCardholderData() {
        if (!isCardholderKycEvent() && !isCardholderUpdatedEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not a cardholder event");
        }
        return parseData(CardholderData.class);
    }

    public CardWithdrawData parseCardWithdrawData() {
        if (!EVENT_TYPE_CARD_WITHDRAW_SUCCEEDED.equals(eventType)) {
            throw new IllegalStateException("Event type " + eventType + " is not a card withdraw event");
        }
        return parseData(CardWithdrawData.class);
    }

    public CardOtpData parseCardOtpData() {
        if (!EVENT_TYPE_CARD_3DS_OTP.equals(eventType) && !EVENT_TYPE_CARD_VERIFICATION_OTP.equals(eventType)) {
            throw new IllegalStateException("Event type " + eventType + " is not a card OTP event");
        }
        return parseData(CardOtpData.class);
    }

    public RfiEventData parseRfiData() {
        if (!isRfiEvent()) {
            throw new IllegalStateException("Event type " + eventType + " is not an RFI event");
        }
        return parseData(RfiEventData.class);
    }

    public SettlementEventData parseSettlementData() {
        if (!EVENT_TYPE_ACQUIRING_PAYMENT_SETTLEMENT.equals(eventType)) {
            throw new IllegalStateException("Event type " + eventType + " is not a settlement event");
        }
        return parseData(SettlementEventData.class);
    }
}
