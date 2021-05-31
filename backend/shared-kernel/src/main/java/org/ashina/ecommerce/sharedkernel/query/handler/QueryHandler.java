package org.ashina.ecommerce.sharedkernel.query.handler;

public interface QueryHandler<Q, V> {

    Class<?> support();

    V handle(Q query);
}
