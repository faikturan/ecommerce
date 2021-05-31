package org.ashina.ecommerce.catalog.registry;

import org.ashina.ecommerce.catalog.application.command.handler.CreateProductCommandHandler;
import org.ashina.ecommerce.catalog.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.catalog.infrastructure.search.SearchProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerRegistry {

    @Bean
    public CreateProductCommandHandler createProductCommandHandler(ProductRepository productRepository,
                                                                   SearchProductService searchProductService) {
        return new CreateProductCommandHandler(productRepository, searchProductService);
    }
}
