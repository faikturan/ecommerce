package org.ashina.ecommerce.sharedkernel.query.gateway;

public interface QueryGateway {

    Object execute(Object query);

    Object execute(Object query, boolean hasValidate);
}
