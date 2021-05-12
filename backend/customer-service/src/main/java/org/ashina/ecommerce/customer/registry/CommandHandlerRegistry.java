package org.ashina.ecommerce.customer.registry;

import org.ashina.ecommerce.customer.application.command.handler.CreateAddressBookCommandHandler;
import org.ashina.ecommerce.customer.application.command.handler.CreateCustomerCommandHandler;
import org.ashina.ecommerce.customer.application.command.handler.UpdateAddressBookCommandHandler;
import org.ashina.ecommerce.customer.infrastructure.identity.IdentityService;
import org.ashina.ecommerce.customer.infrastructure.persistence.AddressBookPersistence;
import org.ashina.ecommerce.customer.infrastructure.persistence.CustomerPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerRegistry {

    @Bean
    public CreateCustomerCommandHandler createCustomerCommandHandler(CustomerPersistence customerPersistence,
                                                                     IdentityService identityService) {
        return new CreateCustomerCommandHandler(customerPersistence, identityService);
    }

    @Bean
    public CreateAddressBookCommandHandler createAddressBookCommandHandler(AddressBookPersistence addressBookPersistence) {
        return new CreateAddressBookCommandHandler(addressBookPersistence);
    }

    @Bean
    public UpdateAddressBookCommandHandler updateAddressBookCommandHandler(AddressBookPersistence addressBookPersistence) {
        return new UpdateAddressBookCommandHandler(addressBookPersistence);
    }

}
