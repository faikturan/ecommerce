package org.ashina.ecommerce.faker.repository.customer;

import org.ashina.ecommerce.faker.model.customer.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
}
