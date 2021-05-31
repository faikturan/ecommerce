package org.ashina.ecommerce.catalog.registry;

import org.ashina.ecommerce.catalog.application.event.handler.OrderCanceledHandler;
import org.ashina.ecommerce.catalog.infrastructure.persistence.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventHandlerRegistry {

    @Bean
    public OrderCanceledHandler orderCanceledHandler(ProductRepository productRepository) {
        return new OrderCanceledHandler(productRepository);
    }
}
