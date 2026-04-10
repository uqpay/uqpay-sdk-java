package com.uqpay.sdk.issuing;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.model.CardProduct;
import com.uqpay.sdk.issuing.model.ListProductsRequest;
import com.uqpay.sdk.issuing.model.ListProductsResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductsService Integration Tests")
class ProductsServiceIntegrationTest {

    private static ProductsService productsService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration config = TestHelper.getTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(config);
        ApiClient apiClient = new ApiClient(config, tokenProvider);
        IssuingClient issuingClient = new IssuingClient(apiClient);
        productsService = issuingClient.getProducts();
    }

    @Nested
    @DisplayName("List Products")
    class ListProducts {

        @Test
        @DisplayName("should list products with pagination")
        void shouldListProductsWithPagination() throws UqpayException {
            ListProductsRequest request = new ListProductsRequest();
            request.setPageSize(10);
            request.setPageNumber(1);

            ListProductsResponse response = productsService.list(request);

            assertThat(response).isNotNull();
            assertThat(response.getTotalPages()).isGreaterThanOrEqualTo(0);
            assertThat(response.getTotalItems()).isGreaterThanOrEqualTo(0);
            assertThat(response.getData()).isNotNull();

            System.out.printf("Found %d products (total: %d, pages: %d)%n",
                    response.getData().size(), response.getTotalItems(), response.getTotalPages());

            if (!response.getData().isEmpty()) {
                CardProduct first = response.getData().get(0);
                System.out.printf("First product: ID=%s, Bin=%s, Scheme=%s, Status=%s, ModeType=%s%n",
                        first.getProductId(), first.getCardBin(),
                        first.getCardScheme(), first.getProductStatus(), first.getModeType());
                if (first.getCardCurrency() != null) {
                    System.out.printf("  Currencies: %s%n", first.getCardCurrency());
                }
                if (first.getCardForm() != null) {
                    System.out.printf("  Forms: %s%n", first.getCardForm());
                }
            }
        }
    }
}
