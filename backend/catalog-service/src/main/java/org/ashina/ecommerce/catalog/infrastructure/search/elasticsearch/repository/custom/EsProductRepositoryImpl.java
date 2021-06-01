package org.ashina.ecommerce.catalog.infrastructure.search.elasticsearch.repository.custom;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.catalog.infrastructure.search.elasticsearch.model.EsProduct;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EsProductRepositoryImpl implements CustomEsProductRepository {

    private final ElasticsearchOperations esTemplate;

    @Override
    public List<EsProduct> search(String keyword, Pageable pageable) {
        // 1. Build query
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.queryStringQuery("*" + keyword + "*").field("name").field("description"));
        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(pageable)
                .build();

        // 2. Execute search
        SearchHits<EsProduct> searchHits
                = esTemplate.search(searchQuery, EsProduct.class, IndexCoordinates.of("products"));

        // 3. Map searchHits to results
        List<EsProduct> results = new ArrayList<>();
        searchHits.forEach(searchHit -> results.add(searchHit.getContent()));
        return results;
    }
}
