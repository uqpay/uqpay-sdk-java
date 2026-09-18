package com.uqpay.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.IssuingClient;
import com.uqpay.sdk.issuing.model.*;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;
import static org.assertj.core.api.Assertions.assertThat;

class IssuingResponseContractTest {
 @Test void frozenIssuingResponses() throws Exception {
  ObjectMapper mapper=new ObjectMapper();
  JsonNode cases;
  try(InputStream input=getClass().getResourceAsStream("/issuing-responses.json")) {assertThat(input).isNotNull();cases=mapper.readTree(input);}
  AtomicReference<JsonNode> current=new AtomicReference<>();
  AtomicReference<Request> request=new AtomicReference<>();
  OkHttpClient transport=new OkHttpClient.Builder().addInterceptor(chain->{request.set(chain.request());return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).message("OK").body(ResponseBody.create(current.get().toString(),MediaType.parse("application/json"))).build();}).build();
  ApiClient api=new ApiClient(Configuration.builder().clientId("client").apiKey("offline").baseUrlOverride("https://example.test").httpClient(transport).build(),()->"offline-token");
  IssuingClient client=new IssuingClient(api);
  for(JsonNode fixture:cases) {
   current.set(fixture.get("body"));Object result;RetrieveCardResponse card=null;
   String operation=fixture.get("operation").asText();
   switch(operation) {
    case "cards.list":
     ListCardsRequest cards=new ListCardsRequest();cards.setPageSize(10);cards.setPageNumber(1);
     ListCardsResponse page=client.getCards().list(cards);assertThat(page.getData()).hasSize(1);card=page.getData().get(0);result=page;break;
    case "cards.get":card=client.getCards().get("card-1");result=card;break;
    case "cardholders.list":
     ListCardholdersRequest holders=new ListCardholdersRequest();holders.setPageSize(10);holders.setPageNumber(1);result=client.getCardholders().list(holders);break;
    case "cardholders.get":result=client.getCardholders().get("holder-1");break;
    case "products.list":
     ListProductsRequest products=new ListProductsRequest();products.setPageSize(10);products.setPageNumber(1);result=client.getProducts().list(products);break;
    case "cards.status":
     UpdateCardStatusRequest status=new UpdateCardStatusRequest();status.setCardStatus("FROZEN");result=client.getCards().updateStatus("card-1",status);break;
    default:throw new AssertionError(operation);
   }
   assertThat(request.get().method()).isEqualTo(operation.equals("cards.status")?"POST":"GET");
   assertThat(request.get().url().encodedPath()).isEqualTo(fixture.get("path").asText());
   JsonNode expected=fixture.get("body").deepCopy();
   if(card!=null) {
    ObjectNode fields=(ObjectNode)(operation.equals("cards.list")?expected.get("data").get(0):expected);
    if(operation.equals("cards.get"))fields.put("card_limit","1.25");
    JsonNode risk=fields.get("risk_controls");
    if(risk==null || risk.isNull()) {assertThat(card.getRiskControls()).isNull();fields.remove("risk_controls");}
    // The additive accessor preserves the wire value; the legacy getter remains a Map view.
    JsonNode metadata=fields.get("metadata");
    if(metadata==null || metadata.isNull()) {
     assertThat(card.getMetadataValue()).isNull();assertThat(card.getMetadata()).isEmpty();
    } else {
     assertThat(mapper.<JsonNode>valueToTree(card.getMetadataValue())).as(operation+" raw metadata").isEqualTo(metadata);
     if(metadata.isObject())assertThat(mapper.<JsonNode>valueToTree(card.getMetadata())).isEqualTo(metadata);
     else assertThat(card.getMetadata()).isEmpty();
    }
    fields.remove("metadata"); // Both public accessors checked explicitly above.

   }
   if(fixture.get("name").asText().equals("missing")) {
    if(result instanceof Cardholder) {assertThat(((Cardholder)result).getGender()).isNull();assertThat(((Cardholder)result).getNationality()).isNull();}
    if(result instanceof ListCardholdersResponse) {Cardholder holder=((ListCardholdersResponse)result).getData().get(0);assertThat(holder.getGender()).isNull();assertThat(holder.getNationality()).isNull();}
   }
   if(result instanceof ListProductsResponse && fixture.get("name").asText().equals("optional-absent")) {
    CardProduct product=((ListProductsResponse)result).getData().get(0);assertThat(product.getModeType()).isNull();assertThat(product.getMaxCardQuota()).isNull();
   }
   if(result instanceof CardStatusResponse && fixture.get("name").asText().equals("reason-absent"))assertThat(((CardStatusResponse)result).getUpdateReason()).isNull();
   assertProvidedFields(operation+":"+fixture.get("name").asText(),mapper.readTree(api.getObjectMapper().writeValueAsString(result)),expected);
  }
 }
 private static void assertProvidedFields(String path,JsonNode actual,JsonNode expected) {
  assertThat(actual).as(path).isNotNull();
  if(expected.isObject()) {
   assertThat(actual.isObject()).as(path).isTrue();expected.fields().forEachRemaining(entry->assertProvidedFields(path+"."+entry.getKey(),actual.get(entry.getKey()),entry.getValue()));
  } else if(expected.isArray()) {
   assertThat(actual.isArray()).as(path).isTrue();assertThat(actual.size()).as(path).isEqualTo(expected.size());
   for(int i=0;i<expected.size();i++)assertProvidedFields(path+"["+i+"]",actual.get(i),expected.get(i));
  } else assertThat(actual).as(path).isEqualTo(expected);
 }
}
