package org.ashina.ecommerce.customer.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.customer.application.command.model.CreateAddressBookCommand;
import org.ashina.ecommerce.customer.application.error.ErrorCode;
import org.ashina.ecommerce.customer.application.error.ServiceException;
import org.ashina.ecommerce.customer.domain.AddressBook;
import org.ashina.ecommerce.customer.infrastructure.persistence.AddressBookPersistence;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.model.Command;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
public class CreateAddressBookCommandHandler implements CommandHandler<CreateAddressBookCommand> {

    private final AddressBookPersistence addressBookPersistence;

    @Override
    public Class<? extends Command> support() {
        return CreateAddressBookCommand.class;
    }

    @Override
    @Transactional
    public void handle(CreateAddressBookCommand command) {
        // Check address exist
        Optional<AddressBook> addressOpt = addressBookPersistence.findByCustomerId(command.getCustomerId());
        if (addressOpt.isPresent()) {
            throw new ServiceException(
                    ErrorCode.ADDRESS_EXIST,
                    String.format("Address of customer %s already exists", command.getCustomerId()),
                    HttpStatus.CONFLICT
            );
        }

        // Save address
        AddressBook addressBook = newAddress(command);
        addressBookPersistence.save(addressBook);
    }

    private AddressBook newAddress(CreateAddressBookCommand command) {
        AddressBook addressBook = new AddressBook(command.getCustomerId());
        addressBook.setFullName(command.getFullName());
        addressBook.setPhoneNumber(command.getPhoneNumber());
        addressBook.setAddress(command.getAddress());
        return addressBook;
    }

}
