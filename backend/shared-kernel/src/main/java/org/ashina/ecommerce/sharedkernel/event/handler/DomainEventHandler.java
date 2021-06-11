package org.ashina.ecommerce.sharedkernel.event.handler;

import org.ashina.ecommerce.sharedkernel.event.model.DomainEvent;

public interface DomainEventHandler<T extends DomainEvent> {

    void handle(T event);
}
