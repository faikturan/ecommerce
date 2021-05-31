package org.ashina.ecommerce.inventory.infrastructure.persistence.repository;

import org.ashina.ecommerce.inventory.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

    Optional<Stock> findByProductId(String productId);

    List<Stock> findByProductIdIn(Collection<String> productIds);
}
