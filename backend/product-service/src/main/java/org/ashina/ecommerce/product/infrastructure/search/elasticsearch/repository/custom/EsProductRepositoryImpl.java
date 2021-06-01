package org.ashina.ecommerce.product.infrastructure.search.elasticsearch.repository.custom;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.product.infrastructure.search.elasticsearch.model.EsProduct;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
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
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("*" + keyword + "*")
                .analyzeWildcard(true)
                .field("name")
                .field("description");
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(queryStringQueryBuilder);
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
