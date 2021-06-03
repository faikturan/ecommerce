package org.ashina.ecommerce.product.application.event.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.sharedkernel.event.handler.DomainEventHandler;
import org.ashina.ecommerce.sharedkernel.event.model.product.ReserveProductRequested;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReserveProductRequestedHandler implements DomainEventHandler<ReserveProductRequested> {

    private final ProductRepository productRepository;

    @Override
    public void handle(ReserveProductRequested event) {

    }
}
