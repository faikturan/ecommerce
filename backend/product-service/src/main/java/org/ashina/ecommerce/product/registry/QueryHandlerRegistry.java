package org.ashina.ecommerce.product.registry;

import org.ashina.ecommerce.product.application.query.handler.SearchProductsQueryHandler;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.product.infrastructure.search.SearchService;
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
