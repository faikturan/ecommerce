package org.ashina.ecommerce.catalog.infrastructure.search;

import org.ashina.ecommerce.catalog.domain.Product;

import java.util.List;

public interface SearchProductService {

    // Return ID of results
    List<String> search(String keyword, int page, int size);

    void save(Product product);
}
