package com.uqpay.sdk;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.uqpay.sdk.issuing.model.*;
import com.uqpay.sdk.connect.model.RfiModels;
import com.uqpay.sdk.simulator.model.SimulateDepositRequest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
class ContractAlignmentTest {
 private final ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE).setSerializationInclusion(JsonInclude.Include.NON_NULL);
 @Test void pinRequestAndAsyncResponse() throws Exception {
  SetPINRequest r=new SetPINRequest();r.setCardId("card-1");r.setPin("135790");r.setType("UPDATE");r.setOldPin("024680");
  assertThat(mapper.readTree(mapper.writeValueAsString(r)).get("old_pin").asText()).isEqualTo("024680");
  SetPINResponse a=mapper.readValue("{\"request_status\":\"SUCCESS\",\"card_order_id\":\"order-1\",\"order_status\":\"PROCESSING\"}",SetPINResponse.class);
  assertThat(a.getCardOrderId()).isEqualTo("order-1");assertThat(a.getOrderStatus()).isEqualTo("PROCESSING");
  CardOrder o=mapper.readValue("{\"order_type\":\"PIN_MANAGEMENT\",\"failure_code\":\"pin_operation_failed\"}",CardOrder.class);
  assertThat(o.getFailureCode()).isEqualTo("pin_operation_failed");assertThat(o.getAmount()).isNull();
 }
 @Test void rfiTextAndObjectAttachments() throws Exception {
  RfiModels.AnswerItem r=new RfiModels.AnswerItem();r.key="note";r.type="TEXT";r.text="source of funds";
  assertThat(mapper.readTree(mapper.writeValueAsString(r)).has("attachments")).isFalse();
  RfiModels.Rfi response=mapper.readValue("{\"rfi_id\":\"ACTREQ-test\",\"request\":[{\"answer\":{\"type\":\"ATTACHMENT\",\"attachments\":[{\"file_name\":\"proof.pdf\",\"size\":42}]}}]}",RfiModels.Rfi.class);
  assertThat(response.request.get(0).answer.attachments.get(0).fileName).isEqualTo("proof.pdf");
 }
 @Test void depositAndSettlement() throws Exception {
  SimulateDepositRequest r=new SimulateDepositRequest();r.setAccountId("account-1");assertThat(mapper.readTree(mapper.writeValueAsString(r)).get("account_id").asText()).isEqualTo("account-1");
  Transaction t=mapper.readValue("{\"settlement_status\":\"SETTLED\"}",Transaction.class);assertThat(t.getSettlementStatus()).isEqualTo("SETTLED");
  assertThat(mapper.readValue("{}",Transaction.class).getSettlementStatus()).isNull();
 }

 @Test void sendsAlignedContractsThroughApiClient() throws Exception {
  java.util.List<okhttp3.Request> captured = new java.util.ArrayList<>();
  okhttp3.OkHttpClient transport = new okhttp3.OkHttpClient.Builder().addInterceptor(chain -> {
   okhttp3.Request request = chain.request(); captured.add(request);
   String body;
   switch (request.url().encodedPath()) {
    case "/v1/issuing/cards/card-1": body = "{\"card_order_id\":\"art-order\",\"order_status\":\"PROCESSING\"}"; break;
    case "/v1/issuing/cards/pin": body = "{\"request_status\":\"SUCCESS\",\"card_order_id\":\"order-1\",\"order_status\":\"PROCESSING\"}"; break;
    case "/v1/rfis/answer": body = "{\"rfi_id\":\"ACTREQ-test\",\"request\":[{\"answer\":{\"attachments\":[{\"file_name\":\"proof.pdf\"}]}}]}"; break;
    case "/v1/simulation/deposit": body = "{\"deposit_id\":\"deposit-1\",\"amount\":\"10\"}"; break;
    case "/v1/issuing/transactions/tx-1": body = "{\"settlement_status\":\"SETTLED\",\"transaction_amount\":\"123456789.01\"}"; break;
    default: throw new java.io.IOException("Unexpected path " + request.url());
   }
   return new okhttp3.Response.Builder().request(request).protocol(okhttp3.Protocol.HTTP_1_1).code(200).message("OK")
       .body(okhttp3.ResponseBody.create(body, okhttp3.MediaType.parse("application/json"))).build();
  }).build();
  com.uqpay.sdk.config.Configuration config = com.uqpay.sdk.config.Configuration.builder()
      .clientId("offline-client").apiKey("offline-key").httpClient(transport).baseUrlOverride("https://example.test").build();
  com.uqpay.sdk.common.ApiClient api = new com.uqpay.sdk.common.ApiClient(config, () -> "offline-token");
  com.uqpay.sdk.issuing.IssuingClient issuing = new com.uqpay.sdk.issuing.IssuingClient(api);
  SetPINRequest pin = new SetPINRequest();pin.setCardId("card-1");pin.setPin("135790");pin.setType("UPDATE");pin.setOldPin("024680");
  assertThat(issuing.getCards().resetPIN(pin).getOrderStatus()).isEqualTo("PROCESSING");
  okio.Buffer buffer = new okio.Buffer();captured.get(0).body().writeTo(buffer);
  assertThat(mapper.readTree(buffer.readUtf8()).get("old_pin").asText()).isEqualTo("024680");
  RfiModels.AnswerItem answer = new RfiModels.AnswerItem();answer.key="note";answer.type="TEXT";answer.text="source of funds";
  RfiModels.Rfi rfi = new com.uqpay.sdk.connect.ConnectClient(api).getRfis().answer(new RfiModels.AnswerRequest("ACTREQ-test",java.util.Collections.singletonList(answer)));
  assertThat(rfi.request.get(0).answer.attachments.get(0).fileName).isEqualTo("proof.pdf");
  buffer = new okio.Buffer();captured.get(1).body().writeTo(buffer);
  assertThat(mapper.readTree(buffer.readUtf8()).get("answer").get(0).get("text").asText()).isEqualTo("source of funds");
  SimulateDepositRequest deposit = new SimulateDepositRequest();deposit.setAccountId("account-1");deposit.setAmount(10.0);deposit.setCurrency("SGD");deposit.setSenderSwiftCode("WELGBE22");
  new com.uqpay.sdk.simulator.SimulatorClient(api,com.uqpay.sdk.config.Environment.SANDBOX).getDeposits().simulate(deposit);
  buffer = new okio.Buffer();captured.get(2).body().writeTo(buffer);
  assertThat(mapper.readTree(buffer.readUtf8()).get("account_id").asText()).isEqualTo("account-1");
  assertThat(issuing.getTransactions().get("tx-1").getSettlementStatus()).isEqualTo("SETTLED");
  CardUpdateRequest art = new CardUpdateRequest();art.setCardArtId("art-1");art.setNameOnCard("Test");
  assertThat(issuing.getCards().update("card-1",art).getOrderStatus()).isEqualTo("PROCESSING");
  buffer = new okio.Buffer();captured.get(captured.size()-1).body().writeTo(buffer);
  com.fasterxml.jackson.databind.JsonNode sent = mapper.readTree(buffer.readUtf8());
  assertThat(sent.get("card_art_id").asText()).isEqualTo("art-1");
  assertThat(sent.get("name_on_card").asText()).isEqualTo("Test");
 }
}
