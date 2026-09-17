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
 }
 private static void assertPath(Request request,String path) {
  assertThat(request.method()).isEqualTo("GET");assertThat(request.url().encodedPath()).isEqualTo(path);
 }
}
