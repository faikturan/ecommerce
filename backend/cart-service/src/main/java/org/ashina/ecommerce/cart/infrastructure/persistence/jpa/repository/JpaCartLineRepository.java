package org.ashina.ecommerce.cart.infrastructure.persistence.jpa.repository;

import org.ashina.ecommerce.cart.infrastructure.persistence.jpa.entity.JpaCartLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCartLineRepository extends JpaRepository<JpaCartLine, String> {

    List<JpaCartLine> findByCustomerId(String customerId);

    Optional<JpaCartLine> findByCustomerIdAndProductId(String customerId, String productId);

}
