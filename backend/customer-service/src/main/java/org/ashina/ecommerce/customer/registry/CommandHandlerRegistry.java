package org.ashina.ecommerce.customer.registry;

import org.ashina.ecommerce.customer.application.command.handler.CreateCustomerCommandHandler;
import org.ashina.ecommerce.customer.infrastructure.persistence.repository.CustomerRepository;
import org.ashina.ecommerce.customer.infrastructure.uaa.UaaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerRegistry {

    @Bean
    public CreateCustomerCommandHandler createCustomerCommandHandler(CustomerRepository customerRepository,
                                                                     UaaService uaaService) {
        return new CreateCustomerCommandHandler(customerRepository, uaaService);
    }
}
