package org.ashina.ecommerce.product.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.product.application.query.model.SearchProductsQuery;
import org.ashina.ecommerce.product.application.query.model.SearchProductsView;
import org.ashina.ecommerce.product.domain.Product;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.product.infrastructure.search.SearchService;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchProductsQueryHandler implements QueryHandler<SearchProductsQuery, SearchProductsView> {

    private final SearchService searchService;
    private final ProductRepository productRepository;

    @Override
    public Class<?> support() {
        return SearchProductsQuery.class;
    }

    @Override
    public SearchProductsView handle(SearchProductsQuery query) {
        List<String> productIds = searchService.search(query.getKeyword(), query.getPage(), query.getSize());
        List<Product> products = (List<Product>) productRepository.findAllById(productIds);
        return new SearchProductsView(products);
    }
}
