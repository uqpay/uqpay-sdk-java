package com.uqpay.sdk;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uqpay.sdk.issuing.model.RetrieveCardResponse;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
class ResponseContractTest {
 @Test void cardLimitRetainsDecimalPrecision() throws Exception {
  ObjectMapper mapper = new ObjectMapper();
  RetrieveCardResponse card = mapper.readValue("{\"card_limit\":\"12345678901234567890.12345678\",\"metadata\":\"{\\\"ref\\\":\\\"0001\\\"}\",\"risk_controls\":null}",RetrieveCardResponse.class);
  assertThat(mapper.valueToTree(card).get("card_limit").asText()).isEqualTo("12345678901234567890.12345678");
 }

 @Test void simulatorDecimalStringsRemainExact() throws Exception {
  ObjectMapper mapper = new ObjectMapper();
  com.uqpay.sdk.simulator.model.SimulateAuthorizationResponse response = mapper.readValue("{\"transaction_amount\":\"12345678901234567890.12345678\",\"billing_amount\":\"0.00\",\"card_available_balance\":\"-0.01\"}", com.uqpay.sdk.simulator.model.SimulateAuthorizationResponse.class);
  assertThat(mapper.valueToTree(response).get("transaction_amount").asText()).isEqualTo("12345678901234567890.12345678");
  assertThat(mapper.valueToTree(response).get("billing_amount").asText()).isEqualTo("0.00");
 }

 @Test void accountSummariesAndMoneyResponses() throws Exception {
  ObjectMapper mapper=new ObjectMapper();
  com.uqpay.sdk.connect.model.Account account=mapper.readValue("{\"entity_type\":\"COMPANY\",\"business_details\":{\"legal_entity_name\":\"Example\",\"registration_number\":\"reg-1\"}}",com.uqpay.sdk.connect.model.Account.class);
  assertThat(account.getBusinessDetails().getLegalEntityName()).isEqualTo("Example");
  assertThat(account.getBusinessDetails().getLegalEntityNameEnglish()).isNull();
  assertThat(mapper.readValue("{\"entity_type\":\"INDIVIDUAL\",\"person_details\":{\"first_name\":\"Test\"}}",com.uqpay.sdk.connect.model.Account.class).getPersonDetails().getFirstName()).isEqualTo("Test");
  String money="12345678901234567890.12345678";
  assertThat(mapper.readValue("{\"transaction_amount\":\""+money+"\",\"original_transaction_id\":\"\",\"merchant_data\":{}}",com.uqpay.sdk.issuing.model.Transaction.class).getTransactionAmount()).isEqualTo(money);
  assertThat(mapper.readValue("{\"amount\":\""+money+"\",\"creator_id\":\"\",\"transfer_status\":\"COMPLETED\"}",com.uqpay.sdk.issuing.model.IssuingTransfer.class).getAmount()).isEqualTo(money);
  assertThat(mapper.readValue("{\"available_balance\":\"-"+money+"\"}",com.uqpay.sdk.banking.model.Balance.class).getAvailableBalance()).isEqualTo("-"+money);
  com.uqpay.sdk.banking.model.PayoutPayer payer=mapper.readValue("{\"payer_id\":\"0\",\"identification_type\":\"\"}",com.uqpay.sdk.banking.model.PayoutPayer.class);
  assertThat(payer.getPayerId()).isEqualTo("0");assertThat(payer.getIdentificationType()).isEmpty();
  assertThat(mapper.readValue("{\"transaction_amount\":\""+money+"\",\"balance_amount\":\"0.00\"}",com.uqpay.sdk.webhook.model.NetworkProtectionFeeData.class).getTransactionAmount()).isEqualTo(money);
  assertThat(mapper.readValue("{\"amount\":\""+money+"\",\"status\":\"succeeded\"}",com.uqpay.sdk.webhook.model.IssuingTransferStatusChangedData.class).getAmount()).isEqualTo(money);
 }
 @Test void legacyNumericAccessAndNullableMetadataRemainCompatible() throws Exception {
  ObjectMapper mapper=new ObjectMapper();
  RetrieveCardResponse card=new RetrieveCardResponse();card.setCardLimit(12.5);assertThat(card.getCardLimit()).isEqualTo(12.5);
  assertThat(card.getCardLimitValue()).isEqualTo("12.5");
  for (String metadata : new String[]{"null", "\"\"", "{}", "\"{\\\"ref\\\":\\\"0001\\\"}\""}) {
   card=mapper.readValue("{\"metadata\":"+metadata+",\"risk_controls\":null}",RetrieveCardResponse.class);
   assertThat(card.getRiskControls()).isNull();
  }
 }

 @Test void nullableWebhookDocumentsAndPaymentResponses() throws Exception {
  ObjectMapper mapper=new ObjectMapper();
  com.uqpay.sdk.webhook.model.Representative representative=mapper.readValue("{\"other_documents\":null}",com.uqpay.sdk.webhook.model.Representative.class);
  assertThat(representative.getOtherDocuments()).isNull();
  representative=mapper.readValue("{\"other_documents\":[{\"type\":\"PROOF_OF_ADDRESS\",\"front\":\"https://example.test/proof\"}]}",com.uqpay.sdk.webhook.model.Representative.class);
  assertThat(representative.getOtherDocuments().get(0).get("type")).isEqualTo("PROOF_OF_ADDRESS");
  com.uqpay.sdk.payment.model.PaymentIntent intent=mapper.readValue("{\"metadata\":null,\"next_action\":null,\"latest_payment_attempt\":null,\"complete_time\":\"\",\"cancel_time\":\"\"}",com.uqpay.sdk.payment.model.PaymentIntent.class);
  assertThat(intent.getCompleteTime()).isEmpty();assertThat(intent.getCancelTime()).isEmpty();
 }
}
