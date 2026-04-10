package com.uqpay.sdk.auth;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.common.UqpayAuthException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.config.Environment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

        @Test
        @DisplayName("should throw auth exception for invalid credentials")
        void shouldThrowAuthExceptionForInvalidCredentials() {
            DefaultTokenProvider provider = new DefaultTokenProvider(
                    Environment.SANDBOX.getBaseUrl(),
                    "invalid-client-id",
                    "invalid-api-key",
                    null
            );

            assertThatThrownBy(provider::getToken)
                    .isInstanceOf(UqpayAuthException.class);
        }

        @Test
        @DisplayName("should throw auth exception for empty credentials")
        void shouldThrowAuthExceptionForEmptyCredentials() {
            DefaultTokenProvider provider = new DefaultTokenProvider(
                    Environment.SANDBOX.getBaseUrl(),
                    "",
                    "",
                    null
            );

            assertThatThrownBy(provider::getToken)
                    .isInstanceOf(UqpayAuthException.class);
        }

        @Test
        @DisplayName("should throw auth exception for invalid base URL")
        void shouldThrowAuthExceptionForInvalidBaseUrl() {
            DefaultTokenProvider provider = new DefaultTokenProvider(
                    "https://invalid-url.example.com/api",
                    "client",
                    "key",
                    null
            );

            assertThatThrownBy(provider::getToken)
                    .isInstanceOf(UqpayAuthException.class);
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
