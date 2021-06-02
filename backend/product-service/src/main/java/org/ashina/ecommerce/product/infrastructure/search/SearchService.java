package org.ashina.ecommerce.product.infrastructure.search;

import org.ashina.ecommerce.product.domain.Product;

import java.util.List;

public interface SearchService {

    // Return ID of results
    List<String> search(String keyword, int page, int size);

    void save(Product product);

    void delete(String productId);
}
