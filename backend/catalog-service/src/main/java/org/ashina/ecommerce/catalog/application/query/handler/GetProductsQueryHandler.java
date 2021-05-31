package org.ashina.ecommerce.catalog.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.catalog.application.query.model.GetProductsQuery;
import org.ashina.ecommerce.catalog.application.query.model.GetProductsView;
import org.ashina.ecommerce.catalog.domain.Product;
import org.ashina.ecommerce.catalog.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;

import java.util.List;

@RequiredArgsConstructor
public class GetProductsQueryHandler implements QueryHandler<GetProductsQuery, GetProductsView> {

    private final ProductRepository productRepository;

    @Override
    public Class<?> support() {
        return GetProductsQuery.class;
    }

    @Override
    public GetProductsView handle(GetProductsQuery query) {
        List<Product> products = productRepository.findAllById(query.getIds());
        return new GetProductsView(products);
    }
}
