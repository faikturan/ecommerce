package org.ashina.ecommerce.catalog.registry;

import org.ashina.ecommerce.catalog.application.query.handler.SearchProductsQueryHandler;
import org.ashina.ecommerce.catalog.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.catalog.infrastructure.search.SearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryHandlerRegistry {

    @Bean
    public SearchProductsQueryHandler searchProductsQueryHandler(SearchService searchService,
                                                                 ProductRepository productRepository) {
        return new SearchProductsQueryHandler(searchService, productRepository);
    }
}
