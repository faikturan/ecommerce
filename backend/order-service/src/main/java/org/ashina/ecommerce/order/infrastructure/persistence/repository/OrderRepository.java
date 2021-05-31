package org.ashina.ecommerce.order.infrastructure.persistence.repository;

import org.ashina.ecommerce.order.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}
