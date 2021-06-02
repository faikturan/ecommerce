package org.ashina.ecommerce.sharedkernel.query.gateway;

import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandlerNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class DefaultQueryGateway implements QueryGateway {

    private final ApplicationContext applicationContext;
    private final Validator validator;
    private final Map<Class<?>, QueryHandler<?, ?>> handlerCache;

    public DefaultQueryGateway(ApplicationContext applicationContext,
                               Validator validator) {
        this.applicationContext = applicationContext;
        this.validator = validator;
        this.handlerCache = new HashMap<>();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Object execute(Object query) {
        QueryHandler handler = lookupHandler(query)
                .orElseThrow(() -> queryHandlerNotFoundException(query));
        return handler.handle(query);
    }

    @Override
    public Object execute(Object query, boolean hasValidate) {
        if (hasValidate) {
            validate(query);
        }
        return execute(query);
    }

    @SuppressWarnings("rawtypes")
    protected Optional<QueryHandler> lookupHandler(Object query) {
        QueryHandler handler = handlerCache.get(query.getClass());
        if (handler != null) {
            return Optional.of(handler);
        }
        Map<String, QueryHandler> handlerMap = applicationContext.getBeansOfType(QueryHandler.class);
        for (Map.Entry<String, QueryHandler> entry : handlerMap.entrySet()) {
            handler = entry.getValue();
            if (handler.support().equals(query.getClass())) {
                handlerCache.put(query.getClass(), handler);
                return Optional.of(handler);
            }
        }
        return Optional.empty();
    }

    public QueryHandlerNotFoundException queryHandlerNotFoundException(Object query) {
        return new QueryHandlerNotFoundException(String.format("Not found handler for query %s", query.getClass()));
    }

    private void validate(Object query) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(query);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
