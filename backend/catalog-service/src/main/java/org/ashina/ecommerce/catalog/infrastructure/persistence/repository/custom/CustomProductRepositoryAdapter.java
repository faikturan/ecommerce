package org.ashina.ecommerce.catalog.infrastructure.persistence.repository.custom;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.catalog.domain.Product;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryAdapter implements CustomProductRepository {

    private final MongoTemplate mongoTemplate;

    private final String FIELD_PRODUCT_ID = "productId";
    private final String FIELD_QUANTITY = "quantity";

    @Override
    public void increaseQuantity(String productId, int increment) {
        Query query = Query.query(Criteria.where(FIELD_PRODUCT_ID).is(productId));
        Update update = new Update();
        update.inc(FIELD_QUANTITY, increment);
        mongoTemplate.updateFirst(query, update, Product.class);
    }

    @Override
    public void increaseQuantity(Map<String, Integer> productIdAndIncrementMap) {
        List<Pair<Query, Update>> pairs = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : productIdAndIncrementMap.entrySet()) {
            Query query = Query.query(Criteria.where(FIELD_PRODUCT_ID).is(entry.getKey()));
            Update update = new Update();
            update.inc(FIELD_QUANTITY, entry.getValue());
            pairs.add(Pair.of(query, update));
        }
        mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Product.class)
                .updateMulti(pairs);
    }
}
