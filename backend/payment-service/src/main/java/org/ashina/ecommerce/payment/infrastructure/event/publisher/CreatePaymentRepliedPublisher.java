package org.ashina.ecommerce.payment.infrastructure.event.publisher;

import org.ashina.ecommerce.sharedkernel.event.model.payment.CreatePaymentReplied;
import org.ashina.ecommerce.sharedkernel.event.publisher.RemoteDomainEventPublisher;

public interface CreatePaymentRepliedPublisher extends RemoteDomainEventPublisher<CreatePaymentReplied> {
}
