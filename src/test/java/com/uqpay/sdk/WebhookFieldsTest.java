package com.uqpay.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.uqpay.sdk.webhook.model.PaymentMethodData;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class WebhookFieldsTest {
 @Test void allPaymentMethodDetailsSurviveTypedParsing() throws Exception {
  ObjectMapper mapper = new ObjectMapper();
  for (String method : new String[]{"card", "card_present", "wechatpay", "alipay", "alipaycn", "alipayhk", "paynow", "grabpay", "applepay", "googlepay", "unionpay", "crypto", "tng", "truemoney", "gcash", "dana", "kakaopay", "tosspay", "naverpay", "mpay", "kplus", "boost", "rabbitlinepay", "kaspi", "hipay", "shopeepay"}) {
   String detail = method.startsWith("card") ? "{\"card_name\":\"Test\",\"card_number\":\"411111******1111\",\"network\":\"VISA\",\"issuer_country_code\":\"SG\"}" : "{\"flow\":null,\"os_type\":null,\"static_qrcode\":\"qr-data\",\"static_qrcode_extension\":\"png\",\"static_qrcode_number_plate\":\"plate\"}";
   PaymentMethodData parsed = mapper.readValue("{\"type\":\"" + method + "\",\"" + method + "\":" + detail + "}", PaymentMethodData.class);
   JsonNode result = mapper.valueToTree(parsed);
   assertThat(result.has(method)).as(method).isTrue();
   JsonNode expected = mapper.readTree(detail);
   expected.fields().forEachRemaining(entry -> { if (!entry.getValue().isNull()) assertThat(result.get(method).get(entry.getKey())).as(method + "." + entry.getKey()).isEqualTo(entry.getValue()); });
  }
 }

 @Test void nullableAndUnknownValuesStayReadable() throws Exception {
  ObjectMapper mapper = new ObjectMapper();
  com.uqpay.sdk.webhook.Event event = mapper.readValue("{\"event_type\":\"acquiring.payment_intent.succeeded\",\"data\":{\"amount\":\"12345678901234567890.12345678\",\"complete_time\":null,\"cancel_time\":\"\",\"payment_method\":null,\"next_action\":{\"redirect_to_url\":{\"return_url\":\"\"}}}}", com.uqpay.sdk.webhook.Event.class);
  com.uqpay.sdk.webhook.model.PaymentIntentData intent = event.parsePaymentIntentData();
  assertThat(intent.getAmount()).isEqualTo("12345678901234567890.12345678");
  assertThat(intent.getCompleteTime()).isNull();
  assertThat(intent.getCancelTime()).isEmpty();
  assertThat(intent.getPaymentMethod()).isNull();
  assertThat(intent.getNextAction()).containsKey("redirect_to_url");
  com.uqpay.sdk.webhook.model.CardTransactionData tx = mapper.readValue("{\"wallet_type\":\"FUTURE_WALLET\",\"transaction_amount\":\"0.00000001\"}", com.uqpay.sdk.webhook.model.CardTransactionData.class);
  assertThat(tx.getWalletType()).isEqualTo("FUTURE_WALLET");
  assertThat(tx.getTransactionAmount()).isEqualTo("0.00000001");
  assertThat(mapper.readValue("{\"reason\":\"more evidence required\"}", com.uqpay.sdk.webhook.model.CardholderData.class).getReason()).isEqualTo("more evidence required");
 }
 @Test void ibanOnlyAndDepositClassifications() throws Exception {
  ObjectMapper mapper = new ObjectMapper();
  com.uqpay.sdk.banking.model.BeneficiaryCheckRequest request = new com.uqpay.sdk.banking.model.BeneficiaryCheckRequest();
  request.setIban("DE89370400440532013000");request.setBankCountryCode("DE");request.setEntityType("COMPANY");request.setPaymentMethod("LOCAL");request.setCurrency("EUR");
  JsonNode wire = mapper.valueToTree(request);
  assertThat(wire.has("account_number")).isFalse();
  assertThat(wire.get("iban").asText()).isEqualTo("DE89370400440532013000");
  assertThat(wire.get("bank_country_code").asText()).isEqualTo("DE");
  com.uqpay.sdk.banking.model.Deposit deposit = mapper.readValue("{\"amount\":\"-12345678901234567890.12345678\",\"complete_time\":null,\"deposit_method\":\"UQPAY_TRANSFER\",\"sender\":{\"sender_type\":\"COMPANY\",\"name_type\":\"NAMED\"}}",com.uqpay.sdk.banking.model.Deposit.class);
  assertThat(deposit.getAmount()).isEqualTo("-12345678901234567890.12345678");
  assertThat(deposit.getDepositMethod()).isEqualTo("UQPAY_TRANSFER");
  assertThat(deposit.getSender().getSenderType()).isEqualTo("COMPANY");
  assertThat(deposit.getSender().getNameType()).isEqualTo("NAMED");
 }
}
