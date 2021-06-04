package org.ashina.ecommerce.cart.infrastructure.event.subscriber;

import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCompleted;
import org.ashina.ecommerce.sharedkernel.event.subscriber.RemoteDomainEventSubscriber;

public interface OrderCompletedSubscriber extends RemoteDomainEventSubscriber<OrderCompleted> {
}
