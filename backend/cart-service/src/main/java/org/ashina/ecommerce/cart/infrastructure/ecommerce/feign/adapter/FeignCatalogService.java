package org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.adapter;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.CatalogService;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.client.CatalogClient;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.model.Product;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FeignCatalogService implements CatalogService {

    private final CatalogClient catalogClient;

    @Override
    public Map<String, Product> getProducts(Collection<String> ids) {
        List<Product> products = catalogClient.getProducts(ids);
        return products
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    @Override
    public Optional<Product> getProduct(String id) {
        List<Product> products = catalogClient.getProducts(Collections.singleton(id));
        return products
                .stream()
                .filter(product -> product.getId().equals(id))
                .findAny();
    }
}
