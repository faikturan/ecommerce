package org.ashina.ecommerce.order.infrastructure.event.publisher;

import org.ashina.ecommerce.sharedkernel.event.model.product.ProductReservedRequest;
import org.ashina.ecommerce.sharedkernel.event.publisher.RemoteDomainEventPublisher;

public interface ProductReservedRequestPublisher extends RemoteDomainEventPublisher<ProductReservedRequest> {
}
