package org.ashina.ecommerce.uaa.server.security.jwt.jwk;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Base64Utils;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class OpenSSLGenerator implements KeyGenerator {

    @Override
    public KeyPair keyPair() throws Exception {
        PublicKey publicKey = resourceToPublicKey(
                new FileSystemResource("classpath:certificate/openssl/public_key.pem"));
        PrivateKey privateKey = resourceToPrivateKey(
                new FileSystemResource("classpath:certificate/openssl/private_key.pem"));
        return new KeyPair(publicKey, privateKey);
    }

    private PublicKey resourceToPublicKey(Resource resource)
            throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        String publicKey = resourceToString(resource)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .trim()
                .replace("\r\n", "")
                .replace("\n", "");
        byte[] encodedPublicKey = Base64Utils.decodeFromString(publicKey);
        EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(encodedKeySpec);
    }

    private PrivateKey resourceToPrivateKey(Resource resource)
            throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        String privateKey = resourceToString(resource)
                .replace("fake", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .trim()
                .replace("\r\n", "")
                .replace("\n", "");
        byte[] encodedPrivateKey = Base64Utils.decodeFromString(privateKey);
        EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private String resourceToString(Resource resource) throws IOException {
        try (InputStream stream = resource.getInputStream()) {
            return StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
        }
    }
}
