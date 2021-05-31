package org.ashina.ecommerce.catalog.infrastructure.persistence.repository;

import org.ashina.ecommerce.catalog.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
