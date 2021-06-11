package org.ashina.ecommerce.sharedkernel.event.subscriber;

import org.ashina.ecommerce.sharedkernel.event.model.DomainEvent;

import java.util.Map;

public interface DomainEventSubscriber<T extends DomainEvent> {

    void subscribe(T event, Map<String, Object> headers);

}
