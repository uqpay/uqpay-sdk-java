package com.uqpay.sdk.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uqpay.sdk.payment.model.*;
import com.uqpay.sdk.webhook.model.ChargebackAlertData;
import com.uqpay.sdk.webhook.model.PaymentMethodData;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentApiSyncTest {
 private final ObjectMapper mapper = new ObjectMapper();
 @Test void captureWireAndAcceptanceArePreserved() throws Exception {
  java.util.List<okhttp3.Request> requests = new java.util.ArrayList<>();
  String response = "{\"payment_intent_id\":\"pi-1\",\"intent_status\":\"PARTIALLY_CAPTURED\",\"captured_amount\":\"6.00\",\"authorization_type\":\"pre_authorization\",\"authorization_expires_at\":null,\"available_to_capture\":\"0.00\",\"available_to_void\":\"0.00\",\"capture_id\":\"cap-1\",\"merchant_capture_order_id\":\"order-cap-1\",\"current_funds_action\":{\"type\":\"release\"},\"capture_records\":[{\"capture_id\":\"cap-1\",\"merchant_capture_order_id\":null,\"capture_sequence\":1,\"capture_amount\":\"6.00\",\"capture_mode\":\"final\",\"channel_capture_status\":0,\"channel_capture_time\":null,\"complete_time\":null}]}";
  okhttp3.OkHttpClient transport = new okhttp3.OkHttpClient.Builder().addInterceptor(chain -> {
   requests.add(chain.request());
   return new okhttp3.Response.Builder().request(chain.request()).protocol(okhttp3.Protocol.HTTP_1_1).code(200).message("OK")
    .body(okhttp3.ResponseBody.create(response, okhttp3.MediaType.parse("application/json"))).build();
  }).build();
  com.uqpay.sdk.config.Configuration config = com.uqpay.sdk.config.Configuration.builder().clientId("offline-client").apiKey("offline-key").httpClient(transport).baseUrlOverride("https://example.test").build();
  PaymentIntentsService service = new PaymentIntentsService(new com.uqpay.sdk.common.ApiClient(config, () -> "offline-token"));
  CardPresent card = new CardPresent();card.setAuthorizationType("pre_authorization");card.setAutoCapture(false);
  PaymentMethod method = new PaymentMethod();method.setType("card_present");method.setCardPresent(card);
  CreatePaymentIntentRequest create = new CreatePaymentIntentRequest();create.setAuthorizationType("pre_authorization");create.setPaymentMethod(method);
  service.create(create);
  ConfirmPaymentIntentRequest confirm = new ConfirmPaymentIntentRequest();confirm.setAuthorizationType("pre_authorization");confirm.setPaymentMethod(method);service.confirm("pi-1",confirm);
  CapturePaymentIntentRequest capture = new CapturePaymentIntentRequest();capture.setAmountToCapture("6.00");capture.setFinalCapture(true);capture.setMerchantCaptureOrderId("order-cap-1");
  PaymentIntent result = service.capture("pi-1",capture);
  assertThat(requests).hasSize(3);
  String[] paths = {"/v2/payment_intents/create", "/v2/payment_intents/pi-1/confirm", "/v2/payment_intents/pi-1/capture"};
  for (int i = 0; i < paths.length; i++) { assertThat(requests.get(i).method()).isEqualTo("POST"); assertThat(requests.get(i).url().encodedPath()).isEqualTo(paths[i]); }
  okio.Buffer body = new okio.Buffer();requests.get(0).body().writeTo(body);
  com.fasterxml.jackson.databind.JsonNode createWire = mapper.readTree(body.readUtf8());
  assertThat(createWire.get("authorization_type").asText()).isEqualTo("pre_authorization");
  assertThat(createWire.get("payment_method").get("card_present").get("authorization_type").asText()).isEqualTo("pre_authorization");
  assertThat(createWire.get("payment_method").get("card_present").get("auto_capture").asBoolean()).isFalse();
  body = new okio.Buffer();requests.get(1).body().writeTo(body);
  assertThat(mapper.readTree(body.readUtf8()).get("authorization_type").asText()).isEqualTo("pre_authorization");
  body = new okio.Buffer();requests.get(2).body().writeTo(body);
  com.fasterxml.jackson.databind.JsonNode wire = mapper.readTree(body.readUtf8());
  assertThat(wire.get("amount_to_capture").isTextual()).isTrue();assertThat(wire.get("final_capture").asBoolean()).isTrue();assertThat(wire.get("merchant_capture_order_id").asText()).isEqualTo("order-cap-1");
  assertThat(result.getCaptureId()).isEqualTo("cap-1");assertThat(result.getCapturedAmount()).isEqualTo("6.00");assertThat(result.getAuthorizationExpiresAt()).isNull();assertThat(result.getAvailableToCapture()).isEqualTo("0.00");assertThat(result.getCaptureRecords().get(0).getChannelCaptureStatus()).isZero();assertThat(result.getCaptureRecords().get(0).getCompleteTime()).isNull();
  CaptureRecord record = result.getCaptureRecords().get(0);
  assertThat(record.getCaptureSequence()).isEqualTo(1);assertThat(record.getCaptureAmount()).isEqualTo("6.00");assertThat(record.getCaptureMode()).isEqualTo("final");assertThat(record.getMerchantCaptureOrderId()).isNull();assertThat(record.getChannelCaptureTime()).isNull();assertThat(record.getCompleteTime()).isNull();
  assertThat(mapper.writeValueAsString(new CapturePaymentIntentRequest())).isEqualTo("{}");
  assertThat(mapper.readValue("{\"intent_status\":\"EXPIRED\"}",PaymentIntent.class).getPaymentIntentStatus()).isEqualTo("EXPIRED");
  assertThat(mapper.readValue("{\"authorization_type\":\"pre_authorization\"}",PaymentAttempt.class).getAuthorizationType()).isEqualTo("pre_authorization");
 }
 @Test void chargebackAndIssuerCountryArePreserved() throws Exception {
  ChargebackAlertData alert=mapper.readValue("{\"card_scheme\":\"VISA\",\"card_masked\":\"411111******1111\",\"auth_code\":\"test\",\"arn\":\"reference\",\"order_status\":\"SUCCEEDED\",\"alert_status\":\"EXPIRED\"}",ChargebackAlertData.class);
  assertThat(alert.getCardScheme()).isEqualTo("VISA");assertThat(alert.getCardMasked()).isEqualTo("411111******1111");assertThat(alert.getAuthCode()).isEqualTo("test");assertThat(alert.getArn()).isEqualTo("reference");assertThat(alert.getOrderStatus()).isEqualTo("SUCCEEDED");assertThat(alert.getAlertStatus()).isEqualTo("EXPIRED");
  PaymentMethodData method=mapper.readValue("{\"card\":{\"issuer_country_code\":\"SG\"},\"card_present\":{\"issuer_country_code\":\"\"}}",PaymentMethodData.class);
  assertThat(method.getCard().getIssuerCountryCode()).isEqualTo("SG");assertThat(method.getCardPresent().getIssuerCountryCode()).isEmpty();
 }
}
