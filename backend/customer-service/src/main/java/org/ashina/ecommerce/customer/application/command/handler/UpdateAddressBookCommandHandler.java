package org.ashina.ecommerce.customer.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.customer.application.command.model.UpdateAddressBookCommand;
import org.ashina.ecommerce.customer.application.error.ErrorCode;
import org.ashina.ecommerce.customer.application.error.ServiceException;
import org.ashina.ecommerce.customer.domain.AddressBook;
import org.ashina.ecommerce.customer.infrastructure.persistence.AddressBookPersistence;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.model.Command;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class UpdateAddressBookCommandHandler implements CommandHandler<UpdateAddressBookCommand> {

    private final AddressBookPersistence addressBookPersistence;

    @Override
    public Class<? extends Command> support() {
        return UpdateAddressBookCommand.class;
    }

    @Override
    @Transactional
    public void handle(UpdateAddressBookCommand command) {
        // Find address
        AddressBook addressBook = addressBookPersistence.findByCustomerId(command.getCustomerId())
                .orElseThrow(() -> new ServiceException(
                        ErrorCode.ADDRESS_NOT_FOUND,
                        String.format("Address book of customer %s not found", command.getCustomerId()),
                        HttpStatus.NOT_FOUND
                ));

        // Save address
        updateAddress(addressBook, command);
        addressBookPersistence.save(addressBook);
    }

    private void updateAddress(AddressBook addressBook, UpdateAddressBookCommand command) {
        addressBook.setFullName(command.getFullName());
        addressBook.setPhoneNumber(command.getPhoneNumber());
        addressBook.setAddress(command.getAddress());
    }

}
