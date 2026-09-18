package com.uqpay.sdk.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uqpay.sdk.common.UqpayWebhookException;
import com.uqpay.sdk.webhook.model.*;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.*;

/** Minimal field probes, not full event-schema or delivery verification. */
class RemainingContractTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void signedFieldsAndTypedValues() throws Exception {
        JsonNode cases = mapper.readTree(getClass().getResourceAsStream("/remaining-webhooks.json"));
        for (JsonNode fixture : cases) {
            String kind = fixture.get("kind").asText();
            JsonNode data = fixture.get("data");
            String body = mapper.createObjectNode().put("event_type", kind).set("data", data).toString();
            String timestamp = String.valueOf(System.currentTimeMillis());
            String signature = sign(body, timestamp);
            WebhookVerifier verifier = new WebhookVerifier("offline-secret");
            Event event = verifier.verifyAndParse(body, signature, timestamp);
            assertThat(mapper.<JsonNode>valueToTree(event.getData())).isEqualTo(data);
            assertThatThrownBy(() -> verifier.verifyAndParse(body + " ", signature, timestamp))
                    .isInstanceOf(UqpayWebhookException.class);

            Class<?> type = modelFor(kind);
            if (type == null) {
                continue; // RFI ID has raw-data evidence only; no typed RFI ID model.
            }
            Object parsed = event.parseData(type);
            Iterator<String> keys = data.fieldNames();
            while (keys.hasNext()) {
                String key = keys.next();
                Field field = findWireField(type, key);
                assertThat(field).as(kind + "." + key).isNotNull();
                field.setAccessible(true);
                JsonNode actual = mapper.valueToTree(field.get(parsed));
                assertThat(actual).as(kind + "." + key).isEqualTo(data.get(key));
            }
        }
    }

    @Test
    void routesFrozenIssuingCardholderEvents() throws Exception {
        for (String eventType : new String[]{"cardholder.kyc.status_changed", "cardholder.updated"}) {
            for (String status : new String[]{"SUCCESS", "FAILED", "INCOMPLETE"}) {
                String body = "{\"event_name\":\"ISSUING\",\"event_type\":\"" + eventType
                        + "\",\"data\":{\"cardholder_status\":\"" + status
                        + "\",\"reason\":\"More evidence required\"}}";
                String timestamp = String.valueOf(System.currentTimeMillis());
                Event event = new WebhookVerifier("offline-secret")
                        .verifyAndParse(body, sign(body, timestamp), timestamp);
                assertThat(event.isCardholderKycEvent()).isEqualTo(eventType.equals("cardholder.kyc.status_changed"));
                assertThat(event.isCardholderUpdatedEvent()).isEqualTo(eventType.equals("cardholder.updated"));
                assertThat(event.parseCardholderData().getCardholderStatus()).isEqualTo(status);
                assertThat(event.parseCardholderData().getReason()).isEqualTo("More evidence required");
            }
        }
        Event unrelated = Event.fromJson("{\"event_name\":\"ISSUING\",\"event_type\":\"card.create.succeeded\",\"data\":{}}");
        assertThatThrownBy(unrelated::parseCardholderData).isInstanceOf(IllegalStateException.class);
    }

    private static Class<?> modelFor(String kind) {
        switch (kind) {
            case "representative": return Representative.class;
            case "cardholder": return CardholderData.class;
            case "fee": return NetworkProtectionFeeData.class;
            case "transfer": return TransferEventData.class;
            case "transaction": return CardTransactionData.class;
            case "deposit": return DepositEventData.class;
            case "intent": return PaymentIntentData.class;
            default: return null;
        }
    }

    private static Field findWireField(Class<?> type, String key) {
        for (Field field : type.getDeclaredFields()) {
            JsonProperty property = field.getAnnotation(JsonProperty.class);
            if (property != null && property.value().equals(key)) {
                return field;
            }
        }
        return null;
    }

    private static String sign(String body, String timestamp) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(new SecretKeySpec("offline-secret".getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
        StringBuilder signature = new StringBuilder();
        for (byte value : mac.doFinal((body + timestamp).getBytes(StandardCharsets.UTF_8))) {
            signature.append(String.format("%02x", value));
        }
        return signature.toString();
    }
}
