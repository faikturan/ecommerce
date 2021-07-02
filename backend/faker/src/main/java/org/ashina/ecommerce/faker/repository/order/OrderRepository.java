package org.ashina.ecommerce.faker.repository.order;

import org.ashina.ecommerce.faker.model.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}
