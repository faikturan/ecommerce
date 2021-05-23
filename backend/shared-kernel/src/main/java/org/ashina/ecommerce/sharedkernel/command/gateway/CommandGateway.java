package org.ashina.ecommerce.sharedkernel.command.gateway;

import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandlerNotFoundException;
import org.ashina.ecommerce.sharedkernel.command.model.Command;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

public abstract class CommandGateway {

    protected final Validator validator;

    protected CommandGateway(Validator validator) {
        this.validator = validator;
    }

    public void send(Command command) {
        if (command.isHasValidate()) {
            validate(command);
        }
        CommandHandler<Command> handler = lookupHandler(command)
                .orElseThrow(() -> new CommandHandlerNotFoundException(
                        String.format("Not found handler for command %s", command.getClass())));
        handler.handle(command);
    }

    protected abstract Optional<CommandHandler<Command>> lookupHandler(Command command);

    private void validate(Command command) {
        Set<ConstraintViolation<Command>> constraintViolations = validator.validate(command);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

}
