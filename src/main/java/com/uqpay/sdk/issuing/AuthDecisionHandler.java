package com.uqpay.sdk.issuing;

import com.uqpay.sdk.issuing.model.AuthDecisionRequest;
import com.uqpay.sdk.issuing.model.AuthDecisionResponse;

@FunctionalInterface
public interface AuthDecisionHandler {
    AuthDecisionResponse decide(AuthDecisionRequest request);
}
