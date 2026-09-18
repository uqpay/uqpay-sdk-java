package com.uqpay.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.banking.BankingClient;
import com.uqpay.sdk.banking.model.*;
import okhttp3.*;
import okio.Buffer;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;
import static org.assertj.core.api.Assertions.assertThat;

class BeneficiaryContractTest {
 @Test void frozenBeneficiaryContract() throws Exception {
  ObjectMapper mapper=new ObjectMapper();JsonNode cases;
  try(InputStream input=getClass().getResourceAsStream("/beneficiary-contract.json")) {assertThat(input).isNotNull();cases=mapper.readTree(input);}
  AtomicReference<JsonNode> current=new AtomicReference<>();AtomicReference<Request> request=new AtomicReference<>();
  OkHttpClient transport=new OkHttpClient.Builder().addInterceptor(chain->{request.set(chain.request());return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).message("OK").body(ResponseBody.create(current.get().toString(),MediaType.parse("application/json"))).build();}).build();
  ApiClient api=new ApiClient(Configuration.builder().clientId("client").apiKey("offline").baseUrlOverride("https://example.test").httpClient(transport).build(),()->"offline-token");
  BankingClient client=new BankingClient(api);
  for(JsonNode fixture:cases) {
   current.set(fixture.get("body"));Object result;String operation=fixture.get("operation").asText();
   switch(operation) {
    case "check":result=client.getBeneficiaries().check(api.getObjectMapper().treeToValue(fixture.get("request"),BeneficiaryCheckRequest.class));break;
    case "list":ListBeneficiariesRequest params=new ListBeneficiariesRequest();params.setPageSize(10);params.setPageNumber(1);result=client.getBeneficiaries().list(params);break;
    case "get":result=client.getBeneficiaries().get("beneficiary-1");break;
    default:throw new AssertionError(operation);
   }
   assertThat(request.get().method()).isEqualTo(operation.equals("check")?"POST":"GET");
   assertThat(request.get().url().encodedPath()).isEqualTo(fixture.get("path").asText());
   if(operation.equals("check")) {Buffer buffer=new Buffer();assertThat(request.get().body()).isNotNull();request.get().body().writeTo(buffer);assertThat(mapper.readTree(buffer.readUtf8())).as(fixture.get("name").asText()).isEqualTo(fixture.get("request"));}
   assertProvidedFields(fixture.get("name").asText(),mapper.readTree(api.getObjectMapper().writeValueAsString(result)),fixture.get("body"));
  }
 }
 private static void assertProvidedFields(String path,JsonNode actual,JsonNode expected) {
  assertThat(actual).as(path).isNotNull();
  if(expected.isObject()) {assertThat(actual.isObject()).as(path).isTrue();expected.fields().forEachRemaining(entry->assertProvidedFields(path+"."+entry.getKey(),actual.get(entry.getKey()),entry.getValue()));}
  else if(expected.isArray()) {assertThat(actual.isArray()).as(path).isTrue();assertThat(actual.size()).as(path).isEqualTo(expected.size());for(int i=0;i<expected.size();i++)assertProvidedFields(path+"["+i+"]",actual.get(i),expected.get(i));}
  else assertThat(actual).as(path).isEqualTo(expected);
 }
}
