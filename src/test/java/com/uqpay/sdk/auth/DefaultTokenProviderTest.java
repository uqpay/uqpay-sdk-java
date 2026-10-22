package com.uqpay.sdk.auth;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.common.UqpayAuthException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.config.Environment;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link DefaultTokenProvider}.
 */
@DisplayName("DefaultTokenProvider")
class DefaultTokenProviderTest {

    @Nested
    @DisplayName("Unit Tests")
    class UnitTests {

        @Test
        @DisplayName("should throw when configuration is null")
        void shouldThrowWhenConfigurationIsNull() {
            assertThatThrownBy(() -> new DefaultTokenProvider((Configuration) null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("should create provider with valid configuration")
        void shouldCreateProviderWithValidConfiguration() {
            Configuration config = Configuration.builder()
                    .clientId("test-client")
                    .apiKey("test-key")
                    .environment(Environment.SANDBOX)
                    .build();

            DefaultTokenProvider provider = new DefaultTokenProvider(config);
            assertThat(provider).isNotNull();
        }

        private MockWebServer server;

        @BeforeEach
        void startServer() throws IOException {
            server = new MockWebServer();
            // Unexpected extra fetches fail immediately instead of waiting for a queued response.
            server.setDispatcher(new Dispatcher() {
                @Override
                public MockResponse dispatch(RecordedRequest request) {
                    return new MockResponse().setResponseCode(500);
                }
            });
            server.start();
        }

        @AfterEach
        void stopServer() throws IOException {
            server.shutdown();
        }

        private DefaultTokenProvider provider(String clientId, String apiKey) {
            return new DefaultTokenProvider(server.url("/api").toString(), clientId, apiKey,
                    new OkHttpClient.Builder().callTimeout(5, TimeUnit.SECONDS).build());
        }

        private void respondWithTokens(long firstExpiry) {
            AtomicInteger calls = new AtomicInteger();
            server.setDispatcher(new Dispatcher() {
                @Override
                public MockResponse dispatch(RecordedRequest request) {
                    boolean first = calls.incrementAndGet() == 1;
                    return new MockResponse().setHeader("Content-Type", "application/json")
                            .setBody("{\"auth_token\":\"" + (first ? "first-token" : "refreshed-token")
                                    + "\",\"expired_at\":"
                                    + (first ? firstExpiry : Instant.now().plusSeconds(3600).getEpochSecond()) + "}");
                }
            });
        }

        @Test
        @DisplayName("should throw auth exception for invalid credentials using local response")
        void shouldThrowAuthExceptionForInvalidCredentials() throws Exception {
            assertRejectedCredentials("invalid-client-id", "invalid-api-key");
        }

        @Test
        @DisplayName("should throw auth exception for empty credentials using local response")
        void shouldThrowAuthExceptionForEmptyCredentials() throws Exception {
            assertRejectedCredentials("", "");
        }

        private void assertRejectedCredentials(String clientId, String apiKey) throws Exception {
            server.setDispatcher(new Dispatcher() {
                @Override
                public MockResponse dispatch(RecordedRequest request) {
                    return new MockResponse().setResponseCode(401).setBody("unauthorized");
                }
            });
            assertThatThrownBy(provider(clientId, apiKey)::getToken)
                    .isInstanceOf(UqpayAuthException.class)
                    .hasMessageContaining("status 401");
            RecordedRequest request = server.takeRequest(1, TimeUnit.SECONDS);
            assertThat(request).isNotNull();
            assertThat(request.getHeader("x-client-id")).isEqualTo(clientId);
            assertThat(request.getHeader("x-api-key")).isEqualTo(apiKey);
            assertThat(server.getRequestCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("should wrap local transport failure without DNS or network access")
        void shouldWrapTransportFailure() {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> { throw new IOException("local transport failure"); })
                    .build();
            DefaultTokenProvider provider = new DefaultTokenProvider(
                    server.url("/api").toString(), "client", "key", client);
            assertThatThrownBy(provider::getToken)
                    .isInstanceOf(UqpayAuthException.class)
                    .hasCauseInstanceOf(IOException.class)
                    .hasMessageContaining("local transport failure");
            assertThat(server.getRequestCount()).isZero();
        }

        @Test
        void shouldCacheTokenAndSendAuthenticationHeaders() throws Exception {
            respondWithTokens(Instant.now().plusSeconds(3600).getEpochSecond());
            DefaultTokenProvider provider = provider("test-client", "test-key");
            assertThat(provider.getToken()).isEqualTo("first-token");
            assertThat(provider.getToken()).isEqualTo("first-token");
            assertThat(server.getRequestCount()).isEqualTo(1);
            RecordedRequest request = server.takeRequest(1, TimeUnit.SECONDS);
            assertThat(request).isNotNull();
            assertThat(request.getMethod()).isEqualTo("POST");
            assertThat(request.getPath()).isEqualTo("/api/v1/connect/token");
            assertThat(request.getHeader("Accept")).isEqualTo("application/json");
            assertThat(request.getHeader("x-client-id")).isEqualTo("test-client");
            assertThat(request.getHeader("x-api-key")).isEqualTo("test-key");
        }

        @Test
        void shouldRefreshExpiredToken() throws Exception {
            assertTokenRefresh(-3600);
        }

        @Test
        void shouldRefreshTokenInsideRefreshBuffer() throws Exception {
            assertTokenRefresh(60);
        }

        private void assertTokenRefresh(long secondsUntilExpiry) throws Exception {
            respondWithTokens(Instant.now().plusSeconds(secondsUntilExpiry).getEpochSecond());
            DefaultTokenProvider provider = provider("test-client", "test-key");
            assertThat(provider.getToken()).isEqualTo("first-token");
            assertThat(provider.getToken()).isEqualTo("refreshed-token");
            assertThat(provider.getToken()).isEqualTo("refreshed-token");
            assertThat(server.getRequestCount()).isEqualTo(2);
        }

        @Test
        void shouldRefetchAfterInvalidation() throws Exception {
            respondWithTokens(Instant.now().plusSeconds(3600).getEpochSecond());
            DefaultTokenProvider provider = provider("test-client", "test-key");
            assertThat(provider.getToken()).isEqualTo("first-token");
            provider.invalidateToken();
            assertThat(provider.getToken()).isEqualTo("refreshed-token");
            assertThat(provider.getToken()).isEqualTo("refreshed-token");
            assertThat(server.getRequestCount()).isEqualTo(2);
        }

        @Test
        void shouldFetchOnlyOnceForConcurrentCalls() throws Exception {
            respondWithTokens(Instant.now().plusSeconds(3600).getEpochSecond());
            DefaultTokenProvider provider = provider("test-client", "test-key");
            int workers = 10;
            ExecutorService executor = Executors.newFixedThreadPool(workers);
            CountDownLatch ready = new CountDownLatch(workers);
            CountDownLatch start = new CountDownLatch(1);
            List<Future<String>> results = new ArrayList<>();
            try {
                for (int i = 0; i < workers; i++) {
                    results.add(executor.submit(() -> {
                        ready.countDown();
                        if (!start.await(5, TimeUnit.SECONDS)) {
                            throw new IllegalStateException("start barrier timed out");
                        }
                        return provider.getToken();
                    }));
                }
                assertThat(ready.await(5, TimeUnit.SECONDS)).isTrue();
                start.countDown();
                for (Future<String> result : results) {
                    assertThat(result.get(10, TimeUnit.SECONDS)).isEqualTo("first-token");
                }
                assertThat(server.getRequestCount()).isEqualTo(1);
            } finally {
                start.countDown();
                executor.shutdownNow();
                assertThat(executor.awaitTermination(5, TimeUnit.SECONDS)).isTrue();
            }
        }

    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("should fetch token with valid credentials")
        void shouldFetchTokenWithValidCredentials() throws UqpayAuthException {
            TestHelper.assumeIntegrationTestsEnabled();

            Configuration config = TestHelper.getTestConfiguration();
            DefaultTokenProvider provider = new DefaultTokenProvider(config);

            String token = provider.getToken();

            assertThat(token)
                    .isNotNull()
                    .isNotEmpty();

            // Token should be a reasonable length (typical JWT/token length)
            assertThat(token.length()).isGreaterThanOrEqualTo(20);

            System.out.println("Token fetched successfully");
            System.out.println("  Token length: " + token.length() + " characters");
            System.out.println("  Token preview: " + TestHelper.safePreview(token, 20));
        }

        @Test
        @DisplayName("should cache token on subsequent calls")
        void shouldCacheTokenOnSubsequentCalls() throws UqpayAuthException {
            TestHelper.assumeIntegrationTestsEnabled();

            Configuration config = TestHelper.getTestConfiguration();
            DefaultTokenProvider provider = new DefaultTokenProvider(config);

            // First call - fetches from API
            String token1 = provider.getToken();

            // Second call - should be cached
            String token2 = provider.getToken();

            assertThat(token1).isEqualTo(token2);

            System.out.println("Token caching works correctly");
            System.out.println("  First token:  " + TestHelper.safePreview(token1, 20));
            System.out.println("  Second token: " + TestHelper.safePreview(token2, 20));
        }

        @Test
        @DisplayName("should handle concurrent access")
        void shouldHandleConcurrentAccess() throws Exception {
            TestHelper.assumeIntegrationTestsEnabled();

            Configuration config = TestHelper.getTestConfiguration();
            DefaultTokenProvider provider = new DefaultTokenProvider(config);

            int threadCount = 10;
            int iterationsPerThread = 10;
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            CountDownLatch startLatch = new CountDownLatch(1);
            CountDownLatch doneLatch = new CountDownLatch(threadCount);
            AtomicInteger successCount = new AtomicInteger(0);
            AtomicInteger errorCount = new AtomicInteger(0);
            AtomicReference<String> firstToken = new AtomicReference<>();

            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try {
                        startLatch.await(); // Wait for all threads to be ready
                        for (int j = 0; j < iterationsPerThread; j++) {
                            try {
                                String token = provider.getToken();
                                assertThat(token).isNotNull().isNotEmpty();

                                // Store first token for comparison
                                firstToken.compareAndSet(null, token);

                                // All tokens should be the same (cached)
                                assertThat(token).isEqualTo(firstToken.get());

                                successCount.incrementAndGet();
                            } catch (Exception e) {
                                errorCount.incrementAndGet();
                                e.printStackTrace();
                            }
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        doneLatch.countDown();
                    }
                });
            }

            // Start all threads simultaneously
            startLatch.countDown();

            // Wait for all threads to complete
            boolean completed = doneLatch.await(60, TimeUnit.SECONDS);
            executor.shutdown();

            assertThat(completed).isTrue();
            assertThat(errorCount.get()).isZero();
            assertThat(successCount.get()).isEqualTo(threadCount * iterationsPerThread);

            System.out.println("Concurrent access test completed");
            System.out.println("  Threads: " + threadCount);
            System.out.println("  Iterations per thread: " + iterationsPerThread);
            System.out.println("  Total successful calls: " + successCount.get());
            System.out.println("  Errors: " + errorCount.get());
        }

        @Test
        @DisplayName("should have good performance with cached token")
        void shouldHaveGoodPerformanceWithCachedToken() throws UqpayAuthException {
            TestHelper.assumeIntegrationTestsEnabled();

            Configuration config = TestHelper.getTestConfiguration();
            DefaultTokenProvider provider = new DefaultTokenProvider(config);

            // First call (will make HTTP request)
            long startFirst = System.nanoTime();
            provider.getToken();
            long firstCallDuration = System.nanoTime() - startFirst;

            // Subsequent calls (should be cached and fast)
            int iterations = 100;
            long startCached = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                provider.getToken();
            }
            long cachedCallsDuration = System.nanoTime() - startCached;

            double firstCallMs = firstCallDuration / 1_000_000.0;
            double cachedCallsTotalMs = cachedCallsDuration / 1_000_000.0;
            double avgCachedCallMs = cachedCallsTotalMs / iterations;

            System.out.println("Performance test completed");
            System.out.println("  First call (HTTP): " + String.format("%.2f ms", firstCallMs));
            System.out.println("  " + iterations + " cached calls: " + String.format("%.2f ms", cachedCallsTotalMs));
            System.out.println("  Avg cached call: " + String.format("%.4f ms", avgCachedCallMs));

            // Cached calls should be significantly faster (at least 10x)
            assertThat(cachedCallsTotalMs).isLessThan(firstCallMs);
        }

        @Test
        @DisplayName("should refresh token after invalidation")
        void shouldRefreshTokenAfterInvalidation() throws UqpayAuthException {
            TestHelper.assumeIntegrationTestsEnabled();

            Configuration config = TestHelper.getTestConfiguration();
            DefaultTokenProvider provider = new DefaultTokenProvider(config);

            // Get initial token
            String token1 = provider.getToken();
            assertThat(token1).isNotNull();

            // Invalidate token
            provider.invalidateToken();

            // Get new token (should make HTTP request)
            String token2 = provider.getToken();
            assertThat(token2).isNotNull();

            // Note: tokens may or may not be the same depending on server behavior
            // The important thing is that both calls succeed
            System.out.println("Token invalidation test completed");
            System.out.println("  Token 1: " + TestHelper.safePreview(token1, 20));
            System.out.println("  Token 2: " + TestHelper.safePreview(token2, 20));
        }
    }
}
