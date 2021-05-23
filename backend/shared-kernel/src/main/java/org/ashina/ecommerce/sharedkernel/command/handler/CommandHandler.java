package org.ashina.ecommerce.sharedkernel.command.handler;

import org.ashina.ecommerce.sharedkernel.command.model.Command;

public interface CommandHandler<C extends Command> {

    Class<? extends Command> support();

    void handle(C command);
}
