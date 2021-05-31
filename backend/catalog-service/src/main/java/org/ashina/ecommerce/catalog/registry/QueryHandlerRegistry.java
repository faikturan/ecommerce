package org.ashina.ecommerce.catalog.registry;

import org.ashina.ecommerce.catalog.application.query.handler.SearchProductQueryHandler;
import org.ashina.ecommerce.catalog.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.catalog.infrastructure.search.SearchProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryHandlerRegistry {

    @Bean
    public SearchProductQueryHandler searchProductQueryHandler(SearchProductService searchProductService,
                                                               ProductRepository productRepository) {
        return new SearchProductQueryHandler(searchProductService, productRepository);
    }
}
