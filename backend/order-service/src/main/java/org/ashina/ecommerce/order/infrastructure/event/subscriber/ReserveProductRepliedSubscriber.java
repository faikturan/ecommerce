package org.ashina.ecommerce.order.infrastructure.event.subscriber;

import org.ashina.ecommerce.sharedkernel.event.model.product.ReserveProductReplied;
import org.ashina.ecommerce.sharedkernel.event.subscriber.RemoteDomainEventSubscriber;

public interface ReserveProductRepliedSubscriber extends RemoteDomainEventSubscriber<ReserveProductReplied> {
}
