package org.ashina.ecommerce.uaa.server.security.jwt.jwk;

import java.security.KeyPair;

public interface KeyGenerator {

    KeyPair keyPair() throws Exception;
}
