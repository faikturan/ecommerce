package org.ashina.ecommerce.order.application.event.handler;

import org.ashina.ecommerce.sharedkernel.event.handler.DomainEventHandler;
import org.ashina.ecommerce.sharedkernel.event.model.payment.CreatePaymentReplied;
import org.springframework.stereotype.Component;

@Component
public class CreatePaymentRepliedHandler implements DomainEventHandler<CreatePaymentReplied> {
    @Override
    public void handle(CreatePaymentReplied event) {

    }
}
