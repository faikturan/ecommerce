package org.ashina.ecommerce.order.infrastructure.event.subscriber;

import org.ashina.ecommerce.sharedkernel.event.model.payment.CreatePaymentReplied;
import org.ashina.ecommerce.sharedkernel.event.subscriber.RemoteDomainEventSubscriber;

public interface CreatePaymentRepliedSubscriber extends RemoteDomainEventSubscriber<CreatePaymentReplied> {
}
