package com.uqpay.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.connect.ConnectClient;
import com.uqpay.sdk.connect.model.RfiModels;
import com.uqpay.sdk.issuing.IssuingClient;
import com.uqpay.sdk.issuing.model.CardOrder;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;
import static org.assertj.core.api.Assertions.assertThat;

class RfiOrderContractTest {
 @Test void rfiListDetailAndPinOrderResponses() throws Exception {
  ObjectMapper mapper=new ObjectMapper();
  JsonNode fixtures;
  try(InputStream stream=getClass().getResourceAsStream("/rfi-orders.json")) {
   assertThat(stream).isNotNull(); fixtures=mapper.readTree(stream);
  }
  AtomicReference<JsonNode> current=new AtomicReference<>();
  AtomicReference<Request> request=new AtomicReference<>();
  OkHttpClient transport=new OkHttpClient.Builder().addInterceptor(chain->{
   request.set(chain.request());
   return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).message("OK").body(ResponseBody.create(current.get().toString(),MediaType.parse("application/json"))).build();
  }).build();
  ApiClient api=new ApiClient(Configuration.builder().clientId("client").apiKey("offline").baseUrlOverride("https://example.test").httpClient(transport).build(),()->"offline-token");
  ConnectClient connect=new ConnectClient(api);
  for(JsonNode rfi:fixtures.get("rfis")) {
   current.set(rfi);
   RfiModels.Rfi detail=connect.getRfis().get(rfi.get("rfi_id").asText());
   assertThat(mapper.readTree(api.getObjectMapper().writeValueAsString(detail))).isEqualTo(rfi);
   assertPath(request.get(),"/v1/rfis/"+rfi.get("rfi_id").asText());
   JsonNode page=mapper.createObjectNode().put("total_pages",3).put("total_items",21).set("data",mapper.createArrayNode().add(rfi));
   current.set(page);
   RfiModels.ListResponse result=connect.getRfis().list(10,2,"ACTION_REQUIRED");
   assertThat(mapper.readTree(api.getObjectMapper().writeValueAsString(result))).isEqualTo(page);
   assertPath(request.get(),"/v1/rfis");
   assertThat(request.get().url().queryParameter("page_size")).isEqualTo("10");
   assertThat(request.get().url().queryParameter("page_number")).isEqualTo("2");
   assertThat(request.get().url().queryParameter("status")).isEqualTo("ACTION_REQUIRED");
  }
  IssuingClient issuing=new IssuingClient(api);
  for(JsonNode fixture:fixtures.get("orders")) {
   current.set(fixture);
   CardOrder order=issuing.getCards().getOrder(fixture.get("card_order_id").asText());
   assertPath(request.get(),"/v1/issuing/cards/"+fixture.get("card_order_id").asText()+"/order");
   JsonNode actual=api.getObjectMapper().valueToTree(order);
   fixture.fields().forEachRemaining(entry->{
    if(!entry.getKey().equals("amount"))assertThat(actual.get(entry.getKey())).as(entry.getKey()).isEqualTo(entry.getValue());
   });
   if(fixture.get("order_type").asText().equals("PIN_MANAGEMENT")) {
    assertThat(order.getAmount()).isNull(); assertThat(order.getCardCurrency()).isNull();
   } else {
    assertThat(order.getAmount()).isEqualTo(1.25); assertThat(order.getCardCurrency()).isEqualTo("USD");
   }
   if(!fixture.has("failure_code"))assertThat(order.getFailureCode()).isNull();
  }
  // D044/D094: detail-only status; missing detail is legacy robustness.
  for(String status:new String[]{"UNKNOWN","UNSETTLED","SETTLED","NOT_APPLICABLE",null}) {
   com.fasterxml.jackson.databind.node.ObjectNode detail=mapper.createObjectNode().put("transaction_id","tx-1");
   if(status!=null)detail.put("settlement_status",status);
   current.set(detail);
   com.uqpay.sdk.issuing.model.Transaction tx=issuing.getTransactions().get("tx-1");
   assertPath(request.get(),"/v1/issuing/transactions/tx-1");
   assertThat(tx.getTransactionId()).isEqualTo("tx-1");assertThat(tx.getSettlementStatus()).isEqualTo(status);
  }
  current.set(mapper.readTree("{\"data\":[{\"transaction_id\":\"tx-1\"}],\"total_pages\":1,\"total_items\":1}"));
  com.uqpay.sdk.issuing.model.ListTransactionsRequest params=new com.uqpay.sdk.issuing.model.ListTransactionsRequest();params.setPageSize(10);params.setPageNumber(1);
  com.uqpay.sdk.issuing.model.ListTransactionsResponse page=issuing.getTransactions().list(params);
  assertPath(request.get(),"/v1/issuing/transactions");
  assertThat(page.getTotalItems()).isEqualTo(1);assertThat(page.getTotalPages()).isEqualTo(1);assertThat(page.getData()).hasSize(1);
  assertThat(page.getData().get(0).getTransactionId()).isEqualTo("tx-1");assertThat(page.getData().get(0).getSettlementStatus()).isNull();

  // Frozen account summaries/details and issuing money: every provided field.
  JsonNode moneyCases;
  try(InputStream stream=getClass().getResourceAsStream("/account-money.json")) {assertThat(stream).isNotNull();moneyCases=mapper.readTree(stream);}
  for(JsonNode fixture:moneyCases) {
   current.set(fixture.get("body"));Object result;
   switch(fixture.get("operation").asText()) {
    case "accounts.list":
     com.uqpay.sdk.connect.model.ListAccountsRequest accountParams=new com.uqpay.sdk.connect.model.ListAccountsRequest();accountParams.setPageSize(10);accountParams.setPageNumber(1);
     result=connect.getAccounts().list(accountParams);break;
    case "accounts.get":result=connect.getAccounts().get("account-1");break;
    case "transactions.get":result=issuing.getTransactions().get("tx-1");break;
    case "transactions.list":result=issuing.getTransactions().list(params);break;
    case "transfers.get":result=issuing.getTransfers().retrieve("transfer-1");break;
    default:throw new AssertionError(fixture.get("operation"));
   }
   assertPath(request.get(),fixture.get("path").asText());
   assertProvidedFields(fixture.get("operation").asText(),mapper.readTree(api.getObjectMapper().writeValueAsString(result)),fixture.get("body"));
  }

 }
 private static void assertPath(Request request,String path) {
  assertThat(request.method()).isEqualTo("GET");assertThat(request.url().encodedPath()).isEqualTo(path);
 }
 // Typed models may add default fields; compare every provided field recursively.
 private static void assertProvidedFields(String path,JsonNode actual,JsonNode expected) {
  assertThat(actual).as(path).isNotNull();
  if(expected.isObject()) {
   assertThat(actual.isObject()).as(path).isTrue();
   expected.fields().forEachRemaining(entry->assertProvidedFields(path+"."+entry.getKey(),actual.get(entry.getKey()),entry.getValue()));
  } else if(expected.isArray()) {
   assertThat(actual.isArray()).as(path).isTrue();assertThat(actual.size()).as(path).isEqualTo(expected.size());
   for(int i=0;i<expected.size();i++)assertProvidedFields(path+"["+i+"]",actual.get(i),expected.get(i));
  } else assertThat(actual).as(path).isEqualTo(expected);
 }

}
