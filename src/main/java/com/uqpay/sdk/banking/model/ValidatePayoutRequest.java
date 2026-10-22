package com.uqpay.sdk.banking.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/** Payout preflight parameters; this does not create an order. */
public class ValidatePayoutRequest {
 private String beneficiaryId;
 private String currency;
 private String amount;
 private String feePaidBy;
 public String getBeneficiaryId() { return beneficiaryId; }
 public void setBeneficiaryId(String value) { beneficiaryId = value; }
 public String getCurrency() { return currency; }
 public void setCurrency(String value) { currency = value; }
 public String getAmount() { return amount; }
 public void setAmount(String value) { amount = value; }
 public String getFeePaidBy() { return feePaidBy; }
 public void setFeePaidBy(String value) { feePaidBy = value; }
 public String toQueryString() {
  return "?beneficiary_id=" + encode(beneficiaryId) + "&currency=" + encode(currency)
      + "&amount=" + encode(amount) + "&fee_paid_by=" + encode(feePaidBy);
 }
 private static String encode(String value) {
  try { return URLEncoder.encode(java.util.Objects.requireNonNull(value, "required payout validation parameter"), StandardCharsets.UTF_8.name()); }
  catch (java.io.UnsupportedEncodingException e) { throw new IllegalStateException(e); }
 }
}
