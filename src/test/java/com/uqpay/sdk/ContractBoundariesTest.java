package com.uqpay.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uqpay.sdk.common.*;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.IssuingClient;
import com.uqpay.sdk.issuing.model.*;
import com.uqpay.sdk.payment.PaymentClient;
import com.uqpay.sdk.payment.model.CreatePaymentIntentRequest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ContractBoundariesTest {
 @Test void kycBranchesPagingAndProxyHeaders() throws Exception {
  ObjectMapper mapper = new ObjectMapper();
  java.util.List<okhttp3.Request> captured = new java.util.ArrayList<>();
  okhttp3.OkHttpClient transport = new okhttp3.OkHttpClient.Builder().addInterceptor(chain -> {
   okhttp3.Request request=chain.request();captured.add(request);
   return new okhttp3.Response.Builder().request(request).protocol(okhttp3.Protocol.HTTP_1_1).code(200).message("OK").body(okhttp3.ResponseBody.create("{}",okhttp3.MediaType.parse("application/json"))).build();
  }).build();
  ApiClient api = new ApiClient(Configuration.builder().clientId("client").apiKey("offline").baseUrlOverride("https://example.test").httpClient(transport).build(), () -> "offline-token");
  IssuingClient issuing = new IssuingClient(api);
  for (String provider : new String[]{"SUMSUB","MYINFO","JUMIO","DIDIT","SHUFTI","REGTANK"}) {
   for (int length : new int[]{9,10,64,65}) for (String dob : new String[]{"2009-09-17","2008-09-17","1947-09-17","1946-09-17"}) {
    com.fasterxml.jackson.databind.node.ObjectNode fields=mapper.createObjectNode();
    fields.put("email","test@example.test").put("first_name","Test").put("last_name","User").put("phone_number","81234567").put("country_code","SG").put("date_of_birth",dob);
    fields.putObject("kyc_verification").put("method","THIRD_PARTY").putObject("kyc_proof").put("provider",provider).put("reference_id","r".repeat(length));
    issuing.getCardholders().create(mapper.treeToValue(fields,CreateCardholderRequest.class));
    assertThat(body(mapper,captured)).isEqualTo(fields);
    // Update does not accept name changes; verify the shared KYC/date fields.
    com.fasterxml.jackson.databind.node.ObjectNode update=fields.deepCopy();update.remove(java.util.Arrays.asList("first_name","last_name"));
    issuing.getCardholders().update("holder-1",mapper.treeToValue(update,UpdateCardholderRequest.class));
    assertThat(body(mapper,captured)).isEqualTo(update);
    CreateCardRequest card=new CreateCardRequest();card.setCardholderId("holder-1");card.setCardProductId("product-1");card.setCardCurrency("SGD");card.setCardholderRequiredFields(mapper.treeToValue(fields,CardholderRequiredFields.class));
    issuing.getCards().create(card);
    assertThat(body(mapper,captured).get("cardholder_required_fields")).isEqualTo(fields);
   }
  }
  for (int size : new int[]{1,10,100}) {
   ListCardsRequest list=new ListCardsRequest();list.setPageSize(size);list.setPageNumber(1);issuing.getCards().list(list);
   assertThat(captured.get(captured.size()-1).url().queryParameter("page_size")).isEqualTo(String.valueOf(size));
  }
  PaymentClient payment=new PaymentClient(api);
  // D189-D196: each GET accepts delegation without caller idempotency keys.
  for (String account : new String[]{"sub-account", ""}) {
   RequestOptions options=account.isEmpty()?null:RequestOptions.builder().onBehalfOf(account).build();
   payment.getBalances().list(new com.uqpay.sdk.payment.model.ListPaymentBalancesRequest(),options);checkGet(captured,"/v2/payment/balances",account);
   payment.getBalances().get("USD",options);checkGet(captured,"/v2/payment/balances/USD",account);
   payment.getBankAccounts().list(new com.uqpay.sdk.payment.model.ListBankAccountsRequest(),options);checkGet(captured,"/v2/payment/bankaccount",account);
   payment.getBankAccounts().get("ba-1",options);checkGet(captured,"/v2/payment/bankaccount/ba-1",account);
   payment.getPayouts().list(new com.uqpay.sdk.payment.model.ListPayoutsRequest(),options);checkGet(captured,"/v2/payment/payout",account);
   payment.getPayouts().get("po-1",options);checkGet(captured,"/v2/payment/payout/po-1",account);
   payment.getReports().listSettlements(new com.uqpay.sdk.payment.model.ListSettlementsRequest(),options);checkGet(captured,"/v2/payment/settlements",account);
   payment.getPaymentIntents().get("pi-1",options);checkGet(captured,"/v2/payment_intents/pi-1",account);
  }
  CreatePaymentIntentRequest request=new CreatePaymentIntentRequest();request.setAmount("1.00");request.setCurrency("USD");
  payment.getPaymentIntents().create(request,RequestOptions.builder().idempotencyKey("fixed-key").build());
  assertThat(captured.get(captured.size()-1).header("x-idempotency-key")).isEqualTo("fixed-key");
 }
 private void checkGet(java.util.List<okhttp3.Request> requests,String path,String account) {
  okhttp3.Request request=requests.get(requests.size()-1);
  assertThat(request.method()).isEqualTo("GET");assertThat(request.url().encodedPath()).isEqualTo(path);
  assertThat(request.header("x-client-id")).isEqualTo("client");
  assertThat(request.header("x-on-behalf-of")==null?"":request.header("x-on-behalf-of")).isEqualTo(account);
 }
 private JsonNode body(ObjectMapper mapper,java.util.List<okhttp3.Request> requests) throws Exception {
  okio.Buffer buffer=new okio.Buffer();requests.get(requests.size()-1).body().writeTo(buffer);return mapper.readTree(buffer.readUtf8());
 }
}
