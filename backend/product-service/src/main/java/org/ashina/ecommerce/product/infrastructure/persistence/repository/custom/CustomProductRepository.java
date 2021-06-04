package org.ashina.ecommerce.product.infrastructure.persistence.repository.custom;

import com.mongodb.client.result.UpdateResult;

public interface CustomProductRepository {

    UpdateResult purchaseProduct(String productId, int quantity);

    UpdateResult reserveProduct(String productId, int quantity);

    UpdateResult refundProduct(String productId, int quantity);
}
