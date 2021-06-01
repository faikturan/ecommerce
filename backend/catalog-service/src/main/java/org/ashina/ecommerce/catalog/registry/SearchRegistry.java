package org.ashina.ecommerce.catalog.registry;

import org.ashina.ecommerce.catalog.infrastructure.search.SearchService;
import org.ashina.ecommerce.catalog.infrastructure.search.elasticsearch.EsSearchService;
import org.ashina.ecommerce.catalog.infrastructure.search.elasticsearch.repository.EsProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchRegistry {

    @Bean
    public SearchService productSearch(EsProductRepository elasticsearchProductRepository) {
        return new EsSearchService(elasticsearchProductRepository);
    }
}
