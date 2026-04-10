package com.uqpay.sdk.issuing.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;

public class AuthDecisionConfig {
    private final String privateKey;
    private final String passphrase;
    private final String uqpayPublicKey;

    public AuthDecisionConfig(@NotNull String privateKey, @Nullable String passphrase, @NotNull String uqpayPublicKey) {
        this.privateKey = Objects.requireNonNull(privateKey, "privateKey must not be null");
        this.passphrase = passphrase;
        this.uqpayPublicKey = Objects.requireNonNull(uqpayPublicKey, "uqpayPublicKey must not be null");
    }

    @NotNull
    public String getPrivateKey() { return privateKey; }

    @Nullable
    public String getPassphrase() { return passphrase; }

    @NotNull
    public String getUqpayPublicKey() { return uqpayPublicKey; }
}
