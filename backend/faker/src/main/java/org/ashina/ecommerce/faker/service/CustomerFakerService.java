package org.ashina.ecommerce.faker.service;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.faker.model.customer.Customer;
import org.ashina.ecommerce.faker.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CustomerFakerService implements FakerService {

    private final CustomerRepository customerRepository;
    private final Faker faker;

    public CustomerFakerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.faker = faker();
    }

    private Faker faker() {
        return new Faker();
    }

    @Override
    public void fake() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            Customer customer = create();
            customers.add(customer);
            if (customers.size() % 1000 == 0) {
                customerRepository.saveAll(customers);
                customers.clear();
            }
        }
        if (!customers.isEmpty()) {
            customerRepository.saveAll(customers);
        }
    }

    protected Customer create() {
        return null;
    }
}
