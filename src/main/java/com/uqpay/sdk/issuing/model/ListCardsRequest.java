package com.uqpay.sdk.issuing.model;

public class ListCardsRequest {

    private int pageSize; // required; 10-100, default 10
    private int pageNumber; // required; >= 1, default 1
    private String cardNumber; // optional; full card number for filtering
    private String cardStatus; // optional; PENDING | ACTIVE | FROZEN | BLOCKED | CANCELLED | LOST | STOLEN | FAILED
    private String cardholderId; // optional; UUID

    public ListCardsRequest() {
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardholderId() {
        return cardholderId;
    }

    public void setCardholderId(String cardholderId) {
        this.cardholderId = cardholderId;
    }

    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        if (pageSize > 0) {
            sb.append("page_size=").append(pageSize).append("&");
        }
        if (pageNumber > 0) {
            sb.append("page_number=").append(pageNumber).append("&");
        }
        if (cardNumber != null && !cardNumber.isEmpty()) {
            sb.append("card_number=").append(cardNumber).append("&");
        }
        if (cardStatus != null && !cardStatus.isEmpty()) {
            sb.append("card_status=").append(cardStatus).append("&");
        }
        if (cardholderId != null && !cardholderId.isEmpty()) {
            sb.append("cardholder_id=").append(cardholderId).append("&");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '&') {
            sb.setLength(sb.length() - 1);
        }
        return sb.length() > 0 ? "?" + sb : "";
    }
}
