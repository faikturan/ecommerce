package org.ashina.ecommerce.inventory.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.inventory.application.command.model.UpdateSupplierCommand;
import org.ashina.ecommerce.inventory.application.error.ErrorCode;
import org.ashina.ecommerce.inventory.application.error.ServiceException;
import org.ashina.ecommerce.inventory.domain.Supplier;
import org.ashina.ecommerce.inventory.infrastructure.persistence.SupplierPersistence;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.model.Command;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class UpdateSupplierCommandHandler implements CommandHandler<UpdateSupplierCommand> {

    private final SupplierPersistence supplierPersistence;

    @Override
    public Class<? extends Command> support() {
        return UpdateSupplierCommand.class;
    }

    @Override
    @Transactional
    public void handle(UpdateSupplierCommand command) {
        // Find supplier
        Supplier supplier = supplierPersistence.findById(command.getId())
                .orElseThrow(() -> ServiceException.of(
                        ErrorCode.SUPPLIER_NOT_FOUND,
                        String.format("Supplier %s not found", command.getId())
                ));

        // Save supplier
        updateSupplier(supplier, command);
        supplierPersistence.save(supplier);
    }

    private void updateSupplier(Supplier supplier, UpdateSupplierCommand command) {
        supplier.setFullName(command.getFullName());
        supplier.setPhoneNumber(command.getPhoneNumber());
    }

}
