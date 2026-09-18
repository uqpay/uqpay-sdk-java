package com.uqpay.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.config.Environment;
import com.uqpay.sdk.banking.BankingClient;
import com.uqpay.sdk.issuing.IssuingClient;
import com.uqpay.sdk.payment.PaymentClient;
import com.uqpay.sdk.payment.model.*;
import com.uqpay.sdk.simulator.SimulatorClient;
import com.uqpay.sdk.simulator.model.SimulateAuthorizationRequest;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;
import static org.assertj.core.api.Assertions.assertThat;

class RemainingResponseContractTest {
 @Test void remainingFrozenResponses() throws Exception {
  ObjectMapper mapper=new ObjectMapper();JsonNode cases;
  try(InputStream input=getClass().getResourceAsStream("/remaining-responses.json")) {assertThat(input).isNotNull();cases=mapper.readTree(input);}
  AtomicReference<JsonNode> current=new AtomicReference<>();AtomicReference<Request> request=new AtomicReference<>();
  OkHttpClient transport=new OkHttpClient.Builder().addInterceptor(chain->{request.set(chain.request());return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).message("OK").body(ResponseBody.create(current.get().toString(),MediaType.parse("application/json"))).build();}).build();
  ApiClient api=new ApiClient(Configuration.builder().clientId("client").apiKey("offline").baseUrlOverride("https://api-sandbox.example.test").httpClient(transport).build(),()->"offline-token");
  BankingClient banking=new BankingClient(api);IssuingClient issuing=new IssuingClient(api);PaymentClient payment=new PaymentClient(api);SimulatorClient simulator=new SimulatorClient(api,Environment.SANDBOX);
  for(JsonNode fixture:cases) {
   current.set(fixture.get("body"));Object result;String operation=fixture.get("operation").asText();
   switch(operation) {
    case "payout":result=banking.getPayouts().get("po-1");break;
    case "transaction":result=issuing.getTransactions().get("tx-1");break;
    case "authorization":result=simulator.getIssuing().authorize(api.getObjectMapper().treeToValue(fixture.get("request"),SimulateAuthorizationRequest.class));break;
    case "bank.get":result=payment.getBankAccounts().get("ba-1");break;
    case "bank.list":ListBankAccountsRequest params=new ListBankAccountsRequest();params.setPageSize(10);params.setPageNumber(1);result=payment.getBankAccounts().list(params);break;
    case "bank.create":result=payment.getBankAccounts().create(api.getObjectMapper().treeToValue(fixture.get("request"),CreateBankAccountRequest.class));break;
    case "intent.get":result=payment.getPaymentIntents().get("pi-1");break;
    case "intent.create":result=payment.getPaymentIntents().create(api.getObjectMapper().treeToValue(fixture.get("request"),CreatePaymentIntentRequest.class));break;
    case "intent.confirm":result=payment.getPaymentIntents().confirm("pi-1",new ConfirmPaymentIntentRequest());break;
    case "attempt":result=payment.getPaymentAttempts().get("pa-1");break;
    default:throw new AssertionError(operation);
   }
   assertThat(request.get().method()).isEqualTo(fixture.get("method").asText());assertThat(request.get().url().encodedPath()).isEqualTo(fixture.get("path").asText());
   ObjectNode expected=fixture.get("body").deepCopy();
   if(result instanceof PaymentIntent) {
    PaymentIntent intent=(PaymentIntent)result;
    String[] fields={"metadata","next_action","latest_payment_attempt"};Object[] values={intent.getMetadataValue(),intent.getNextAction(),intent.getLatestPaymentAttempt()};
    for(int i=0;i<fields.length;i++) {JsonNode want=expected.get(fields[i]);if(want==null || want.isNull())assertThat(values[i]).as(fields[i]).isNull();else assertThat(mapper.<JsonNode>valueToTree(values[i])).as(fields[i]).isEqualTo(want);expected.remove(fields[i]);}
    if(!expected.has("cancel_time"))assertThat(intent.getCancelTime()).isNull();if(!expected.has("complete_time"))assertThat(intent.getCompleteTime()).isNull();
   }
   assertProvidedFields(operation+":"+fixture.get("name").asText(),mapper.readTree(api.getObjectMapper().writeValueAsString(result)),expected);
  }
 }
 private static void assertProvidedFields(String path,JsonNode actual,JsonNode expected) {
  assertThat(actual).as(path).isNotNull();
  if(expected.isObject()) {assertThat(actual.isObject()).as(path).isTrue();expected.fields().forEachRemaining(entry->assertProvidedFields(path+"."+entry.getKey(),actual.get(entry.getKey()),entry.getValue()));}
  else if(expected.isArray()) {assertThat(actual.isArray()).as(path).isTrue();assertThat(actual.size()).as(path).isEqualTo(expected.size());for(int i=0;i<expected.size();i++)assertProvidedFields(path+"["+i+"]",actual.get(i),expected.get(i));}
  else assertThat(actual).as(path).isEqualTo(expected);
 }
}
