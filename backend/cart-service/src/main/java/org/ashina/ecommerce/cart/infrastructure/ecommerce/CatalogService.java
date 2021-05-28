package org.ashina.ecommerce.cart.infrastructure.ecommerce;

import org.ashina.ecommerce.cart.infrastructure.ecommerce.model.Product;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CatalogService {

    Map<String, Product> getProducts(Collection<String> ids);

    Optional<Product> getProduct(String id);
}
