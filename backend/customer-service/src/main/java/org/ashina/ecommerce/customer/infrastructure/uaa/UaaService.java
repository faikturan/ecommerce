package org.ashina.ecommerce.customer.infrastructure.uaa;

import org.ashina.ecommerce.customer.domain.Customer;

public interface UaaService {

    void createAccount(Customer customer, String password);
}
