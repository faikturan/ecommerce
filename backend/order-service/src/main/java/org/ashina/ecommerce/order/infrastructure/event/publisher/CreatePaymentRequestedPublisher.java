package org.ashina.ecommerce.order.infrastructure.event.publisher;

import org.ashina.ecommerce.sharedkernel.event.model.payment.CreatePaymentRequested;
import org.ashina.ecommerce.sharedkernel.event.publisher.RemoteDomainEventPublisher;

public interface CreatePaymentRequestedPublisher extends RemoteDomainEventPublisher<CreatePaymentRequested> {
}
