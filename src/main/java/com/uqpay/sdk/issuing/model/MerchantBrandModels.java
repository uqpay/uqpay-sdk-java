package com.uqpay.sdk.issuing.model;

import java.util.List;

public final class MerchantBrandModels {
    private MerchantBrandModels() {}

    public static final class MerchantBrand {
        public String merchantCode;
        public String displayName;
    }

    public static final class ListResponse {
        public int totalItems;
        public int totalPages;
        public List<MerchantBrand> data;
    }
}
