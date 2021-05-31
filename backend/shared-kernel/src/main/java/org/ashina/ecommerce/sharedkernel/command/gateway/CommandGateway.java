package org.ashina.ecommerce.sharedkernel.command.gateway;

public interface CommandGateway {

    void send(Object command);

    void send(Object command, boolean hasValidate);

    Object sendAndWait(Object command);

    Object sendAndWait(Object command, boolean hasValidate);
}
