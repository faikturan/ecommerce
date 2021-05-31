package org.ashina.ecommerce.catalog.infrastructure.persistence.repository.custom;

import java.util.Map;

public interface CustomProductRepository {

    void increaseQuantity(String productId, int increment);

    void increaseQuantity(Map<String, Integer> productIdAndIncrementMap);
}
