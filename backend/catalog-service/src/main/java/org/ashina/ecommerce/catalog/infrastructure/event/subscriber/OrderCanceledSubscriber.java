package org.ashina.ecommerce.catalog.infrastructure.event.subscriber;

import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCanceled;
import org.ashina.ecommerce.sharedkernel.event.subscriber.RemoteDomainEventSubscriber;

public interface OrderCanceledSubscriber extends RemoteDomainEventSubscriber<OrderCanceled> {
}
