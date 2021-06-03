package org.ashina.ecommerce.product.infrastructure.persistence.repository.custom;

import com.mongodb.bulk.BulkWriteResult;

import java.util.Map;

public interface CustomProductRepository {

    void changeQuantity(String productId, int difference, boolean isIncrement);

    BulkWriteResult changeQuantity(Map<String, Integer> productIdAndIncrementMap);
}
