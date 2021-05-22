package org.ashina.ecommerce.customer.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.customer.application.command.model.CreateCustomerCommand;
import org.ashina.ecommerce.customer.application.error.ErrorCode;
import org.ashina.ecommerce.customer.domain.Customer;
import org.ashina.ecommerce.customer.infrastructure.uaa.UaaService;
import org.ashina.ecommerce.customer.infrastructure.persistence.CustomerPersistence;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.model.Command;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.ashina.ecommerce.sharedkernel.exception.DomainException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
public class CreateCustomerCommandHandler implements CommandHandler<CreateCustomerCommand> {

    private final CustomerPersistence customerPersistence;
    private final UaaService uaaService;

    @Override
    public Class<? extends Command> support() {
        return CreateCustomerCommand.class;
    }

    @Override
    @Transactional
    public void handle(CreateCustomerCommand command) throws DomainException {
        // Validate email not registered
        Optional<Customer> customerOpt = customerPersistence.findByEmail(command.getEmail());
        if (customerOpt.isPresent()) {
            throw DomainException.of(
                    ErrorCode.EMAIL_REGISTERED,
                    String.format("Email %s already registered", command.getEmail()));
        }

        // Create customer
        Customer customer = newCustomer(command);
        customerPersistence.save(customer);

        // Create account
        uaaService.createAccount(customer, command.getPassword());
    }

    private Customer newCustomer(CreateCustomerCommand command) {
        Customer customer = new Customer(DomainEntityIdentifierGenerator.uuid());
        customer.setFullName(command.getFullName());
        customer.setEmail(command.getEmail());
        return customer;
    }
}
