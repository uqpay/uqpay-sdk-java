package com.uqpay.sdk.banking.model;

public class ListBeneficiariesRequest {

    private int pageSize; // Required. Items per page, range: 10-100
    private int pageNumber; // Required. Page number to retrieve, must be >= 1
    private String entityType; // Optional. Filter by entity type: COMPANY or INDIVIDUAL
    private String nickname; // Optional. Beneficiary nickname filter, max 120 chars
    private String currency; // Optional. Filter by ISO 4217 currency code, e.g. "USD"
    private String companyName; // Optional. Company name filter, max 120 chars

    public ListBeneficiariesRequest() {
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        if (pageSize > 0) {
            sb.append("page_size=").append(pageSize).append("&");
        }
        if (pageNumber > 0) {
            sb.append("page_number=").append(pageNumber).append("&");
        }
        if (entityType != null && !entityType.isEmpty()) {
            sb.append("entity_type=").append(entityType).append("&");
        }
        if (nickname != null && !nickname.isEmpty()) {
            sb.append("nickname=").append(nickname).append("&");
        }
        if (currency != null && !currency.isEmpty()) {
            sb.append("currency=").append(currency).append("&");
        }
        if (companyName != null && !companyName.isEmpty()) {
            sb.append("company_name=").append(companyName).append("&");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '&') {
            sb.setLength(sb.length() - 1);
        }
        return sb.length() > 0 ? "?" + sb : "";
    }
}
