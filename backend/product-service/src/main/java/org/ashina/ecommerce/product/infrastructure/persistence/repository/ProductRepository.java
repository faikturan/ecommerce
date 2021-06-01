package org.ashina.ecommerce.product.infrastructure.persistence.repository;

import org.ashina.ecommerce.product.domain.Product;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.custom.CustomProductRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>, CustomProductRepository {
}
