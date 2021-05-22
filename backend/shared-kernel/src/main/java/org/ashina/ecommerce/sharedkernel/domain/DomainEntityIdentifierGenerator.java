package org.ashina.ecommerce.sharedkernel.domain;

import java.util.UUID;

public class DomainEntityIdentifierGenerator {

    private DomainEntityIdentifierGenerator() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
