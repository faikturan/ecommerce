package org.ashina.ecommerce.sharedkernel.event.subscriber;

import org.ashina.ecommerce.sharedkernel.exception.DomainException;
import org.ashina.ecommerce.sharedkernel.event.model.DomainEvent;

import java.util.Map;

public interface RemoteDomainEventSubscriber<T extends DomainEvent> {

    void subscribe(T event, Map<String, Object> headers) throws DomainException;

}
