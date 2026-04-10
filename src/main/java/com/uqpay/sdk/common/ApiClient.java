package com.uqpay.sdk.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.uqpay.sdk.config.Configuration;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * HTTP client wrapper for UQPAY API requests.
 * <p>
 * Handles request serialization, response deserialization, authentication,
 * and error handling.
 * </p>
 */
public final class ApiClient {

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_AUTH_TOKEN = "x-auth-token"; // Required; authentication token from /v1/connect/token
    private static final String HEADER_IDEMPOTENCY_KEY = "x-idempotency-key"; // UUID; auto-generated if not provided; cached for 24 hours
    private static final String HEADER_ON_BEHALF_OF = "x-on-behalf-of"; // Optional; UQPAY sub-account ID for connected account requests
    private static final String HEADER_ACCEPT = "Accept";
    private static final String BEARER_PREFIX = "Bearer ";

    private final Configuration config;
    private final TokenProvider tokenProvider;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    /**
     * Creates a new API client.
     *
     * @param config        the SDK configuration
     * @param tokenProvider the token provider for authentication
     */
    public ApiClient(@NotNull Configuration config, @NotNull TokenProvider tokenProvider) {
        this.config = Objects.requireNonNull(config, "config must not be null");
        this.tokenProvider = Objects.requireNonNull(tokenProvider, "tokenProvider must not be null");
        this.httpClient = config.getHttpClient();
        this.objectMapper = createObjectMapper();
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    /**
     * Returns the ObjectMapper used for JSON serialization/deserialization.
     *
     * @return the ObjectMapper instance
     */
    @NotNull
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Returns the base URL for API requests.
     *
     * @return the base URL
     */
    @NotNull
    public String getBaseUrl() {
        String override = config.getBaseUrlOverride();
        return (override != null && !override.isEmpty()) ? override : config.getEnvironment().getBaseUrl();
    }

    // =========================================================================
    // GET methods
    // =========================================================================

    /**
     * Sends a GET request and returns the deserialized response.
     *
     * @param path         the API path (e.g., "/v1/issuing/cards")
     * @param responseType the response type class
     * @param <T>          the response type
     * @return the deserialized response
     * @throws UqpayException if the request fails
     */
    @NotNull
    public <T> T get(@NotNull String path, @NotNull Class<T> responseType) throws UqpayException {
        return get(path, responseType, null);
    }

    /**
     * Sends a GET request with custom options and returns the deserialized response.
     *
     * @param path         the API path
     * @param responseType the response type class
     * @param options      the request options (optional)
     * @param <T>          the response type
     * @return the deserialized response
     * @throws UqpayException if the request fails
     */
    @NotNull
    public <T> T get(@NotNull String path, @NotNull Class<T> responseType, @Nullable RequestOptions options)
            throws UqpayException {
        return execute("GET", path, null, responseType, options);
    }

    // =========================================================================
    // POST methods
    // =========================================================================

    /**
     * Sends a POST request with a body and returns the deserialized response.
     *
     * @param path         the API path
     * @param body         the request body
     * @param responseType the response type class
     * @param <T>          the response type
     * @return the deserialized response
     * @throws UqpayException if the request fails
     */
    @NotNull
    public <T> T post(@NotNull String path, @Nullable Object body, @NotNull Class<T> responseType)
            throws UqpayException {
        return post(path, body, responseType, null);
    }

    /**
     * Sends a POST request with a body and custom options, returns the deserialized response.
     *
     * @param path         the API path
     * @param body         the request body
     * @param responseType the response type class
     * @param options      the request options (optional)
     * @param <T>          the response type
     * @return the deserialized response
     * @throws UqpayException if the request fails
     */
    @NotNull
    public <T> T post(@NotNull String path, @Nullable Object body, @NotNull Class<T> responseType,
                      @Nullable RequestOptions options) throws UqpayException {
        return execute("POST", path, body, responseType, options);
    }

    // =========================================================================
    // PUT methods
    // =========================================================================

    /**
     * Sends a PUT request with a body and returns the deserialized response.
     *
     * @param path         the API path
     * @param body         the request body
     * @param responseType the response type class
     * @param <T>          the response type
     * @return the deserialized response
     * @throws UqpayException if the request fails
     */
    @NotNull
    public <T> T put(@NotNull String path, @Nullable Object body, @NotNull Class<T> responseType)
            throws UqpayException {
        return put(path, body, responseType, null);
    }

    /**
     * Sends a PUT request with a body and custom options, returns the deserialized response.
     *
     * @param path         the API path
     * @param body         the request body
     * @param responseType the response type class
     * @param options      the request options (optional)
     * @param <T>          the response type
     * @return the deserialized response
     * @throws UqpayException if the request fails
     */
    @NotNull
    public <T> T put(@NotNull String path, @Nullable Object body, @NotNull Class<T> responseType,
                     @Nullable RequestOptions options) throws UqpayException {
        return execute("PUT", path, body, responseType, options);
    }

    // =========================================================================
    // DELETE methods
    // =========================================================================

    /**
     * Sends a DELETE request and returns the deserialized response.
     *
     * @param path         the API path
     * @param responseType the response type class
     * @param <T>          the response type
     * @return the deserialized response
     * @throws UqpayException if the request fails
     */
    @NotNull
    public <T> T delete(@NotNull String path, @NotNull Class<T> responseType) throws UqpayException {
        return delete(path, responseType, null);
    }

    /**
     * Sends a DELETE request with custom options and returns the deserialized response.
     *
     * @param path         the API path
     * @param responseType the response type class
     * @param options      the request options (optional)
     * @param <T>          the response type
     * @return the deserialized response
     * @throws UqpayException if the request fails
     */
    @NotNull
    public <T> T delete(@NotNull String path, @NotNull Class<T> responseType, @Nullable RequestOptions options)
            throws UqpayException {
        return execute("DELETE", path, null, responseType, options);
    }

    // =========================================================================
    // Raw methods (for binary data)
    // =========================================================================

    /**
     * Sends a GET request and returns the raw response bytes.
     *
     * @param path the API path
     * @return the raw response bytes
     * @throws UqpayException if the request fails
     */
    @NotNull
    public byte[] getRaw(@NotNull String path) throws UqpayException {
        return getRaw(path, null);
    }

    /**
     * Sends a GET request with custom options and returns the raw response bytes.
     *
     * @param path    the API path
     * @param options the request options (optional)
     * @return the raw response bytes
     * @throws UqpayException if the request fails
     */
    @NotNull
    public byte[] getRaw(@NotNull String path, @Nullable RequestOptions options) throws UqpayException {
        String url = getBaseUrl() + path;

        String token = resolveToken(options);
        String idempotencyKey = resolveIdempotencyKey(options);

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .get()
                .header(HEADER_ACCEPT, "application/octet-stream")
                .header(HEADER_AUTH_TOKEN, BEARER_PREFIX + token)
                .header(HEADER_IDEMPOTENCY_KEY, idempotencyKey);

        if (options != null && options.getOnBehalfOf() != null) {
            requestBuilder.header(HEADER_ON_BEHALF_OF, options.getOnBehalfOf());
        }

        Request request = requestBuilder.build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                handleErrorResponse(response);
            }

            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                return new byte[0];
            }
            return responseBody.bytes();
        } catch (IOException e) {
            throw new UqpayNetworkException("Request failed: " + e.getMessage(), e);
        }
    }

    // =========================================================================
    // Multipart methods (for file uploads)
    // =========================================================================

    /**
     * Sends a POST request with a multipart/form-data body for file uploads.
     *
     * @param path         the API path (e.g., "/v1/files/upload")
     * @param file         the file to upload
     * @param notes        optional notes (max 50 chars)
     * @param responseType the response type class
     * @param options      the request options (optional)
     * @param <T>          the response type
     * @return the deserialized response
     * @throws UqpayException if the request fails
     */
    @NotNull
    public <T> T postMultipart(@NotNull String path, @NotNull File file, @Nullable String notes,
                                @NotNull Class<T> responseType, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(file, "file must not be null");
        if (!file.exists()) {
            throw new IllegalArgumentException("file does not exist: " + file.getAbsolutePath());
        }

        String url = getBaseUrl() + path;
        if (notes != null && !notes.isEmpty()) {
            try {
                url += "?notes=" + URLEncoder.encode(notes, StandardCharsets.UTF_8.name());
            } catch (IOException e) {
                throw new UqpayException("Failed to encode notes parameter: " + e.getMessage(), e);
            }
        }

        String token = resolveToken(options);
        String idempotencyKey = resolveIdempotencyKey(options);

        String fileName = file.getName();
        MediaType fileMediaType = guessMediaType(fileName);

        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, RequestBody.create(file, fileMediaType))
                .build();

        Request.Builder multipartRequestBuilder = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header(HEADER_AUTH_TOKEN, BEARER_PREFIX + token)
                .header(HEADER_IDEMPOTENCY_KEY, idempotencyKey);

        if (options != null && options.getOnBehalfOf() != null) {
            multipartRequestBuilder.header(HEADER_ON_BEHALF_OF, options.getOnBehalfOf());
        }

        Request request = multipartRequestBuilder.build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                handleErrorResponse(response);
            }

            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new UqpayException("Empty response body");
            }

            String responseJson = responseBody.string();
            return objectMapper.readValue(responseJson, responseType);
        } catch (IOException e) {
            throw new UqpayNetworkException("Request failed: " + e.getMessage(), e);
        }
    }

    @NotNull
    private static MediaType guessMediaType(@NotNull String fileName) {
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".pdf")) {
            return MediaType.parse("application/pdf");
        } else if (lower.endsWith(".png")) {
            return MediaType.parse("image/png");
        } else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
            return MediaType.parse("image/jpeg");
        } else if (lower.endsWith(".doc")) {
            return MediaType.parse("application/msword");
        } else if (lower.endsWith(".docx")) {
            return MediaType.parse("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        }
        return MediaType.parse("application/octet-stream");
    }

    // =========================================================================
    // Core execution method
    // =========================================================================

    @NotNull
    private <T> T execute(@NotNull String method, @NotNull String path, @Nullable Object body,
                          @NotNull Class<T> responseType, @Nullable RequestOptions options) throws UqpayException {
        String url = getBaseUrl() + path;

        String token = resolveToken(options);
        String idempotencyKey = resolveIdempotencyKey(options);

        RequestBody requestBody = null;
        if (body != null) {
            try {
                String json = objectMapper.writeValueAsString(body);
                requestBody = RequestBody.create(json, JSON_MEDIA_TYPE);
            } catch (JsonProcessingException e) {
                throw new UqpayException("Failed to serialize request body: " + e.getMessage(), e);
            }
        }

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTH_TOKEN, BEARER_PREFIX + token)
                .header(HEADER_IDEMPOTENCY_KEY, idempotencyKey);

        if (options != null && options.getOnBehalfOf() != null) {
            requestBuilder.header(HEADER_ON_BEHALF_OF, options.getOnBehalfOf());
        }

        switch (method) {
            case "GET":
                requestBuilder.get();
                break;
            case "POST":
                requestBuilder.post(requestBody != null ? requestBody : RequestBody.create("", JSON_MEDIA_TYPE));
                break;
            case "PUT":
                requestBuilder.put(requestBody != null ? requestBody : RequestBody.create("", JSON_MEDIA_TYPE));
                break;
            case "DELETE":
                requestBuilder.delete(requestBody);
                break;
            default:
                throw new UqpayException("Unsupported HTTP method: " + method);
        }

        Request request = requestBuilder.build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                handleErrorResponse(response);
            }

            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new UqpayException("Empty response body");
            }

            String responseJson = responseBody.string();
            return objectMapper.readValue(responseJson, responseType);
        } catch (IOException e) {
            throw new UqpayNetworkException("Request failed: " + e.getMessage(), e);
        }
    }

    @NotNull
    private String resolveToken(@Nullable RequestOptions options) throws UqpayAuthException {
        if (options != null && options.getAuthToken() != null) {
            return options.getAuthToken();
        }
        return tokenProvider.getToken();
    }

    @NotNull
    private String resolveIdempotencyKey(@Nullable RequestOptions options) {
        if (options != null && options.getIdempotencyKey() != null) {
            return options.getIdempotencyKey();
        }
        return UUID.randomUUID().toString();
    }

    // Handles 4xx/5xx errors; HTTP 429 indicates rate limiting (use exponential backoff with jitter)
    private void handleErrorResponse(@NotNull Response response) throws UqpayException {
        int statusCode = response.code();
        ResponseBody responseBody = response.body();

        if (responseBody == null) {
            throw new UqpayApiException(null, null, statusCode);
        }

        String rawBody = null;
        try {
            rawBody = responseBody.string();
            UqpayApiException apiException = objectMapper.readValue(rawBody, UqpayApiException.class);
            apiException.setStatusCode(statusCode);
            throw apiException;
        } catch (IOException e) {
            throw new UqpayApiException(null,
                    "Request failed with status " + statusCode + (rawBody != null ? ": " + rawBody : ""),
                    statusCode);
        }
    }
}
