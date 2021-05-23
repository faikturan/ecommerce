package org.ashina.ecommerce.sharedkernel.query.gateway;

import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandlerNotFoundException;
import org.ashina.ecommerce.sharedkernel.query.model.Query;
import org.ashina.ecommerce.sharedkernel.query.model.View;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

public abstract class QueryGateway {

    protected final Validator validator;

    protected QueryGateway(Validator validator) {
        this.validator = validator;
    }

    public View execute(Query query) {
        if (query.isHasValidate()) {
            validate(query);
        }
        QueryHandler<Query, View> handler = lookupHandler(query)
                .orElseThrow(() -> new QueryHandlerNotFoundException(
                        String.format("Not found handler for query %s", query.getClass())));
        return handler.handle(query);
    }

    protected abstract Optional<QueryHandler<Query, View>> lookupHandler(Query query);

    private void validate(Query query) {
        Set<ConstraintViolation<Query>> constraintViolations = validator.validate(query);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

}
