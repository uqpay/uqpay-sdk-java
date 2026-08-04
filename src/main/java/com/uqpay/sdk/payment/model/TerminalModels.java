package com.uqpay.sdk.payment.model;

public final class TerminalModels {
    private TerminalModels() {}

    public static final class RegisterRequest {
        public String firmCode;
        public String firmSn;
        public String terminalModel;
    }

    public static final class RegisterResponse {
        public String createTime;
        public String firmSn;
        public String terminalId;
    }

    public static final class GetPinKeyRequest {
        public String terminalId;
        public String prvKey;
    }

    public static final class GetPinKeyResponse {
        public String encryptPinKey;
        public String pinKeyExpire;
        public String terminalId;
    }
}
