package org.ashina.ecommerce.sharedkernel.command.gateway;

import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandlerNotFoundException;
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
public class DefaultCommandGateway implements CommandGateway {

    private final ApplicationContext applicationContext;
    private final Validator validator;
    private final Map<Class<?>, CommandHandler<?, ?>> handlerCache;

    public DefaultCommandGateway(ApplicationContext applicationContext,
                                 Validator validator) {
        this.applicationContext = applicationContext;
        this.validator = validator;
        this.handlerCache = new HashMap<>();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void send(Object command) {
        CommandHandler handler = lookupHandler(command)
                .orElseThrow(() -> commandHandlerNotFoundException(command));
        handler.handle(command);
    }

    @Override
    public void send(Object command, boolean hasValidate) {
        if (hasValidate) {
            validate(command);
        }
        send(command);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Object sendAndWait(Object command) {
        CommandHandler handler = lookupHandler(command)
                .orElseThrow(() -> commandHandlerNotFoundException(command));
        return handler.handle(command);
    }

    @Override
    public Object sendAndWait(Object command, boolean hasValidate) {
        if (hasValidate) {
            validate(command);
        }
        return sendAndWait(command);
    }

    @SuppressWarnings("rawtypes")
    private Optional<CommandHandler> lookupHandler(Object command) {
        CommandHandler handler = handlerCache.get(command.getClass());
        if (handler != null) {
            return Optional.of(handler);
        }
        Map<String, CommandHandler> handlerMap = applicationContext.getBeansOfType(CommandHandler.class);
        for (Map.Entry<String, CommandHandler> entry : handlerMap.entrySet()) {
            handler = entry.getValue();
            if (handler.support().equals(command.getClass())) {
                handlerCache.put(command.getClass(), handler);
                return Optional.of(handler);
            }
        }
        return Optional.empty();
    }

    private CommandHandlerNotFoundException commandHandlerNotFoundException(Object command) {
        return new CommandHandlerNotFoundException(String.format("Not found handler for command %s", command.getClass()));
    }

    private void validate(Object command) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(command);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
