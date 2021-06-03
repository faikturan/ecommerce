package org.ashina.ecommerce.product.infrastructure.event.publisher;

import org.ashina.ecommerce.sharedkernel.event.model.product.ReserveProductReplied;
import org.ashina.ecommerce.sharedkernel.event.publisher.RemoteDomainEventPublisher;

public interface ReserveProductRepliedPublisher extends RemoteDomainEventPublisher<ReserveProductReplied> {
}
