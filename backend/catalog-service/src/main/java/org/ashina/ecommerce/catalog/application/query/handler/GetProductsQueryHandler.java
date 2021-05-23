package org.ashina.ecommerce.catalog.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.catalog.application.query.model.GetProductsQuery;
import org.ashina.ecommerce.catalog.application.query.model.GetProductsView;
import org.ashina.ecommerce.catalog.domain.Product;
import org.ashina.ecommerce.catalog.infrastructure.persistence.ProductPersistence;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;
import org.ashina.ecommerce.sharedkernel.query.model.Query;

import java.util.List;

@RequiredArgsConstructor
public class GetProductsQueryHandler implements QueryHandler<GetProductsQuery, GetProductsView> {

    private final ProductPersistence productPersistence;

    @Override
    public Class<? extends Query> support() {
        return GetProductsQuery.class;
    }

    @Override
    public GetProductsView handle(GetProductsQuery query) {
        List<Product> products = productPersistence.findByIdIn(query.getIds());
        return new GetProductsView(products);
    }
}
