package org.ashina.ecommerce.uaa.server.security.jwt.jwk;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

public class KeytoolGenerator implements KeyGenerator {

    // TODO: move passwords to application.yml
    @Override
    public KeyPair keyPair() throws Exception {
        String keyStorePassword = "123456";
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("jwk/keytool/jwt.jks"), keyStorePassword.toCharArray());
        String keyPairPassword = "123456";
        return keyStoreKeyFactory.getKeyPair("jwt", keyPairPassword.toCharArray());
    }
}
