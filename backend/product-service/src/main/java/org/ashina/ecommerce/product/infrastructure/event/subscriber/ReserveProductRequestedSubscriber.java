package org.ashina.ecommerce.product.infrastructure.event.subscriber;

import org.ashina.ecommerce.sharedkernel.event.model.product.ReserveProductRequested;
import org.ashina.ecommerce.sharedkernel.event.subscriber.RemoteDomainEventSubscriber;

public interface ReserveProductRequestedSubscriber extends RemoteDomainEventSubscriber<ReserveProductRequested> {
}
