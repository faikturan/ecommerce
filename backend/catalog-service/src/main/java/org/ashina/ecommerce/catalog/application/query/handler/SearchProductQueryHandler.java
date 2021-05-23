package org.ashina.ecommerce.catalog.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.catalog.application.query.model.SearchProductQuery;
import org.ashina.ecommerce.catalog.application.query.model.SearchProductView;
import org.ashina.ecommerce.catalog.domain.Product;
import org.ashina.ecommerce.catalog.infrastructure.persistence.ProductPersistence;
import org.ashina.ecommerce.catalog.infrastructure.search.ProductSearch;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;
import org.ashina.ecommerce.sharedkernel.query.model.Query;

import java.util.List;

@RequiredArgsConstructor
public class SearchProductQueryHandler implements QueryHandler<SearchProductQuery, SearchProductView> {

    private final ProductSearch productSearch;
    private final ProductPersistence productPersistence;

    @Override
    public Class<? extends Query> support() {
        return SearchProductQuery.class;
    }

    @Override
    public SearchProductView handle(SearchProductQuery query) {
        List<String> productIds = productSearch.search(query.getKeyword(), query.getPage(), query.getSize());
        List<Product> products = productPersistence.findByIdIn(productIds);
        return new SearchProductView(products);
    }

}
