package com.uqpay.sdk.issuing.model;

import java.util.List;
import java.util.Map;

public final class CardCapabilityModels {
    private CardCapabilityModels() {}

    public static final class ElevateLimitRequest {
        public double limitAmount;
        public Integer durationInDays;
    }

    public static final class ElevateLimitResponse {
        public String cardId;
        public String cardOrderId;
        public String orderStatus;
    }

    public static final class EnrollNetworkProtectionRequest {
        public String riskControl = "network_protection";
        public String actionCode;
    }

    public static final class RemoveNetworkProtectionRequest {
        public String riskControl = "network_protection";
    }

    public static final class NetworkProtectionResponse {
        public String cardId;
        public String cardNumber;
        public String cardholderId;
        public String cardScheme;
        public boolean enabled;
        public String status;
        public String actionCode;
        public String definition;
        public String updateTime;
    }

    public static final class ManagePinRequest {
        public String cardId;
        public String type;
        public String pin;
        public String oldPin;
    }

    public static final class ManagePinResponse {
        public String cardId;
        public String cardOrderId;
        public String createTime;
    }

    public static final class CardArtsResponse {
        public String defaultCardArtId;
        public List<Map<String, Object>> cardArts;
    }

    public static final class SetDefaultArtRequest {
        public String cardArtId;
    }

    public static final class SetDefaultArtResponse {
        public String defaultCardArtId;
        public String updatedAt;
    }

    public static final class ClaimUnsolicitedRefundRequest {
        public String relatedTransactionId;
        public String remark;
    }

    public static final class ClaimUnsolicitedRefundResponse {
        public String requestStatus;
    }
}
