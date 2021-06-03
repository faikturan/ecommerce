package org.ashina.ecommerce.order.application.event.handler;

import org.ashina.ecommerce.sharedkernel.event.handler.DomainEventHandler;
import org.ashina.ecommerce.sharedkernel.event.model.product.ReserveProductReplied;
import org.springframework.stereotype.Component;

@Component
public class ReserveProductRepliedHandler implements DomainEventHandler<ReserveProductReplied> {
    @Override
    public void handle(ReserveProductReplied event) {

    }
}
