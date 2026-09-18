package com.uqpay.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.banking.BankingClient;
import com.uqpay.sdk.banking.model.Deposit;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;
import static org.assertj.core.api.Assertions.assertThat;

class DepositContractTest {
 @Test void frozenDepositResponses() throws Exception {
  ObjectMapper mapper=new ObjectMapper();JsonNode cases;
  try(InputStream input=getClass().getResourceAsStream("/deposit-contract.json")) {assertThat(input).isNotNull();cases=mapper.readTree(input);}
  AtomicReference<JsonNode> current=new AtomicReference<>();AtomicReference<Request> request=new AtomicReference<>();
  OkHttpClient transport=new OkHttpClient.Builder().addInterceptor(chain->{request.set(chain.request());return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).message("OK").body(ResponseBody.create(current.get().toString(),MediaType.parse("application/json"))).build();}).build();
  ApiClient api=new ApiClient(Configuration.builder().clientId("client").apiKey("offline").baseUrlOverride("https://example.test").httpClient(transport).build(),()->"offline-token");
  BankingClient client=new BankingClient(api);
  for(JsonNode fixture:cases) {
   current.set(fixture.get("body"));Deposit result=client.getDeposits().get("deposit-1");
   assertThat(request.get().method()).isEqualTo("GET");assertThat(request.get().url().encodedPath()).isEqualTo("/v1/deposit/deposit-1");
   ObjectNode expected=fixture.get("body").deepCopy();
   if(expected.get("complete_time").isNull()) {assertThat(result.getCompleteTime()).isNull();expected.remove("complete_time");}
   assertThat(mapper.readTree(api.getObjectMapper().writeValueAsString(result))).as(fixture.get("name").asText()).isEqualTo(expected);
  }
 }
}
