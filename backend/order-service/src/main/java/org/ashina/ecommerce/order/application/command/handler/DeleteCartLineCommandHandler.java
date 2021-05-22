package org.ashina.ecommerce.order.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.order.application.command.DeleteCartLineCommand;
import org.ashina.ecommerce.order.application.command.UpdateCartLineCommand;
import org.ashina.ecommerce.order.infrastructure.persistence.CartLinePersistence;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.model.Command;
import org.ashina.ecommerce.sharedkernel.exception.DomainException;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class DeleteCartLineCommandHandler implements CommandHandler<DeleteCartLineCommand> {

    private final CartLinePersistence cartLinePersistence;

    @Override
    public Class<? extends Command> support() {
        return UpdateCartLineCommand.class;
    }

    @Override
    @Transactional
    public void handle(DeleteCartLineCommand command) throws DomainException {
        cartLinePersistence.findByCustomerIdAndProductId(command.getCustomerId(), command.getProductId())
                .ifPresent(cartLinePersistence::delete);
    }
}
