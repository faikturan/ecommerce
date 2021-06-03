package org.ashina.ecommerce.order.infrastructure.persistence.repository;

import org.ashina.ecommerce.order.domain.FulfillmentTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FulfillmentTransactionRepository extends MongoRepository<FulfillmentTransaction, String> {
}
