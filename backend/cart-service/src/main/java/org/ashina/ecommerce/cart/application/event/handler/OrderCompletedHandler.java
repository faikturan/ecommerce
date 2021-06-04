package org.ashina.ecommerce.cart.application.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.cart.infrastructure.persistence.repository.CartRepository;
import org.ashina.ecommerce.sharedkernel.event.handler.DomainEventHandler;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCompleted;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCompletedHandler implements DomainEventHandler<OrderCompleted> {

    private final CartRepository cartRepository;

    @Override
    public void handle(OrderCompleted event) {
        cartRepository.deleteByCustomerId(event.getCustomerId());
        log.debug("Clear cart of customer {} done", event.getCustomerId());
    }
}
