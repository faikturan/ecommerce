package org.ashina.ecommerce.product.application.event.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.sharedkernel.event.handler.DomainEventHandler;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCanceled;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderCanceledHandler implements DomainEventHandler<OrderCanceled> {

    private final ProductRepository productRepository;

    @Override
    public void handle(OrderCanceled event) {
        Map<String, Integer> productIdAndIncrementMap = event.getLines().stream()
                .collect(Collectors.toMap(OrderCanceled.Line::getProductId, OrderCanceled.Line::getQuantity));
        productRepository.increaseQuantity(productIdAndIncrementMap);
    }
}
