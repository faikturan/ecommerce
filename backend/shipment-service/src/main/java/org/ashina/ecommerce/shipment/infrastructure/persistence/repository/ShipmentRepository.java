package org.ashina.ecommerce.shipment.infrastructure.persistence.repository;

import org.ashina.ecommerce.shipment.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {
}
