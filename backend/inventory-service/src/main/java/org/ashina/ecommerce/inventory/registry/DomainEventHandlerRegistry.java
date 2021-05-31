package org.ashina.ecommerce.inventory.registry;

import org.ashina.ecommerce.inventory.application.event.handler.OrderCanceledHandler;
import org.ashina.ecommerce.inventory.application.event.handler.OrderCreatedHandler;
import org.ashina.ecommerce.inventory.infrastructure.persistence.repository.StockRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventHandlerRegistry {

    @Bean
    public OrderCreatedHandler orderCreatedHandler(StockRepository stockRepository) {
        return new OrderCreatedHandler(stockRepository);
    }

    @Bean
    public OrderCanceledHandler orderCanceledHandler(StockRepository stockRepository) {
        return new OrderCanceledHandler(stockRepository);
    }
}
