package org.ashina.ecommerce.product.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.product.application.query.model.GetProductsQuery;
import org.ashina.ecommerce.product.application.query.model.GetProductsView;
import org.ashina.ecommerce.product.domain.Product;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetProductsQueryHandler implements QueryHandler<GetProductsQuery, GetProductsView> {

    private final ProductRepository productRepository;

    @Override
    public Class<?> support() {
        return GetProductsQuery.class;
    }

    @Override
    public GetProductsView handle(GetProductsQuery query) {
        List<Product> products = (List<Product>) productRepository.findAllById(query.getIds());
        return new GetProductsView(products);
    }
}
