package com.uqpay.sdk.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the response from the token endpoint.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class TokenResponse {

    @JsonProperty("auth_token")
    private String authToken; // Required; returned auth token; store securely and destroy when no longer needed

    @JsonProperty("expired_at")
    private long expiredAt; // Required; Unix timestamp in seconds; token valid for 30 minutes in production

    /**
     * Default constructor for JSON deserialization.
     */
    public TokenResponse() {
    }

    /**
     * Returns the authentication token.
     *
     * @return the authentication token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the authentication token.
     *
     * @param authToken the authentication token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Returns the token expiration time as a Unix timestamp in seconds.
     *
     * @return the expiration timestamp in seconds
     */
    public long getExpiredAt() {
        return expiredAt;
    }

    /**
     * Sets the token expiration time.
     *
     * @param expiredAt the expiration timestamp in seconds
     */
    public void setExpiredAt(long expiredAt) {
        this.expiredAt = expiredAt;
    }
}
