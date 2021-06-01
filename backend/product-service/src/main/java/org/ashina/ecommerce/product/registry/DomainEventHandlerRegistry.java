package org.ashina.ecommerce.product.registry;

import org.ashina.ecommerce.product.application.event.handler.OrderCanceledHandler;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventHandlerRegistry {

    @Bean
    public OrderCanceledHandler orderCanceledHandler(ProductRepository productRepository) {
        return new OrderCanceledHandler(productRepository);
    }
}
