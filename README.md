# UQPAY Java SDK

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Official Java SDK for the [UQPAY API](https://developer.uqpay.com/api/#/) — a comprehensive payment and card issuing platform.

## Requirements

- Java 11 or higher
- Maven 3.6+ or Gradle 7+

## Installation

### Maven

```xml
<dependency>
    <groupId>com.uqpay.sdk</groupId>
    <artifactId>uqpay-sdk-java</artifactId>
    <version>1.1.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'com.uqpay.sdk:uqpay-sdk-java:1.1.0'
```

## Quick Start

```java
import com.uqpay.sdk.UqpayClient;

// Sandbox (for testing)
UqpayClient client = UqpayClient.sandbox("your-client-id", "your-api-key");

// Production
UqpayClient client = UqpayClient.production("your-client-id", "your-api-key");
```

## Authentication

The SDK handles OAuth2 authentication automatically. It fetches an access token using your `clientId` and `apiKey`, caches it, and refreshes it before expiry. You do not need to manage tokens manually.

## Resources

### Banking

```java
import com.uqpay.sdk.banking.model.*;

// Balances
ListBalancesResponse balances = client.banking().getBalances().list(
    new ListBalancesRequest().setPageSize(20).setPageNumber(1)
);
Balance balance = client.banking().getBalances().get("SGD");

// Balance transactions
ListBalanceTransactionsRequest txReq = new ListBalanceTransactionsRequest();
txReq.setPageSize(20);
txReq.setPageNumber(1);
ListBalanceTransactionsResponse txns = client.banking().getBalances().listTransactions(txReq);

// Transfers
CreateTransferRequest transferReq = new CreateTransferRequest();
transferReq.setSourceAccountId("acc-123");
transferReq.setDestinationAccountId("acc-456");
transferReq.setCurrency("SGD");
transferReq.setAmount("100.00");
CreateTransferResponse transfer = client.banking().getTransfers().create(transferReq);

ListTransfersResponse transfers = client.banking().getTransfers().list(
    new ListTransfersRequest().setPageSize(20).setPageNumber(1)
);
Transfer retrieved = client.banking().getTransfers().get("transfer-id");

// Deposits
ListDepositsResponse deposits = client.banking().getDeposits().list(
    new ListDepositsRequest().setPageSize(20).setPageNumber(1)
);
Deposit deposit = client.banking().getDeposits().get("deposit-id");

// Beneficiaries
CreateBeneficiaryResponse beneficiary = client.banking().getBeneficiaries().create(createBeneficiaryReq);
ListBeneficiariesResponse beneficiaries = client.banking().getBeneficiaries().list(listBeneficiaryReq);
Beneficiary ben = client.banking().getBeneficiaries().get("ben-id");

// Payouts
CreatePayoutRequest payoutReq = new CreatePayoutRequest();
payoutReq.setBeneficiaryId("ben-123");
payoutReq.setCurrency("SGD");
payoutReq.setAmount("50.00");
payoutReq.setPurposeCode("PERSONAL");
CreatePayoutResponse payout = client.banking().getPayouts().create(payoutReq);

// Virtual Accounts
CreateVirtualAccountResponse va = client.banking().getVirtualAccounts().create(createVaReq);
ListVirtualAccountsResponse vas = client.banking().getVirtualAccounts().list(listVaReq);

// Conversions
CreateConversionQuoteResponse quote = client.banking().getConversions().createQuote(quoteReq);
CreateConversionResponse conversion = client.banking().getConversions().create(createConversionReq);

// Exchange Rates
ListExchangeRatesResponse rates = client.banking().getExchangeRates().list(listRatesReq);
```

### Issuing

#### Cardholders

```java
import com.uqpay.sdk.issuing.model.*;

// Create a cardholder — simplified KYC (basic fields only)
CreateCardholderRequest request = new CreateCardholderRequest();
request.setEmail("user@example.com");
request.setFirstName("Jane");
request.setLastName("Smith");
request.setCountryCode("SG");
request.setPhoneNumber("+6512345678");
request.setDateOfBirth("1990-01-01");

CreateCardholderResponse cardholder = client.issuing().getCardholders().create(request);
System.out.printf("Cardholder: %s (status: %s)%n",
        cardholder.getCardholderId(), cardholder.getCardholderStatus());

// Create a cardholder — standard KYC (gender, nationality, identity, address)
CreateCardholderRequest standardReq = new CreateCardholderRequest();
standardReq.setEmail("user@example.com");
standardReq.setFirstName("Bob");
standardReq.setLastName("Lee");
standardReq.setCountryCode("SG");
standardReq.setPhoneNumber("+6598765432");
standardReq.setDateOfBirth("1988-11-03");
standardReq.setGender("MALE");
standardReq.setNationality("SG");

Identity identity = new Identity();
identity.setType("PASSPORT");
identity.setNumber("E1234567");
standardReq.setIdentity(identity);

ResidentialAddress addr = new ResidentialAddress();
addr.setCountry("SG");
addr.setState("Singapore");
addr.setCity("Singapore");
addr.setLine1("1 Raffles Place");
addr.setLineEn("1 Raffles Place");
addr.setPostalCode("048616");
standardReq.setResidentialAddress(addr);

CreateCardholderResponse stdCardholder = client.issuing().getCardholders().create(standardReq);

// Create a cardholder — enhanced KYC (SUMSUB_REDIRECT — returns idv_verification_url)
KycVerification kyc = new KycVerification();
kyc.setMethod("SUMSUB_REDIRECT");
request.setKycVerification(kyc);

CreateCardholderResponse enhanced = client.issuing().getCardholders().create(request);
System.out.println("IDV URL: " + enhanced.getIdvVerificationUrl());

// Get / list / update cardholders
Cardholder ch = client.issuing().getCardholders().get("ch-id");
ListCardholdersResponse list = client.issuing().getCardholders().list(
    new ListCardholdersRequest().setPageSize(20).setPageNumber(1).setCardholderStatus("SUCCESS")
);

UpdateCardholderRequest updateReq = new UpdateCardholderRequest();
updateReq.setResidentialAddress(addr);
client.issuing().getCardholders().update("ch-id", updateReq);
```

#### Cards

```java
// Create a card
CreateCardRequest cardReq = new CreateCardRequest();
cardReq.setCardCurrency("SGD");
cardReq.setCardholderId(cardholder.getCardholderId());
cardReq.setCardProductId("prod-123");

CardCreationResponse card = client.issuing().getCards().create(cardReq);
System.out.printf("Card: %s (status: %s)%n", card.getCardId(), card.getCardStatus());

// Create a ONE_TIME card with auto-cancel trigger
CreateCardRequest oneTimeReq = new CreateCardRequest();
oneTimeReq.setCardCurrency("SGD");
oneTimeReq.setCardholderId(cardholder.getCardholderId());
oneTimeReq.setCardProductId("prod-123");
oneTimeReq.setUsageType("ONE_TIME");
oneTimeReq.setAutoCancelTrigger("ON_AUTH");

// Create a card with supplemental KYC fields (cardholder_required_fields)
CardholderRequiredFields fields = new CardholderRequiredFields();
fields.setGender("FEMALE");
fields.setNationality("SG");
fields.setIdentity(identity);
fields.setResidentialAddress(addr);
cardReq.setCardholderRequiredFields(fields);

// Get, list, update status, recharge, withdraw
RetrieveCardResponse retrieved = client.issuing().getCards().get("card-id");
ListCardsResponse cards = client.issuing().getCards().list(
    new ListCardsRequest().setPageSize(20).setPageNumber(1)
);

// Freeze / unfreeze
UpdateCardStatusRequest freeze = new UpdateCardStatusRequest();
freeze.setCardStatus("FROZEN");
client.issuing().getCards().updateStatus("card-id", freeze);

// Recharge and withdraw
CardOrderRequest rechargeReq = new CardOrderRequest();
rechargeReq.setAmount(100.00);
CardOrder recharge = client.issuing().getCards().recharge("card-id", rechargeReq);

CardOrderRequest withdrawReq = new CardOrderRequest();
withdrawReq.setAmount(50.00);
CardOrder withdraw = client.issuing().getCards().withdraw("card-id", withdrawReq);
```

#### Transactions, Products, Balances, Transfers

```java
// Transactions
ListTransactionsResponse txns = client.issuing().getTransactions().list(
    new ListTransactionsRequest().setPageSize(20).setPageNumber(1).setCardId("card-id")
);
Transaction txn = client.issuing().getTransactions().get("txn-id");

// Products
ListProductsResponse products = client.issuing().getProducts().list(
    new ListProductsRequest().setPageSize(20).setPageNumber(1)
);

// Issuing balances
ListIssuingBalancesResponse issuingBalances = client.issuing().getBalances().list(listReq);
IssuingBalance issuingBalance = client.issuing().getBalances().get("SGD");

// Issuing transfers
CreateIssuingTransferResponse issuingTransfer = client.issuing().getTransfers().create(createIssuingTransferReq);
```

### Connect

```java
import com.uqpay.sdk.connect.model.*;

// List and get accounts
ListAccountsResponse accounts = client.connect().getAccounts().list(
    new ListAccountsRequest().setPageSize(20).setPageNumber(1)
);
Account account = client.connect().getAccounts().get("acc-id");
Account accountByBizCode = client.connect().getAccounts().get("acc-id", "BUSINESS_CODE");
```

### Payment

```java
import com.uqpay.sdk.payment.model.*;

// Payment Intents
CreatePaymentIntentRequest intentReq = new CreatePaymentIntentRequest();
intentReq.setAmount("100.00");
intentReq.setCurrency("SGD");
CreatePaymentIntentResponse intent = client.payment().getPaymentIntents().create(intentReq);

client.payment().getPaymentIntents().confirm(intent.getPaymentIntentId(), confirmReq);
client.payment().getPaymentIntents().capture(intent.getPaymentIntentId(), captureReq);
client.payment().getPaymentIntents().cancel(intent.getPaymentIntentId());

ListPaymentIntentsResponse intents = client.payment().getPaymentIntents().list(listIntentsReq);

// Refunds
CreateRefundRequest refundReq = new CreateRefundRequest();
refundReq.setPaymentIntentId(intent.getPaymentIntentId());
refundReq.setAmount("50.00");
CreateRefundResponse refund = client.payment().getRefunds().create(refundReq);

// Bank Accounts
CreateBankAccountResponse bankAccount = client.payment().getBankAccounts().create(bankAccountReq);

// Payouts
CreatePayoutResponse payout = client.payment().getPayouts().create(payoutReq);

// Balances and Attempts
ListPaymentBalancesResponse paymentBalances = client.payment().getBalances().list(listBalReq);
ListPaymentAttemptsResponse attempts = client.payment().getPaymentAttempts().list(listAttemptsReq);
```

### Supporting (File Upload / Download)

```java
import com.uqpay.sdk.supporting.model.*;
import java.io.File;

// Upload a file
File doc = new File("kyc-document.pdf");
UploadFileResponse uploaded = client.supporting().getFiles().upload(doc, "KYC document");
System.out.println("File ID: " + uploaded.getFileId());

// Get download links
DownloadLinksRequest dlReq = new DownloadLinksRequest(List.of(uploaded.getFileId()));
DownloadLinksResponse links = client.supporting().getFiles().getDownloadLinks(dlReq);
links.getFiles().forEach(f -> System.out.println("URL: " + f.getUrl()));
```

### Simulator (sandbox only)

The simulator is only available in the `sandbox` environment. Calling simulator methods in production throws a `UqpayException`.

```java
import com.uqpay.sdk.simulator.model.*;

// Simulate a card authorization
SimulateAuthorizationRequest authReq = new SimulateAuthorizationRequest();
authReq.setCardId("card-123");
authReq.setTransactionAmount(25.00);
authReq.setTransactionCurrency("SGD");
authReq.setMerchantName("Coffee Shop");
SimulateAuthorizationResponse auth = client.simulator().getIssuing().authorize(authReq);

// Simulate a reversal
SimulateReversalRequest revReq = new SimulateReversalRequest();
revReq.setTransactionId(auth.getTransactionId());
client.simulator().getIssuing().reverse(revReq);

// Simulate a deposit
SimulateDepositRequest depositReq = new SimulateDepositRequest();
depositReq.setCurrency("SGD");
depositReq.setAmount(500.00);
SimulateDepositResponse deposit = client.simulator().getDeposits().simulate(depositReq);
```

## Pagination

All list methods return a paginated response with `getData()`, `getTotalPages()`, and `getTotalItems()`.

```java
ListCardsRequest req = new ListCardsRequest();
req.setPageNumber(1);
req.setPageSize(50);
ListCardsResponse page = client.issuing().getCards().list(req);

System.out.println(page.getData());       // List<Card>
System.out.println(page.getTotalPages()); // total number of pages
System.out.println(page.getTotalItems()); // total number of items
```

## Webhooks

Verify incoming webhook signatures from UQPAY:

```java
import com.uqpay.sdk.webhook.WebhookVerifier;
import com.uqpay.sdk.webhook.Event;
import com.uqpay.sdk.common.UqpayWebhookException;

WebhookVerifier verifier = new WebhookVerifier("your-webhook-secret");

// In your HTTP handler:
try {
    Event event = verifier.verifyAndParse(
        requestBodyString,         // raw request body as String
        request.getHeader("x-signature"),
        request.getHeader("x-timestamp")
    );

    if (event.isCardTransactionEvent()) {
        // handle card transaction authorization, clearing, etc.
    } else if (Event.EVENT_NAME_CARDHOLDER_KYC.equals(event.getEventName())) {
        // handle KYC status update
    }
} catch (UqpayWebhookException e) {
    // signature invalid or timestamp expired
    response.setStatus(400);
}
```

The verifier checks the HMAC-SHA512 signature and rejects requests with a timestamp older than 300 seconds by default.

## Authorization Decision (PGP)

Handle real-time card transaction authorization decisions. UQPAY sends PGP-encrypted transactions to your endpoint; the SDK decrypts them, calls your handler, and returns an encrypted response.

```java
import com.uqpay.sdk.issuing.AuthDecisionService;
import com.uqpay.sdk.issuing.model.AuthDecisionConfig;
import com.uqpay.sdk.issuing.model.AuthDecisionRequest;
import com.uqpay.sdk.issuing.model.AuthDecisionResponse;

// Configure PGP keys (once at startup)
AuthDecisionConfig config = new AuthDecisionConfig(
    "./keys/my-private.asc",
    System.getenv("PGP_PASSPHRASE"),
    "./keys/uqpay-public.asc"
);

AuthDecisionService authDecision = client.issuing().getAuthDecision();
authDecision.configure(config);

// Handle a decision request
AuthDecisionHandler handler = (AuthDecisionRequest tx) -> {
    if (tx.getBillingAmount() > 10000) {
        return AuthDecisionResponse.decline(tx.getTransactionId(), "51"); // Insufficient Funds
    }
    return AuthDecisionResponse.approve(tx.getTransactionId());
};

// In your HTTP handler, pass the raw encrypted body string:
String encryptedResponse = authDecision.processRequest(requestBodyString, handler);
response.getWriter().write(encryptedResponse);
```

The SDK handles all PGP encryption/decryption automatically.

## Error Handling

The SDK uses a hierarchy of exceptions:

- `UqpayException` — base exception for all SDK errors
  - `UqpayApiException` — API returned an error response (includes HTTP status, error code, message)
  - `UqpayAuthException` — authentication failed (invalid credentials, token refresh failure)
  - `UqpayNetworkException` — network-level failure (timeout, connection refused)
  - `UqpayWebhookException` — webhook signature verification failed

```java
try {
    Balance balance = client.banking().getBalances().get("SGD");
} catch (UqpayApiException e) {
    System.err.printf("API error: status=%d, code=%s, message=%s%n",
            e.getStatusCode(), e.getCode(), e.getMessage());
} catch (UqpayAuthException e) {
    System.err.println("Authentication failed: " + e.getMessage());
} catch (UqpayNetworkException e) {
    System.err.println("Network error: " + e.getMessage());
} catch (UqpayException e) {
    System.err.println("SDK error: " + e.getMessage());
}
```

## Configuration

### Custom HTTP Client

```java
import okhttp3.OkHttpClient;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.config.Environment;

OkHttpClient httpClient = new OkHttpClient.Builder()
        .connectTimeout(Duration.ofSeconds(60))
        .readTimeout(Duration.ofSeconds(60))
        .build();

Configuration config = Configuration.builder()
        .clientId("your-client-id")
        .apiKey("your-api-key")
        .environment(Environment.PRODUCTION)
        .httpClient(httpClient)
        .build();

UqpayClient client = new UqpayClient(config);
```

### Environments

| Constant | Description |
|----------|-------------|
| `Environment.SANDBOX` | Sandbox environment for testing and development |
| `Environment.PRODUCTION` | Production environment for live transactions |

### Environment Variables

For local development, create a `.env` file in the project root:

```
UQPAY_CLIENT_ID=your-sandbox-client-id
UQPAY_API_KEY=your-sandbox-api-key
```

> The `.env` file is git-ignored and must never be committed. Obtain sandbox credentials from the [UQPAY Developer Portal](https://developer.uqpay.com).

## API Coverage

### Banking API

| Resource | Operations |
|----------|------------|
| **Balances** | Get, List, ListTransactions |
| **Transfers** | Create, List, Get |
| **Deposits** | List, Get |
| **Beneficiaries** | Create, List, Get, Update, Delete, ListPaymentMethods, Check |
| **Payouts** | Create, List, Get |
| **Virtual Accounts** | Create, List |
| **Conversions** | CreateQuote, Create, List, Get, ListConversionDates |
| **Exchange Rates** | List |

### Issuing API

| Resource | Operations |
|----------|------------|
| **Cardholders** | Create (Simplified/Standard/Enhanced KYC), Get, List, Update |
| **Cards** | Create, Get, GetSecure, List, Recharge, Withdraw, UpdateStatus |
| **Transactions** | Get, List |
| **Products** | List |
| **Balances** | Get, List, ListTransactions |
| **Transfers** | Create, List, Get |
| **Reports** | Create, Get |
| **Auth Decision** | PGP-based real-time authorization |

### Connect API

| Resource | Operations |
|----------|------------|
| **Accounts** | Get, List |

### Payment API

| Resource | Operations |
|----------|------------|
| **Payment Intents** | Create, Confirm, Capture, Cancel, List, Get |
| **Refunds** | Create, List, Get |
| **Bank Accounts** | Create, List, Get |
| **Payouts** | Create, List, Get |
| **Balances** | List |
| **Payment Attempts** | List, Get |
| **Settlements** | List |

### Supporting API

| Resource | Operations |
|----------|------------|
| **Files** | Upload, GetDownloadLinks |

### Simulator (sandbox only)

| Resource | Operations |
|----------|------------|
| **Issuing** | Authorize, Reverse |
| **Deposits** | Simulate |

## Testing

Integration tests run against the UQPAY sandbox API. Create a `.env` file with sandbox credentials, then:

```bash
# Run all tests
mvn test

# Skip integration tests (for CI without credentials)
mvn test -DSKIP_INTEGRATION_TESTS=true
```

## Project Structure

```
uqpay-sdk-java/
├── src/main/java/com/uqpay/sdk/v2/
│   ├── UqpayClient.java           # Main entry point
│   ├── auth/                      # OAuth2 token management
│   ├── banking/                   # Banking API
│   ├── common/                    # Shared HTTP client, exceptions
│   ├── config/                    # Configuration and environments
│   ├── connect/                   # Connect API
│   ├── issuing/                   # Issuing API + Auth Decision
│   ├── payment/                   # Payment API
│   ├── simulator/                 # Sandbox simulator
│   ├── supporting/                # File upload/download
│   └── webhook/                   # Webhook verification and event parsing
└── src/test/java/com/uqpay/sdk/v2/
    ├── banking/                   # Banking integration tests
    ├── connect/                   # Connect integration tests
    ├── issuing/                   # Issuing integration tests (incl. KYC)
    ├── payment/                   # Payment integration tests
    ├── simulator/                 # Simulator integration tests
    ├── supporting/                # Supporting integration tests
    └── webhook/                   # Webhook verifier unit tests
```

## Documentation

- [UQPAY API Reference](https://developer.uqpay.com/api/#/)

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.
