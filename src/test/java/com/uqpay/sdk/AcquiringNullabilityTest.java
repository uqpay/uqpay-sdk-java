package com.uqpay.sdk;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.uqpay.sdk.webhook.model.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
class AcquiringNullabilityTest {
 // WH-AQ: missing/null merge in POJOs; raw JSON distinguishes them.
 @Test void typedEventTimesRetainNonNullValues() throws Exception {
  ObjectMapper mapper=new ObjectMapper();
  Class<?>[] types={PaymentIntentData.class,PaymentAttemptData.class,RefundData.class,PayoutAcquiringData.class,ChargebackAlertData.class};
  String[][] fields={{"complete_time","cancel_time"},{"complete_time","cancel_time"},{"complete_time"},{"complete_time"},{"appeal_time","response_time"}};
  for(int i=0;i<types.length;i++) for(String field:fields[i]) for(String value:new String[]{null,"","2026-09-17T00:00:00Z"}) {
   ObjectNode raw=mapper.createObjectNode();if(value==null)raw.putNull(field);else raw.put(field,value);
   JsonNode output=mapper.valueToTree(mapper.treeToValue(raw,types[i]));
   if(value!=null)assertThat(output.get(field)).as(types[i]+"."+field).isEqualTo(raw.get(field));
   else assertThat(output.hasNonNull(field)).isFalse();
   mapper.readValue("{}",types[i]);
  }
 }

 @Test void signedRawEventsDistinguishNullEmptyMissing() throws Exception {
  ObjectMapper mapper=new ObjectMapper();
  for(String kind:new String[]{"payment_intent.succeeded","payment_attempt.succeeded","refund.succeeded","payout.succeeded","chargeback.alert.created"}) {
   String[] fields=kind.startsWith("chargeback")?new String[]{"appeal_time","response_time"}:new String[]{"complete_time"};
   for(String mode:new String[]{"missing","null","empty","populated"}) {
    ObjectNode data=mapper.createObjectNode();
    if(!mode.equals("missing"))for(String field:fields) {if(mode.equals("null"))data.putNull(field);else data.put(field,mode.equals("empty")?"":"2026-09-17T00:00:00Z");}
    ObjectNode envelope=mapper.createObjectNode();envelope.put("event_type","acquiring."+kind);envelope.set("data",data);
    String raw=mapper.writeValueAsString(envelope),timestamp=String.valueOf(System.currentTimeMillis());
    javax.crypto.Mac mac=javax.crypto.Mac.getInstance("HmacSHA512");mac.init(new javax.crypto.spec.SecretKeySpec("offline-secret".getBytes(java.nio.charset.StandardCharsets.UTF_8),"HmacSHA512"));
    byte[] digest=mac.doFinal((raw+timestamp).getBytes(java.nio.charset.StandardCharsets.UTF_8));StringBuilder hex=new StringBuilder();for(byte value:digest)hex.append(String.format("%02x",value));
    com.uqpay.sdk.webhook.WebhookVerifier verifier=new com.uqpay.sdk.webhook.WebhookVerifier("offline-secret");
    assertThat(verifier.verifyAndParse(raw,hex.toString(),timestamp).getData()).isEqualTo(data);
    org.assertj.core.api.Assertions.assertThatThrownBy(()->verifier.verifyAndParse(raw+" ",hex.toString(),timestamp)).isInstanceOf(com.uqpay.sdk.common.UqpayWebhookException.class);
   }
  }
 }
 @Test void restOptionalFieldsThroughApiClient() throws Exception {
  ObjectMapper mapper=new ObjectMapper();ObjectNode current=mapper.createObjectNode();
  okhttp3.OkHttpClient transport=new okhttp3.OkHttpClient.Builder().addInterceptor(chain->new okhttp3.Response.Builder().request(chain.request()).protocol(okhttp3.Protocol.HTTP_1_1).code(200).message("OK").body(okhttp3.ResponseBody.create(mapper.writeValueAsString(current),okhttp3.MediaType.parse("application/json"))).build()).build();
  com.uqpay.sdk.payment.PaymentClient payment=new com.uqpay.sdk.payment.PaymentClient(new com.uqpay.sdk.common.ApiClient(com.uqpay.sdk.config.Configuration.builder().clientId("client").apiKey("offline").baseUrlOverride("https://example.test").httpClient(transport).build(),()->"offline-token"));
  for(String value:new String[]{"","2026-09-17T00:00:00Z"}) {
   current.removeAll();current.put("complete_time",value).put("advice_code",value.isEmpty()?"":"01");current.putObject("authentication_data").put("cvv_result",value.isEmpty()?"":"M");
   com.uqpay.sdk.payment.model.PaymentAttempt attempt=payment.getPaymentAttempts().get("pa-1");
   assertThat(attempt.getCompleteTime()).isEqualTo(value);assertThat(attempt.getAdviceCode()).isEqualTo(current.get("advice_code").asText());
   assertThat(mapper.valueToTree(attempt).get("authentication_data").get("cvv_result")).isEqualTo(current.get("authentication_data").get("cvv_result"));
   current.removeAll();current.put("completed_time",value);assertThat(payment.getPayouts().get("po-1").getCompletedTime()).isEqualTo(value);
  }
  for(String raw:new String[]{"{}","{\"metadata\":null}","{\"metadata\":{}}","{\"metadata\":{\"ref\":\"0001\"}}"}) {
   current.removeAll();current.setAll((ObjectNode)mapper.readTree(raw));
   com.uqpay.sdk.payment.model.Refund refund=payment.getRefunds().get("re-1");
   Object actual=refund.getMetadataValue();
   if(!current.hasNonNull("metadata"))assertThat(refund.getMetadata()).isEmpty();
   com.uqpay.sdk.payment.model.PaymentIntent intent=payment.getPaymentIntents().get("pi-1");
   assertThat(intent.getMetadataValue()).isEqualTo(actual);
   if(!current.hasNonNull("metadata"))assertThat(actual).isNull();else assertThat(mapper.<JsonNode>valueToTree(actual)).isEqualTo(current.get("metadata"));
  }
 }

 @Test void typedMetadataNullAndEmptyObjects() throws Exception {
  ObjectMapper mapper=new ObjectMapper();
  for(Class<?> type:new Class<?>[]{PaymentIntentData.class,RefundData.class}) {
   for(String raw:new String[]{"{}","{\"metadata\":null}","{\"metadata\":{}}","{\"metadata\":{\"ref\":\"0001\"}}"}) {
    JsonNode input=mapper.readTree(raw);JsonNode output=mapper.valueToTree(mapper.treeToValue(input,type));
    if(input.hasNonNull("metadata"))assertThat(output.get("metadata")).isEqualTo(input.get("metadata"));
    else assertThat(output.hasNonNull("metadata")).isFalse();
   }
  }
 }

 @Test void acquiringEventCanonicalFieldsDoNotDisappear() throws Exception {
  ObjectMapper mapper=new ObjectMapper();
  ObjectNode payout=mapper.createObjectNode();payout.put("account_id","acct-1").put("account_name","Example").put("payout_currency","USD").put("payout_reference","reference");payout.put("payout_amount",new java.math.BigDecimal("12345678901234567890.12"));
  JsonNode output=mapper.valueToTree(mapper.treeToValue(payout,PayoutAcquiringData.class));
  payout.fields().forEachRemaining(entry->assertThat(output.get(entry.getKey())).isEqualTo(entry.getValue()));
  assertThat(((PayoutAcquiringData)mapper.treeToValue(payout,PayoutAcquiringData.class)).getPayoutAmount()).isEqualByComparingTo("12345678901234567890.12");
  String rawEvent="{\"event_type\":\"acquiring.payout.succeeded\",\"data\":"+mapper.writeValueAsString(payout)+"}";
  assertThat(com.uqpay.sdk.webhook.Event.fromJson(rawEvent).parseData(PayoutAcquiringData.class).getPayoutAmount()).isEqualByComparingTo("12345678901234567890.12");
  ObjectNode alert=mapper.createObjectNode();
  for(String field:new String[]{"merchant_order_id","order_amount","order_currency","order_status","alert_amount","alert_currency","alert_status","alert_type","alert_create_time","alert_update_time","appeal_status","response_status","transaction_time"})alert.put(field,"value-"+field);
  JsonNode result=mapper.valueToTree(mapper.treeToValue(alert,ChargebackAlertData.class));
  alert.fields().forEachRemaining(entry->assertThat(result.get(entry.getKey())).isEqualTo(entry.getValue()));
 }
}
