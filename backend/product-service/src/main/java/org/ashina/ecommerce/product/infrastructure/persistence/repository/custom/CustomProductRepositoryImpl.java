package org.ashina.ecommerce.product.infrastructure.persistence.repository.custom;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.product.domain.Product;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final MongoTemplate mongoTemplate;

    private final String FIELD_ID = "_id";
    private final String FIELD_QUANTITY = "quantity";

    @Override
    public UpdateResult purchaseProduct(String productId, int quantity) {
        Criteria criteria = Criteria.where(FIELD_ID).is(productId);
        Query query = Query.query(criteria);

        Update update = new Update();
        update.inc(FIELD_QUANTITY, quantity);

        return mongoTemplate.updateFirst(query, update, Product.class);
    }

    @Override
    public UpdateResult reserveProduct(String productId, int quantity) {
        Criteria criteria = Criteria
                .where(FIELD_ID).is(productId)
                .and(FIELD_QUANTITY).gte(quantity);
        Query query = Query.query(criteria);

        Update update = new Update();
        update.inc(FIELD_QUANTITY, quantity * -1);

        return mongoTemplate.updateFirst(query, update, Product.class);
    }

    @Override
    public UpdateResult refundProduct(String productId, int quantity) {
        Criteria criteria = Criteria.where(FIELD_ID).is(productId);
        Query query = Query.query(criteria);

        Update update = new Update();
        update.inc(FIELD_QUANTITY, quantity);

        return mongoTemplate.updateFirst(query, update, Product.class);
    }
}
