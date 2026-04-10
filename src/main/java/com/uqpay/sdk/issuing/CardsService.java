package com.uqpay.sdk.issuing;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.issuing.model.ActivateCardRequest;
import com.uqpay.sdk.issuing.model.ActivateCardResponse;
import com.uqpay.sdk.issuing.model.AssignCardRequest;
import com.uqpay.sdk.issuing.model.AssignCardResponse;
import com.uqpay.sdk.issuing.model.BulkCardCreationRequest;
import com.uqpay.sdk.issuing.model.BulkCardCreationResponse;
import com.uqpay.sdk.issuing.model.CardCreationResponse;
import com.uqpay.sdk.issuing.model.CardOrder;
import com.uqpay.sdk.issuing.model.CardOrderRequest;
import com.uqpay.sdk.issuing.model.CardStatusResponse;
import com.uqpay.sdk.issuing.model.CardUpdateRequest;
import com.uqpay.sdk.issuing.model.CardUpdatedResponse;
import com.uqpay.sdk.issuing.model.CreateCardRequest;
import com.uqpay.sdk.issuing.model.ListCardsRequest;
import com.uqpay.sdk.issuing.model.ListCardsResponse;
import com.uqpay.sdk.issuing.model.PanTokenResponse;
import com.uqpay.sdk.issuing.model.RetrieveCardResponse;
import com.uqpay.sdk.issuing.model.SecureCardInfo;
import com.uqpay.sdk.issuing.model.SetPINRequest;
import com.uqpay.sdk.issuing.model.SetPINResponse;
import com.uqpay.sdk.issuing.model.UpdateCardStatusRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class CardsService {

    private final ApiClient apiClient;

    public CardsService(@NotNull ApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient must not be null");
    }

    // =========================================================================
    // Create
    // =========================================================================

    @NotNull
    public CardCreationResponse create(@NotNull CreateCardRequest request) throws UqpayException {
        return create(request, null);
    }

    @NotNull
    public CardCreationResponse create(@NotNull CreateCardRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/cards", request, CardCreationResponse.class, options);
    }

    // =========================================================================
    // Update
    // =========================================================================

    @NotNull
    public CardUpdatedResponse update(@NotNull String cardId, @NotNull CardUpdateRequest request)
            throws UqpayException {
        return update(cardId, request, null);
    }

    @NotNull
    public CardUpdatedResponse update(@NotNull String cardId, @NotNull CardUpdateRequest request,
                                      @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(cardId, "cardId must not be null");
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/cards/" + cardId, request, CardUpdatedResponse.class, options);
    }

    // =========================================================================
    // Get
    // =========================================================================

    @NotNull
    public RetrieveCardResponse get(@NotNull String cardId) throws UqpayException {
        return get(cardId, null);
    }

    @NotNull
    public RetrieveCardResponse get(@NotNull String cardId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(cardId, "cardId must not be null");
        if (cardId.isEmpty()) {
            throw new IllegalArgumentException("cardId must not be empty");
        }
        return apiClient.get("/v1/issuing/cards/" + cardId, RetrieveCardResponse.class, options);
    }

    // =========================================================================
    // Get Secure
    // =========================================================================

    @NotNull
    public SecureCardInfo getSecure(@NotNull String cardId) throws UqpayException {
        return getSecure(cardId, null);
    }

    @NotNull
    public SecureCardInfo getSecure(@NotNull String cardId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(cardId, "cardId must not be null");
        if (cardId.isEmpty()) {
            throw new IllegalArgumentException("cardId must not be empty");
        }
        return apiClient.get("/v1/issuing/cards/" + cardId + "/secure", SecureCardInfo.class, options);
    }

    // =========================================================================
    // List
    // =========================================================================

    @NotNull
    public ListCardsResponse list(@NotNull ListCardsRequest request) throws UqpayException {
        return list(request, null);
    }

    @NotNull
    public ListCardsResponse list(@NotNull ListCardsRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        String queryString = request.toQueryString();
        return apiClient.get("/v1/issuing/cards" + queryString, ListCardsResponse.class, options);
    }

    // =========================================================================
    // Update Status
    // =========================================================================

    @NotNull
    public CardStatusResponse updateStatus(@NotNull String cardId, @NotNull UpdateCardStatusRequest request)
            throws UqpayException {
        return updateStatus(cardId, request, null);
    }

    @NotNull
    public CardStatusResponse updateStatus(@NotNull String cardId, @NotNull UpdateCardStatusRequest request,
                                           @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(cardId, "cardId must not be null");
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/cards/" + cardId + "/status", request, CardStatusResponse.class, options);
    }

    // =========================================================================
    // Recharge
    // =========================================================================

    @NotNull
    public CardOrder recharge(@NotNull String cardId, @NotNull CardOrderRequest request) throws UqpayException {
        return recharge(cardId, request, null);
    }

    @NotNull
    public CardOrder recharge(@NotNull String cardId, @NotNull CardOrderRequest request,
                              @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(cardId, "cardId must not be null");
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/cards/" + cardId + "/recharge", request, CardOrder.class, options);
    }

    // =========================================================================
    // Withdraw
    // =========================================================================

    @NotNull
    public CardOrder withdraw(@NotNull String cardId, @NotNull CardOrderRequest request) throws UqpayException {
        return withdraw(cardId, request, null);
    }

    @NotNull
    public CardOrder withdraw(@NotNull String cardId, @NotNull CardOrderRequest request,
                              @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(cardId, "cardId must not be null");
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/cards/" + cardId + "/withdraw", request, CardOrder.class, options);
    }

    // =========================================================================
    // Get Order
    // =========================================================================

    @NotNull
    public CardOrder getOrder(@NotNull String orderId) throws UqpayException {
        return getOrder(orderId, null);
    }

    @NotNull
    public CardOrder getOrder(@NotNull String orderId, @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(orderId, "orderId must not be null");
        if (orderId.isEmpty()) {
            throw new IllegalArgumentException("orderId must not be empty");
        }
        return apiClient.get("/v1/issuing/cards/" + orderId + "/order", CardOrder.class, options);
    }

    // =========================================================================
    // Activate
    // =========================================================================

    @NotNull
    public ActivateCardResponse activate(@NotNull ActivateCardRequest request) throws UqpayException {
        return activate(request, null);
    }

    @NotNull
    public ActivateCardResponse activate(@NotNull ActivateCardRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/cards/activate", request, ActivateCardResponse.class, options);
    }

    // =========================================================================
    // Reset PIN
    // =========================================================================

    @NotNull
    public SetPINResponse resetPIN(@NotNull SetPINRequest request) throws UqpayException {
        return resetPIN(request, null);
    }

    @NotNull
    public SetPINResponse resetPIN(@NotNull SetPINRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/cards/pin", request, SetPINResponse.class, options);
    }

    // =========================================================================
    // Assign
    // =========================================================================

    @NotNull
    public AssignCardResponse assign(@NotNull AssignCardRequest request) throws UqpayException {
        return assign(request, null);
    }

    @NotNull
    public AssignCardResponse assign(@NotNull AssignCardRequest request, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/cards/assign", request, AssignCardResponse.class, options);
    }

    // =========================================================================
    // Create PAN Token
    // =========================================================================

    @NotNull
    public PanTokenResponse createPanToken(@NotNull String cardId) throws UqpayException {
        return createPanToken(cardId, null);
    }

    @NotNull
    public PanTokenResponse createPanToken(@NotNull String cardId, @Nullable RequestOptions options)
            throws UqpayException {
        Objects.requireNonNull(cardId, "cardId must not be null");
        if (cardId.isEmpty()) {
            throw new IllegalArgumentException("cardId must not be empty");
        }
        return apiClient.post("/v1/issuing/cards/" + cardId + "/token", null, PanTokenResponse.class, options);
    }

    // =========================================================================
    // Bulk Create
    // =========================================================================

    @NotNull
    public BulkCardCreationResponse bulkCreate(@NotNull BulkCardCreationRequest request) throws UqpayException {
        return bulkCreate(request, null);
    }

    @NotNull
    public BulkCardCreationResponse bulkCreate(@NotNull BulkCardCreationRequest request,
                                               @Nullable RequestOptions options) throws UqpayException {
        Objects.requireNonNull(request, "request must not be null");
        return apiClient.post("/v1/issuing/cards/bulk", request, BulkCardCreationResponse.class, options);
    }
}
