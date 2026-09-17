package com.uqpay.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.uqpay.sdk.banking.BankingClient;
import com.uqpay.sdk.banking.model.Balance;
import com.uqpay.sdk.banking.model.ListBalancesRequest;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.config.Configuration;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class BalancePrecisionTest {
 // D122-D129: distinct values detect swapped fields; actual ApiClient decoding.
 @Test void allBalanceFieldsRetainStringsThroughBothRoutes() throws Exception {
  ObjectMapper mapper=new ObjectMapper();
  String[] fields={"available_balance","frozen_balance","margin_balance","prepaid_balance"};
  String[] amounts={"0.00","1.23","-0.01","12345678901234567890.12","-12345678901234567890.12","0.12345678901234567890"};
  ObjectNode current=mapper.createObjectNode();
  okhttp3.OkHttpClient transport=new okhttp3.OkHttpClient.Builder().addInterceptor(chain->{
   okhttp3.Request req=chain.request();assertThat(req.method()).isEqualTo("GET");
   JsonNode payload;
   if(req.url().encodedPath().equals("/v1/balances/USD")) payload=current;
   else {
    assertThat(req.url().encodedPath()).isEqualTo("/v1/balances");
    assertThat(req.url().queryParameter("page_size")).isEqualTo("10");
    ObjectNode list=mapper.createObjectNode();list.put("total_pages",1).put("total_items",1);list.putArray("data").add(current);payload=list;
   }
   return new okhttp3.Response.Builder().request(req).protocol(okhttp3.Protocol.HTTP_1_1).code(200).message("OK").body(okhttp3.ResponseBody.create(mapper.writeValueAsString(payload),okhttp3.MediaType.parse("application/json"))).build();
  }).build();
  BankingClient banking=new BankingClient(new ApiClient(Configuration.builder().clientId("client").apiKey("offline").baseUrlOverride("https://example.test").httpClient(transport).build(),()->"offline-token"));
  for(int i=0;i<amounts.length;i++) {
   current.removeAll();current.put("currency","USD");
   for(int j=0;j<fields.length;j++) current.put(fields[j],amounts[(i+j)%amounts.length]);
   Balance detail=banking.getBalances().get("USD");
   ListBalancesRequest request=new ListBalancesRequest();request.setPageSize(10);request.setPageNumber(1);
   java.util.List<Balance> items=banking.getBalances().list(request).getData();assertThat(items).hasSize(1);
   for(Balance value:new Balance[]{detail,items.get(0)}) {
    JsonNode wire=mapper.valueToTree(value);
    for(String field:fields) {assertThat(wire.get(field).isTextual()).isTrue();assertThat(wire.get(field)).isEqualTo(current.get(field));}
   }
  }
 }
}
