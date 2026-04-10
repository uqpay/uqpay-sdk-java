package com.uqpay.sdk.issuing;

import com.uqpay.sdk.issuing.model.AuthDecisionConfig;
import com.uqpay.sdk.issuing.model.AuthDecisionResponse;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.jcajce.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.util.Date;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link AuthDecisionService}.
 *
 * <p>Uses self-generated PGP key pairs to exercise the full encrypt→processRequest→decrypt
 * round-trip without any external dependencies.
 */
@DisplayName("AuthDecisionService")
class AuthDecisionServiceTest {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    // Key pair used by the service (our private key to decrypt incoming requests)
    private static PGPKeyPair serviceKeyPair;
    // Key pair that simulates UQPAY (their private key to decrypt our encrypted responses)
    private static PGPKeyPair uqpayKeyPair;

    private static String servicePrivateKeyArmored;
    private static String uqpayPublicKeyArmored;

    private static AuthDecisionService configuredService;

    @BeforeAll
    static void setUpKeys() throws Exception {
        serviceKeyPair = generateKeyPair();
        uqpayKeyPair = generateKeyPair();

        servicePrivateKeyArmored = exportSecretKey(serviceKeyPair, "service-test@uqpay.com", null);
        exportPublicKey(serviceKeyPair); // verify export works
        exportSecretKey(uqpayKeyPair, "uqpay-test@uqpay.com", null); // verify export works
        uqpayPublicKeyArmored = exportPublicKey(uqpayKeyPair);

        // Configure the service:
        //   - our private key  → to decrypt incoming requests from UQPAY
        //   - UQPAY's public key → to encrypt responses back to UQPAY
        AuthDecisionConfig config = new AuthDecisionConfig(
                servicePrivateKeyArmored,
                null,
                uqpayPublicKeyArmored
        );

        configuredService = new AuthDecisionService();
        configuredService.configure(config);
    }

    // -------------------------------------------------------------------------
    // PGP round-trip tests
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("processRequest")
    class ProcessRequest {

        @Test
        @DisplayName("should decrypt request and return encrypted approve response")
        void shouldProcessAndApproveTransaction() throws Exception {
            String payload = "{\"transaction_id\":\"txn_123\",\"card_id\":\"card_456\","
                    + "\"transaction_amount\":10.0,\"transaction_currency\":\"USD\"}";

            // Simulate UQPAY encrypting the request with our (service) public key
            String encryptedRequest = pgpEncrypt(payload, serviceKeyPair.getPublicKey());

            // Process: service decrypts with its private key, calls handler, encrypts with UQPAY's public key
            String encryptedResponse = configuredService.processRequest(
                    encryptedRequest,
                    request -> AuthDecisionResponse.approve(request.getTransactionId())
            );

            assertThat(encryptedResponse)
                    .isNotNull()
                    .isNotEmpty()
                    .contains("-----BEGIN PGP MESSAGE-----");

            // Simulate UQPAY decrypting our response with their private key
            String decryptedResponse = pgpDecrypt(encryptedResponse, uqpayKeyPair);

            assertThat(decryptedResponse)
                    .contains("\"transaction_id\":\"txn_123\"")
                    .contains("\"response_code\":\"00\"");
        }

        @Test
        @DisplayName("should decrypt request and return encrypted decline response")
        void shouldProcessAndDeclineTransaction() throws Exception {
            String payload = "{\"transaction_id\":\"txn_456\",\"card_id\":\"card_789\","
                    + "\"transaction_amount\":500.0,\"transaction_currency\":\"HKD\"}";

            String encryptedRequest = pgpEncrypt(payload, serviceKeyPair.getPublicKey());

            String encryptedResponse = configuredService.processRequest(
                    encryptedRequest,
                    request -> AuthDecisionResponse.decline(request.getTransactionId(), "51")
            );

            assertThat(encryptedResponse)
                    .isNotNull()
                    .contains("-----BEGIN PGP MESSAGE-----");

            String decryptedResponse = pgpDecrypt(encryptedResponse, uqpayKeyPair);

            assertThat(decryptedResponse)
                    .contains("\"transaction_id\":\"txn_456\"")
                    .contains("\"response_code\":\"51\"");
        }

        @Test
        @DisplayName("should throw IllegalStateException when service is not configured")
        void shouldThrowIfNotConfigured() {
            AuthDecisionService unconfigured = new AuthDecisionService();

            assertThatThrownBy(() -> unconfigured.processRequest(
                    "some-encrypted-body",
                    request -> AuthDecisionResponse.approve(request.getTransactionId())
            )).isInstanceOf(IllegalStateException.class);
        }
    }

    // -------------------------------------------------------------------------
    // AuthDecisionResponse factory tests (no PGP required)
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("AuthDecisionResponse static factories")
    class AuthDecisionResponseFactories {

        @Test
        @DisplayName("approve() should set response code 00 and transaction id")
        void approveFactory() {
            AuthDecisionResponse response = AuthDecisionResponse.approve("txn_1");

            assertThat(response.getTransactionId()).isEqualTo("txn_1");
            assertThat(response.getResponseCode()).isEqualTo("00");
        }

        @Test
        @DisplayName("decline() should set given response code and transaction id")
        void declineFactory() {
            AuthDecisionResponse response = AuthDecisionResponse.decline("txn_1", "51");

            assertThat(response.getTransactionId()).isEqualTo("txn_1");
            assertThat(response.getResponseCode()).isEqualTo("51");
        }

        @Test
        @DisplayName("decline() should preserve custom response code")
        void declineFactoryCustomCode() {
            AuthDecisionResponse response = AuthDecisionResponse.decline("txn_2", "05");

            assertThat(response.getResponseCode()).isEqualTo("05");
        }
    }

    // -------------------------------------------------------------------------
    // configure() validation tests
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("configure")
    class Configure {

        @Test
        @DisplayName("should throw when privateKey is null")
        void shouldThrowWhenPrivateKeyIsNull() {
            assertThatThrownBy(() -> new AuthDecisionConfig(null, null, uqpayPublicKeyArmored))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("should throw when uqpayPublicKey is null")
        void shouldThrowWhenUqpayPublicKeyIsNull() {
            assertThatThrownBy(() -> new AuthDecisionConfig(servicePrivateKeyArmored, null, null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    // -------------------------------------------------------------------------
    // PGP helper utilities (mirrors what AuthDecisionService does internally)
    // -------------------------------------------------------------------------

    private static PGPKeyPair generateKeyPair() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        return new JcaPGPKeyPair(PGPPublicKey.RSA_GENERAL, kp, new Date());
    }

    /**
     * Exports a PGP secret key as an armored ASCII string.
     */
    private static String exportSecretKey(PGPKeyPair keyPair, String userId, String passphrase) throws Exception {
        PGPDigestCalculator sha1Calc = new JcaPGPDigestCalculatorProviderBuilder()
                .build()
                .get(HashAlgorithmTags.SHA1);

        char[] pass = passphrase != null ? passphrase.toCharArray() : new char[0];

        PGPSecretKey secretKey = new PGPSecretKey(
                PGPSignature.DEFAULT_CERTIFICATION,
                keyPair,
                userId,
                sha1Calc,
                null,
                null,
                new JcaPGPContentSignerBuilder(keyPair.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA256),
                new JcePBESecretKeyEncryptorBuilder(PGPEncryptedData.CAST5, sha1Calc)
                        .setProvider("BC")
                        .build(pass)
        );

        PGPSecretKeyRing secretKeyRing = new PGPSecretKeyRing(
                java.util.Collections.singletonList(secretKey));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ArmoredOutputStream armoredOut = new ArmoredOutputStream(out)) {
            secretKeyRing.encode(armoredOut);
        }
        return out.toString(StandardCharsets.UTF_8.name());
    }

    /**
     * Exports a PGP public key as an armored ASCII string.
     */
    private static String exportPublicKey(PGPKeyPair keyPair) throws Exception {
        PGPPublicKeyRing publicKeyRing = new PGPPublicKeyRing(
                java.util.Collections.singletonList(keyPair.getPublicKey()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ArmoredOutputStream armoredOut = new ArmoredOutputStream(out)) {
            publicKeyRing.encode(armoredOut);
        }
        return out.toString(StandardCharsets.UTF_8.name());
    }

    /**
     * Encrypts plaintext with the given PGP public key, returning armored ciphertext.
     * Mirrors the encrypt() helper inside AuthDecisionService.
     */
    private static String pgpEncrypt(String plaintext, PGPPublicKey recipientKey) throws Exception {
        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ArmoredOutputStream armoredOut = new ArmoredOutputStream(out)) {
            ByteArrayOutputStream compressedOut = new ByteArrayOutputStream();
            PGPCompressedDataGenerator compressor =
                    new PGPCompressedDataGenerator(CompressionAlgorithmTags.ZIP);
            PGPLiteralDataGenerator literalGen = new PGPLiteralDataGenerator();
            try (OutputStream literalStream = literalGen.open(
                    compressor.open(compressedOut),
                    PGPLiteralData.BINARY,
                    PGPLiteralData.CONSOLE,
                    plaintextBytes.length,
                    new Date())) {
                literalStream.write(plaintextBytes);
            }
            compressor.close();

            byte[] compressedBytes = compressedOut.toByteArray();

            PGPEncryptedDataGenerator encGen = new PGPEncryptedDataGenerator(
                    new JcePGPDataEncryptorBuilder(SymmetricKeyAlgorithmTags.AES_256)
                            .setWithIntegrityPacket(true)
                            .setProvider("BC"));
            encGen.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(recipientKey).setProvider("BC"));

            try (OutputStream encryptedStream = encGen.open(armoredOut, compressedBytes.length)) {
                encryptedStream.write(compressedBytes);
            }
        }

        return out.toString(StandardCharsets.UTF_8.name());
    }

    /**
     * Decrypts an armored PGP ciphertext using the given key pair's private key.
     * Mirrors the decrypt() helper inside AuthDecisionService.
     */
    private static String pgpDecrypt(String armoredCiphertext, PGPKeyPair keyPair) throws Exception {
        byte[] ciphertextBytes = armoredCiphertext.getBytes(StandardCharsets.UTF_8);

        try (InputStream armoredStream = PGPUtil.getDecoderStream(
                new ByteArrayInputStream(ciphertextBytes))) {

            PGPObjectFactory factory = new PGPObjectFactory(armoredStream, new JcaKeyFingerprintCalculator());
            Object obj = factory.nextObject();

            PGPEncryptedDataList encryptedDataList;
            if (obj instanceof PGPEncryptedDataList) {
                encryptedDataList = (PGPEncryptedDataList) obj;
            } else {
                encryptedDataList = (PGPEncryptedDataList) factory.nextObject();
            }

            PGPPublicKeyEncryptedData encryptedData = null;
            PGPPrivateKey privateKey = null;

            Iterator<PGPEncryptedData> encDataIt = encryptedDataList.getEncryptedDataObjects();
            while (encDataIt.hasNext()) {
                PGPEncryptedData ed = encDataIt.next();
                if (ed instanceof PGPPublicKeyEncryptedData) {
                    PGPPublicKeyEncryptedData pked = (PGPPublicKeyEncryptedData) ed;
                    if (pked.getKeyID() == keyPair.getPublicKey().getKeyID()) {
                        privateKey = keyPair.getPrivateKey();
                        encryptedData = pked;
                        break;
                    }
                }
            }

            if (privateKey == null || encryptedData == null) {
                throw new PGPException("No matching private key found to decrypt the test message");
            }

            try (InputStream decryptedStream = encryptedData.getDataStream(
                    new JcePublicKeyDataDecryptorFactoryBuilder().setProvider("BC").build(privateKey))) {

                PGPObjectFactory plainFactory = new PGPObjectFactory(
                        decryptedStream, new JcaKeyFingerprintCalculator());
                Object plainObj = plainFactory.nextObject();

                if (plainObj instanceof PGPCompressedData) {
                    PGPCompressedData compressedData = (PGPCompressedData) plainObj;
                    plainFactory = new PGPObjectFactory(
                            compressedData.getDataStream(), new JcaKeyFingerprintCalculator());
                    plainObj = plainFactory.nextObject();
                }

                if (plainObj instanceof PGPLiteralData) {
                    PGPLiteralData literalData = (PGPLiteralData) plainObj;
                    try (InputStream literalStream = literalData.getInputStream()) {
                        ByteArrayOutputStream buf = new ByteArrayOutputStream();
                        byte[] tmp = new byte[4096];
                        int n;
                        while ((n = literalStream.read(tmp)) >= 0) {
                            buf.write(tmp, 0, n);
                        }
                        return buf.toString(StandardCharsets.UTF_8.name());
                    }
                }

                throw new PGPException("Unexpected PGP object: " +
                        (plainObj != null ? plainObj.getClass().getSimpleName() : "null"));
            }
        }
    }
}
