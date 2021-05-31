package org.ashina.ecommerce.inventory.registry;

import org.ashina.ecommerce.inventory.application.command.handler.PurchaseProductCommandHandler;
import org.ashina.ecommerce.inventory.infrastructure.ecommerce.CatalogService;
import org.ashina.ecommerce.inventory.infrastructure.persistence.repository.StockRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerRegistry {

    @Bean
    public PurchaseProductCommandHandler createPurchaseCommandHandler(StockRepository stockRepository,
                                                                      CatalogService catalogService) {
        return new PurchaseProductCommandHandler(stockRepository, catalogService);
    }
}
