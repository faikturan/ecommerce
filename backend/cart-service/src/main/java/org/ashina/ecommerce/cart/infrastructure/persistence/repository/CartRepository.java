package org.ashina.ecommerce.cart.infrastructure.persistence.repository;

import org.ashina.ecommerce.cart.domain.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByCustomerId(String customerId);

    void deleteByCustomerId(String customerId);
}
