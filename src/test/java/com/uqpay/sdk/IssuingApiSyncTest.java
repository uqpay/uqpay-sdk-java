package com.uqpay.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.issuing.IssuingClient;
import com.uqpay.sdk.issuing.model.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class IssuingApiSyncTest {
 @Test void supportingOnlyUpdatesDefaultHolderAndSparseAddress() throws Exception {
  ObjectMapper mapper = new ObjectMapper();
  java.util.List<okhttp3.Request> captured = new java.util.ArrayList<>();
  okhttp3.OkHttpClient transport = new okhttp3.OkHttpClient.Builder().addInterceptor(chain -> {
   captured.add(chain.request());
   return new okhttp3.Response.Builder().request(chain.request()).protocol(okhttp3.Protocol.HTTP_1_1).code(200).message("OK").body(okhttp3.ResponseBody.create("{\"cardholder_id\":\"holder-1\",\"residential_address\":{\"line2\":null}}",okhttp3.MediaType.parse("application/json"))).build();
  }).build();
  IssuingClient issuing = new IssuingClient(new ApiClient(Configuration.builder().clientId("client").apiKey("offline").baseUrlOverride("https://example.test").httpClient(transport).build(), () -> "offline-token"));
  Identity identity = new Identity();identity.setProofOfAddress("cGRm");identity.setProofOfAddressIssueDate("2026-10-01");identity.setProofOfAddressIssuingCountry("SG");identity.setIdentityVerification("a3lj");
  UpdateCardholderRequest update = new UpdateCardholderRequest();update.setIdentity(identity);
  issuing.getCardholders().update("holder-1",update);
  checkRoute(captured,"POST","/v1/issuing/cardholders/holder-1");
  JsonNode wire = body(mapper,captured);
  assertThat(wire).isEqualTo(mapper.readTree("{\"identity\":{\"proof_of_address\":\"cGRm\",\"proof_of_address_issue_date\":\"2026-10-01\",\"proof_of_address_issuing_country\":\"SG\",\"identity_verification\":\"a3lj\"}}"));
  CreateCardRequest card = new CreateCardRequest();card.setCardCurrency("USD");card.setCardProductId("product-1");issuing.getCards().create(card);
  checkRoute(captured,"POST","/v1/issuing/cards");
  assertThat(body(mapper,captured).has("cardholder_id")).isFalse();
  AssignCardRequest assign = new AssignCardRequest();assign.setCardNumber("example-pan");assign.setCardCurrency("USD");assign.setCardMode("SINGLE");issuing.getCards().assign(assign);
  checkRoute(captured,"POST","/v1/issuing/cards/assign");
  assertThat(body(mapper,captured).has("cardholder_id")).isFalse();
  Cardholder holder = issuing.getCardholders().get("holder-1");
  checkRoute(captured,"GET","/v1/issuing/cardholders/holder-1");
  assertThat(holder.getResidentialAddress()).isNotNull();
  assertThat(holder.getResidentialAddress().getLine2()).isNull();
 }
 private void checkRoute(java.util.List<okhttp3.Request> requests,String method,String path) {
  okhttp3.Request request=requests.get(requests.size()-1);
  assertThat(request.method()).isEqualTo(method);
  assertThat(request.url().encodedPath()).isEqualTo(path);
 }
 private JsonNode body(ObjectMapper mapper,java.util.List<okhttp3.Request> requests) throws Exception {
  okio.Buffer bytes = new okio.Buffer();requests.get(requests.size()-1).body().writeTo(bytes);return mapper.readTree(bytes.readUtf8());
 }
}
