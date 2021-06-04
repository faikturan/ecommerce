package org.ashina.ecommerce.payment.infrastructure.persistence.repository;

import org.ashina.ecommerce.payment.domain.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
}
