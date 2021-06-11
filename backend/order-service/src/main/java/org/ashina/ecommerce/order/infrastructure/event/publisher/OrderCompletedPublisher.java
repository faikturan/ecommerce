package org.ashina.ecommerce.order.infrastructure.event.publisher;

import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCompleted;
import org.ashina.ecommerce.sharedkernel.event.publisher.DomainEventPublisher;

public interface OrderCompletedPublisher extends DomainEventPublisher<OrderCompleted> {
}
