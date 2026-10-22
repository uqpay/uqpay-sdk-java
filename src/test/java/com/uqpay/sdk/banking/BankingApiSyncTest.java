package com.uqpay.sdk.banking;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uqpay.sdk.banking.model.*;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.RequestOptions;
import com.uqpay.sdk.config.Configuration;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class BankingApiSyncTest {
 @Test
 void validationAndMethodQueriesPreserveRulesAndHeaders() throws Exception {
  try (MockWebServer server = new MockWebServer()) {
   server.start();
   Configuration config = Configuration.builder().clientId("test-client").apiKey("unused").baseUrlOverride(server.url("/").toString().replaceAll("/$", "")).build();
   ApiClient api = new ApiClient(config, () -> "test-token");
   RequestOptions options = RequestOptions.builder().onBehalfOf("sub-account").build();
   server.enqueue(new MockResponse().setHeader("Content-Type", "application/json").setBody("{\"channel_code\":\"MASTERCARD\",\"sufficient_funds\":false,\"beneficiary_valid\":true,\"fee_amount\":\"5\",\"total_amount\":\"6\"}"));
   ValidatePayoutRequest params = new ValidatePayoutRequest();
   params.setBeneficiaryId("saved + id"); params.setCurrency("USD"); params.setAmount("1.00"); params.setFeePaidBy("OURS");
   ValidatePayoutResponse result = new PayoutsService(api).validate(params, options);
   RecordedRequest request = server.takeRequest();
   assertThat(request.getMethod()).isEqualTo("GET");
   assertThat(request.getRequestUrl().encodedPath()).isEqualTo("/v1/payouts/validation");
   assertThat(request.getRequestUrl().queryParameter("beneficiary_id")).isEqualTo("saved + id");
   assertThat(request.getRequestUrl().queryParameter("currency")).isEqualTo("USD");
   assertThat(request.getRequestUrl().queryParameter("amount")).isEqualTo("1.00");
   assertThat(request.getRequestUrl().queryParameter("fee_paid_by")).isEqualTo("OURS");
   assertThat(request.getHeader("x-on-behalf-of")).isEqualTo("sub-account");
   assertThat(result.getSufficientFunds()).isFalse();
   assertThat(result.getChannelCode()).isEqualTo("MASTERCARD");
   assertThat(result.getBeneficiaryValid()).isTrue();
   assertThat(result.getFeeAmount()).isEqualTo("5");
   assertThat(result.getTotalAmount()).isEqualTo("6");
   server.enqueue(new MockResponse().setHeader("Content-Type", "application/json").setBody("{\"data\":[{\"payment_method\":\"CARD\",\"validation_field\":[\"account_number\",{\"value\":\"card_expiry\",\"children\":[{\"type\":\"MONTH\"}]}]}]}"));
   ListPaymentMethodsResponse methods = new BeneficiariesService(api).listPaymentMethods("USD", "US", "INDIVIDUAL", options);
   request = server.takeRequest();
   assertThat(request.getRequestUrl().queryParameter("entity_type")).isEqualTo("INDIVIDUAL");
   assertThat(methods.getData().get(0).getValidationField().get(0).asText()).isEqualTo("account_number");
   assertThat(methods.getData().get(0).getValidationField().get(1).get("children").get(0).get("type").asText()).isEqualTo("MONTH");
   server.enqueue(new MockResponse().setHeader("Content-Type", "application/json").setBody("{\"data\":[]}"));
   ListBeneficiariesRequest list = new ListBeneficiariesRequest(); list.setPageSize(10); list.setPageNumber(1); list.setPaymentMethod("CARD");
   new BeneficiariesService(api).list(list, options);
   assertThat(server.takeRequest().getRequestUrl().queryParameter("payment_method")).isEqualTo("CARD");
  }
 }

 @Test
 void cardExpiryRetainedInRestAndNestedWebhookModels() throws Exception {
  ObjectMapper mapper = new ObjectMapper();
  BankDetails details = mapper.readValue("{\"card_expiry\":\"2099-12\",\"clearing_system\":\"MASTERCARD SEND\"}", BankDetails.class);
  assertThat(details.getCardExpiry()).isEqualTo("2099-12");
  assertThat(mapper.valueToTree(details).get("card_expiry").asText()).isEqualTo("2099-12");
  com.uqpay.sdk.webhook.model.BeneficiaryBankDetails webhook = mapper.readValue("{\"card_expiry\":\"2099-12\"}", com.uqpay.sdk.webhook.model.BeneficiaryBankDetails.class);
  assertThat(webhook.getCardExpiry()).isEqualTo("2099-12");
  CreatePayoutRequest payout = new CreatePayoutRequest(); payout.setCurrency("USD"); payout.setAmount("1"); payout.setPurposeCode("BILL_PAYMENT");
  JsonNode body = mapper.valueToTree(payout);
  assertThat(body.has("payout_reference")).isFalse();
  assertThat(body.has("fee_paid_by")).isFalse();
  assertThat(body.has("payout_date")).isFalse();
 }
}
