package org.ashina.ecommerce.customer.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.customer.application.command.model.CreateCustomerCommand;
import org.ashina.ecommerce.customer.application.error.ErrorCode;
import org.ashina.ecommerce.customer.application.error.ServiceException;
import org.ashina.ecommerce.customer.domain.Customer;
import org.ashina.ecommerce.customer.infrastructure.persistence.repository.CustomerRepository;
import org.ashina.ecommerce.customer.infrastructure.uaa.UaaService;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
public class CreateCustomerCommandHandler implements CommandHandler<CreateCustomerCommand, Void> {

    private final CustomerRepository customerRepository;
    private final UaaService uaaService;

    @Override
    public Class<?> support() {
        return CreateCustomerCommand.class;
    }

    @Override
    @Transactional
    public Void handle(CreateCustomerCommand command) {
        // Validate email not registered
        Optional<Customer> customerOpt = customerRepository.findByEmail(command.getEmail());
        if (customerOpt.isPresent()) {
            throw ServiceException.of(
                    ErrorCode.EMAIL_REGISTERED,
                    String.format("Email %s already registered", command.getEmail()),
                    HttpStatus.CONFLICT
            );
        }

        // Create customer
        Customer customer = newCustomer(command);
        customerRepository.save(customer);

        // Create account
        uaaService.createAccount(customer, command.getPassword());

        return null;
    }

    private Customer newCustomer(CreateCustomerCommand command) {
        Customer customer = new Customer();
        customer.setId(DomainEntityIdentifierGenerator.uuid());
        customer.setFullName(command.getFullName());
        customer.setEmail(command.getEmail());
        return customer;
    }
}
