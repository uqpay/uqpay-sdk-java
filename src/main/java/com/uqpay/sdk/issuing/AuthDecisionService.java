package com.uqpay.sdk.issuing;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.issuing.model.AuthDecisionConfig;
import com.uqpay.sdk.issuing.model.AuthDecisionRequest;
import com.uqpay.sdk.issuing.model.AuthDecisionResponse;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.jcajce.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Security;
import java.util.Date;
import java.util.Iterator;

/**
 * Service for handling PGP-based real-time card transaction authorization.
 *
 * <p>Implements the flow: PGP decrypt → parse → business logic → serialize → PGP encrypt.
 * Configure once with {@link #configure(AuthDecisionConfig)}, then call
 * {@link #processRequest(String, AuthDecisionHandler)} for each incoming request.
 */
public class AuthDecisionService {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private volatile PGPSecretKeyRingCollection secretKeyRings;
    private volatile String passphrase;
    private volatile PGPPublicKey uqpayPublicKey;

    public AuthDecisionService() {
    }

    /**
     * Loads PGP keys from the given config.
     *
     * <p>If a key string ends in {@code .asc}, {@code .pgp}, or {@code .gpg}, it is treated as
     * a file path and the key content is read from disk. Otherwise, it is treated as armored key
     * content directly.
     *
     * @param config the auth decision configuration containing private and public keys
     * @throws IOException  if a key file cannot be read
     * @throws PGPException if a key cannot be parsed
     */
    public void configure(AuthDecisionConfig config) throws UqpayException {
        try {
            this.passphrase = config.getPassphrase();

            String privateKeyContent = resolveKeyContent(config.getPrivateKey());
            try (InputStream is = PGPUtil.getDecoderStream(
                    new ByteArrayInputStream(privateKeyContent.getBytes(StandardCharsets.UTF_8)))) {
                this.secretKeyRings = new PGPSecretKeyRingCollection(is, new JcaKeyFingerprintCalculator());
            }

            String publicKeyContent = resolveKeyContent(config.getUqpayPublicKey());
            try (InputStream is = PGPUtil.getDecoderStream(
                    new ByteArrayInputStream(publicKeyContent.getBytes(StandardCharsets.UTF_8)))) {
                PGPPublicKeyRingCollection publicKeyRings = new PGPPublicKeyRingCollection(
                        is, new JcaKeyFingerprintCalculator());
                this.uqpayPublicKey = findFirstEncryptionKey(publicKeyRings);
            }

            if (this.uqpayPublicKey == null) {
                throw new UqpayException("No encryption-capable public key found in uqpayPublicKey");
            }
        } catch (UqpayException e) {
            throw e;
        } catch (IOException | PGPException e) {
            throw new UqpayException("Failed to configure AuthDecisionService: " + e.getMessage(), e);
        }
    }

    /**
     * Processes an encrypted auth decision request.
     *
     * <p>Decrypts the PGP-encrypted body, parses it as an {@link AuthDecisionRequest}, calls the
     * provided handler, serializes the response, then re-encrypts it with UQPAY's public key.
     *
     * @param encryptedBody the armored PGP-encrypted request body
     * @param handler       the business logic handler that decides approve/decline
     * @return armored PGP-encrypted response ciphertext
     * @throws IOException  if I/O operations fail
     * @throws PGPException if PGP operations fail
     */
    public String processRequest(String encryptedBody, AuthDecisionHandler handler)
            throws UqpayException {
        if (secretKeyRings == null || uqpayPublicKey == null) {
            throw new IllegalStateException(
                    "AuthDecisionService is not configured. Call configure(AuthDecisionConfig) first.");
        }

        try {
            // 1. Decrypt
            String plainJson = decrypt(encryptedBody);

            // 2. Parse
            AuthDecisionRequest request = MAPPER.readValue(plainJson, AuthDecisionRequest.class);

            // 3. Business logic
            AuthDecisionResponse response = handler.decide(request);

            // 4. Serialize
            String responseJson = MAPPER.writeValueAsString(response);

            // 5. Encrypt with UQPAY's public key
            return encrypt(responseJson, uqpayPublicKey);
        } catch (IOException | PGPException e) {
            throw new UqpayException("Failed to process auth decision request: " + e.getMessage(), e);
        }
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private String resolveKeyContent(String keyOrPath) throws IOException, UqpayException {
        String lower = keyOrPath.trim().toLowerCase();
        if (lower.endsWith(".asc") || lower.endsWith(".pgp") || lower.endsWith(".gpg")) {
            return new String(Files.readAllBytes(Paths.get(keyOrPath.trim())), StandardCharsets.UTF_8);
        }
        return keyOrPath;
    }

    private PGPPublicKey findFirstEncryptionKey(PGPPublicKeyRingCollection rings) {
        Iterator<PGPPublicKeyRing> ringIt = rings.getKeyRings();
        while (ringIt.hasNext()) {
            PGPPublicKeyRing ring = ringIt.next();
            Iterator<PGPPublicKey> keyIt = ring.getPublicKeys();
            while (keyIt.hasNext()) {
                PGPPublicKey key = keyIt.next();
                if (key.isEncryptionKey()) {
                    return key;
                }
            }
        }
        return null;
    }

    private String decrypt(String armoredCiphertext) throws IOException, PGPException {
        byte[] ciphertextBytes = armoredCiphertext.getBytes(StandardCharsets.UTF_8);
        try (InputStream armoredStream = PGPUtil.getDecoderStream(
                new ByteArrayInputStream(ciphertextBytes))) {

            PGPObjectFactory factory = new PGPObjectFactory(armoredStream, new JcaKeyFingerprintCalculator());
            Object obj = factory.nextObject();

            // Unwrap encrypted data list
            PGPEncryptedDataList encryptedDataList;
            if (obj instanceof PGPEncryptedDataList) {
                encryptedDataList = (PGPEncryptedDataList) obj;
            } else {
                encryptedDataList = (PGPEncryptedDataList) factory.nextObject();
            }

            // Find the encrypted data item that matches one of our secret keys
            PGPPublicKeyEncryptedData encryptedData = null;
            PGPPrivateKey privateKey = null;

            Iterator<PGPEncryptedData> encDataIt = encryptedDataList.getEncryptedDataObjects();
            while (encDataIt.hasNext()) {
                PGPEncryptedData ed = encDataIt.next();
                if (ed instanceof PGPPublicKeyEncryptedData) {
                    PGPPublicKeyEncryptedData pked = (PGPPublicKeyEncryptedData) ed;
                    PGPSecretKey secretKey = secretKeyRings.getSecretKey(pked.getKeyID());
                    if (secretKey != null) {
                        char[] pass = passphrase != null ? passphrase.toCharArray() : new char[0];
                        privateKey = secretKey.extractPrivateKey(
                                new JcePBESecretKeyDecryptorBuilder().setProvider("BC").build(pass));
                        encryptedData = pked;
                        break;
                    }
                }
            }

            if (privateKey == null || encryptedData == null) {
                throw new PGPException("No matching private key found to decrypt the message");
            }

            // Decrypt to compressed/literal stream
            try (InputStream decryptedStream = encryptedData.getDataStream(
                    new JcePublicKeyDataDecryptorFactoryBuilder().setProvider("BC").build(privateKey))) {

                PGPObjectFactory plainFactory = new PGPObjectFactory(
                        decryptedStream, new JcaKeyFingerprintCalculator());

                Object plainObj = plainFactory.nextObject();

                // Unwrap compression if present
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

                throw new PGPException("Unexpected PGP object type: " +
                        (plainObj != null ? plainObj.getClass().getSimpleName() : "null"));
            }
        }
    }

    private String encrypt(String plaintext, PGPPublicKey recipientKey) throws IOException, PGPException {
        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ArmoredOutputStream armoredOut = new ArmoredOutputStream(out)) {

            // Build compressor → literal data → encrypt pipeline (inner to outer)
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
}
