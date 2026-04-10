package com.uqpay.sdk.connect.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents the type of account entity.
 */
public enum EntityType {

    INDIVIDUAL("INDIVIDUAL"),
    COMPANY("COMPANY");

    private final String value;

    EntityType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
