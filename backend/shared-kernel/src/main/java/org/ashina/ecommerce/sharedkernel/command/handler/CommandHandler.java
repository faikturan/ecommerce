package org.ashina.ecommerce.sharedkernel.command.handler;

public interface CommandHandler<C, R> {

    Class<?> support();

    R handle(C command);
}
