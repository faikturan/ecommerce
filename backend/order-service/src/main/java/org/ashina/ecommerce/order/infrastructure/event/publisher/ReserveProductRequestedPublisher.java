package org.ashina.ecommerce.order.infrastructure.event.publisher;

import org.ashina.ecommerce.sharedkernel.event.model.product.ReserveProductRequested;
import org.ashina.ecommerce.sharedkernel.event.publisher.RemoteDomainEventPublisher;

public interface ReserveProductRequestedPublisher extends RemoteDomainEventPublisher<ReserveProductRequested> {
}
