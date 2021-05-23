package org.ashina.ecommerce.sharedkernel.query.handler;

import org.ashina.ecommerce.sharedkernel.query.model.Query;
import org.ashina.ecommerce.sharedkernel.query.model.View;

public interface QueryHandler<Q extends Query, V extends View> {

    Class<? extends Query> support();

    V handle(Q query);
}
