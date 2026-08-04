package com.uqpay.sdk;

import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.connect.ConnectClient;
import com.uqpay.sdk.connect.model.RfiModels;
import com.uqpay.sdk.issuing.IssuingClient;
import com.uqpay.sdk.issuing.model.CardCapabilityModels;
import com.uqpay.sdk.payment.PaymentClient;
import com.uqpay.sdk.payment.model.TerminalModels;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BootstrapCapabilitiesContractTest {

    private static final MediaType JSON = MediaType.parse("application/json");

    @Test
    void routesNewCoreCapabilitiesAccordingToPublishedContract() throws Exception {
        List<Request> captured = new ArrayList<>();
        ApiClient apiClient = newApiClient(captured);
        ConnectClient connect = new ConnectClient(apiClient);
        IssuingClient issuing = new IssuingClient(apiClient);
        PaymentClient payment = new PaymentClient(apiClient);

        connect.getRfis().list(10, 1, "ACTION_REQUIRED");
        connect.getRfis().get("rfi_123");
        connect.getRfis().answer(new RfiModels.AnswerRequest("rfi_123", Collections.emptyList()));

        CardCapabilityModels.ElevateLimitRequest elevate = new CardCapabilityModels.ElevateLimitRequest();
        elevate.limitAmount = 1000;
        issuing.getCards().elevateLimit("card_123", elevate);

        CardCapabilityModels.EnrollNetworkProtectionRequest enroll =
                new CardCapabilityModels.EnrollNetworkProtectionRequest();
        enroll.actionCode = "41";
        issuing.getCards().enrollNetworkProtection("card_123", enroll);
        issuing.getCards().removeNetworkProtection(
                "card_123", new CardCapabilityModels.RemoveNetworkProtectionRequest());

        CardCapabilityModels.ManagePinRequest pin = new CardCapabilityModels.ManagePinRequest();
        pin.cardId = "card_123";
        pin.type = "SET";
        pin.pin = "1234";
        issuing.getCards().managePin(pin);
        issuing.getCards().listArts("product_123");

        CardCapabilityModels.SetDefaultArtRequest art = new CardCapabilityModels.SetDefaultArtRequest();
        art.cardArtId = "art_123";
        issuing.getCards().setDefaultArt(art);
        issuing.getMerchantBrands().list("Grab", null, 1, 10);

        CardCapabilityModels.ClaimUnsolicitedRefundRequest claim =
                new CardCapabilityModels.ClaimUnsolicitedRefundRequest();
        claim.relatedTransactionId = "txn_123";
        issuing.getTransactions().claimUnsolicitedRefund(claim);

        TerminalModels.RegisterRequest register = new TerminalModels.RegisterRequest();
        register.firmCode = "01";
        register.firmSn = "SN123";
        register.terminalModel = "PAX A920";
        payment.getTerminals().register(register);

        TerminalModels.GetPinKeyRequest pinKey = new TerminalModels.GetPinKeyRequest();
        pinKey.terminalId = "terminal_123";
        pinKey.prvKey = "key";
        payment.getTerminals().getPinKey(pinKey);

        assertThat(captured).extracting(Request::method).containsExactly(
                "GET", "GET", "POST", "POST", "POST", "DELETE", "POST",
                "GET", "POST", "GET", "POST", "POST", "POST");
        assertThat(captured).extracting(request -> request.url().encodedPath()).containsExactly(
                "/v1/rfis",
                "/v1/rfis/rfi_123",
                "/v1/rfis/answer",
                "/v1/issuing/cards/card_123/elevate_limit",
                "/v1/issuing/cards/card_123/risk",
                "/v1/issuing/cards/card_123/risk",
                "/v1/issuing/cards/manage/pin",
                "/v1/issuing/cards/arts",
                "/v1/issuing/cards/arts/default",
                "/v1/issuing/merchant_brands",
                "/v1/issuing/transactions/unsolicited_refund/release",
                "/v2/terminal/register",
                "/v2/terminal/getPinKey");

        assertThat(captured.get(0).url().queryParameter("status")).isEqualTo("ACTION_REQUIRED");
        assertThat(captured.get(7).url().queryParameter("card_product_id")).isEqualTo("product_123");
        assertThat(captured.get(9).url().queryParameter("display_name")).isEqualTo("Grab");
        assertThat(readBody(captured.get(5))).contains("\"risk_control\":\"network_protection\"");
        assertThat(readBody(captured.get(10))).contains("\"related_transaction_id\":\"txn_123\"");
        assertThat(captured.get(11).header("x-client-id")).isEqualTo("client_123");
        assertThat(captured.get(12).header("x-client-id")).isEqualTo("client_123");
    }

    private static ApiClient newApiClient(List<Request> captured) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    captured.add(chain.request());
                    return new Response.Builder()
                            .request(chain.request())
                            .protocol(Protocol.HTTP_1_1)
                            .code(200)
                            .message("OK")
                            .body(ResponseBody.create("{}", JSON))
                            .build();
                })
                .build();
        Configuration config = Configuration.builder()
                .clientId("client_123")
                .apiKey("api_key_123")
                .httpClient(httpClient)
                .baseUrlOverride("https://example.test")
                .build();
        return new ApiClient(config, () -> "token_123");
    }

    private static String readBody(Request request) throws IOException {
        Buffer buffer = new Buffer();
        request.body().writeTo(buffer);
        return buffer.readUtf8();
    }
}
