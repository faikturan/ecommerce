package org.ashina.ecommerce.faker.service;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.faker.model.customer.Customer;
import org.ashina.ecommerce.faker.repository.customer.CustomerRepository;
import org.ashina.ecommerce.faker.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerFakerService implements FakerService {

    private final Faker faker;
    private final CustomerRepository customerRepository;

    private static final int BATCH_SIZE = 100;

    @Override
    public void fake(int count) {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Customer customer = fakeCustomer(i + 1);
            customers.add(customer);
            if (customers.size() % BATCH_SIZE == 0) {
                customerRepository.saveAll(customers);
                log.debug("Fake {} customers done", BATCH_SIZE);
                customers.clear();
            }
        }
        if (!customers.isEmpty()) {
            customerRepository.saveAll(customers);
        }
    }

    private Customer fakeCustomer(int i) {
        Customer customer = new Customer();
        customer.setLastName(faker.name().lastName());
        customer.setFirstName(faker.name().firstName());
        customer.setEmail(String.format("%s.%s+%d@gmail.com",
                customer.getLastName().toLowerCase(), customer.getFirstName().toLowerCase(), i));
        return customer;
    }
}
