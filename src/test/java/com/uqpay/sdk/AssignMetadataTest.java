package com.uqpay.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.model.AssignCardRequest;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AssignMetadataTest {
 @Test void publicClientPreservesPersonalVisaMetadataAndOmitsForExistingCalls() throws Exception {
  ObjectMapper mapper=new ObjectMapper();
  java.util.List<JsonNode> bodies=new java.util.ArrayList<>();
  okhttp3.OkHttpClient transport=new okhttp3.OkHttpClient.Builder().addInterceptor(chain->{
   okhttp3.Request r=chain.request();String response;
   if(r.url().encodedPath().equals("/api/v1/connect/token")) {
    response="{\"auth_token\":\"offline-token\",\"expired_at\":4102444800}";
   } else {
    assertThat(r.method()).isEqualTo("POST");assertThat(r.url().encodedPath()).isEqualTo("/v1/issuing/cards/assign");
    okio.Buffer buffer=new okio.Buffer();r.body().writeTo(buffer);bodies.add(mapper.readTree(buffer.readUtf8()));response="{\"card_id\":\"card-1\"}";
   }
   return new okhttp3.Response.Builder().request(r).protocol(okhttp3.Protocol.HTTP_1_1).code(200).message("OK").body(okhttp3.ResponseBody.create(response,okhttp3.MediaType.parse("application/json"))).build();
  }).build();
  UqpayClient client=new UqpayClient(Configuration.builder().clientId("offline-client").apiKey("offline-key").baseUrlOverride("https://example.test").httpClient(transport).build());
  AssignCardRequest request=new AssignCardRequest();request.setCardholderId("holder-1");request.setCardNumber("example-pan");request.setCardCurrency("USD");request.setCardMode("SINGLE");
  assertThat(client.issuing().getCards().assign(request).getCardId()).isEqualTo("card-1");
  assertThat(bodies.get(0)).isEqualTo(mapper.readTree("{\"cardholder_id\":\"holder-1\",\"card_number\":\"example-pan\",\"card_currency\":\"USD\",\"card_mode\":\"SINGLE\"}"));
  request.setMetadata(Map.of("card_sku_code","SKU123456789"));
  assertThat(client.issuing().getCards().assign(request).getCardId()).isEqualTo("card-1");
  assertThat(bodies.get(1)).isEqualTo(mapper.readTree("{\"cardholder_id\":\"holder-1\",\"card_number\":\"example-pan\",\"card_currency\":\"USD\",\"card_mode\":\"SINGLE\",\"metadata\":{\"card_sku_code\":\"SKU123456789\"}}"));
 }
}
