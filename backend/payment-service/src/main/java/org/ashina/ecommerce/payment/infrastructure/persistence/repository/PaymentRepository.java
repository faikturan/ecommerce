package org.ashina.ecommerce.payment.infrastructure.persistence.repository;

import org.ashina.ecommerce.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
}
