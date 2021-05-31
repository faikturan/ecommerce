package org.ashina.ecommerce.catalog.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.catalog.application.query.model.SearchProductQuery;
import org.ashina.ecommerce.catalog.application.query.model.SearchProductView;
import org.ashina.ecommerce.catalog.domain.Product;
import org.ashina.ecommerce.catalog.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.catalog.infrastructure.search.SearchProductService;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;

import java.util.List;

@RequiredArgsConstructor
public class SearchProductQueryHandler implements QueryHandler<SearchProductQuery, SearchProductView> {

    private final SearchProductService searchProductService;
    private final ProductRepository productRepository;

    @Override
    public Class<?> support() {
        return SearchProductQuery.class;
    }

    @Override
    public SearchProductView handle(SearchProductQuery query) {
        List<String> productIds = searchProductService.search(query.getKeyword(), query.getPage(), query.getSize());
        List<Product> products = (List<Product>) productRepository.findAllById(productIds);
        return new SearchProductView(products);
    }
}
