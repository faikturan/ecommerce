package org.ashina.ecommerce.product.registry;

import org.ashina.ecommerce.product.application.command.handler.CreateProductCommandHandler;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.product.infrastructure.search.SearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerRegistry {

    @Bean
    public CreateProductCommandHandler createProductCommandHandler(ProductRepository productRepository,
                                                                   SearchService searchService) {
        return new CreateProductCommandHandler(productRepository, searchService);
    }
}
