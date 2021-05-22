package org.ashina.ecommerce.inventory.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.inventory.application.command.model.CreateSupplierCommand;
import org.ashina.ecommerce.inventory.domain.Supplier;
import org.ashina.ecommerce.inventory.infrastructure.persistence.SupplierPersistence;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.model.Command;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.ashina.ecommerce.sharedkernel.exception.DomainException;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class CreateSupplierCommandHandler implements CommandHandler<CreateSupplierCommand> {

    private final SupplierPersistence supplierPersistence;

    @Override
    public Class<? extends Command> support() {
        return CreateSupplierCommand.class;
    }

    @Override
    @Transactional
    public void handle(CreateSupplierCommand command) throws DomainException {
        // Create supplier
        Supplier supplier = newSupplier(command);
        supplierPersistence.save(supplier);
    }

    private Supplier newSupplier(CreateSupplierCommand command) {
        Supplier supplier = new Supplier(DomainEntityIdentifierGenerator.uuid());
        supplier.setFullName(command.getFullName());
        return supplier;
    }

}
