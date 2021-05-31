package org.ashina.ecommerce.catalog.registry;

import org.ashina.ecommerce.catalog.infrastructure.search.SearchProductService;
import org.ashina.ecommerce.catalog.infrastructure.search.elasticsearch.adapter.EsSearchProductServiceAdapter;
import org.ashina.ecommerce.catalog.infrastructure.search.elasticsearch.repository.EsProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchRegistry {

    @Bean
    public SearchProductService productSearch(EsProductRepository elasticsearchProductRepository) {
        return new EsSearchProductServiceAdapter(elasticsearchProductRepository);
    }
}
