package org.ashina.ecommerce.payment.infrastructure.event.subscriber;

import org.ashina.ecommerce.sharedkernel.event.model.payment.CreatePaymentRequested;
import org.ashina.ecommerce.sharedkernel.event.subscriber.RemoteDomainEventSubscriber;

public interface CreatePaymentRequestedSubscriber extends RemoteDomainEventSubscriber<CreatePaymentRequested> {
}
