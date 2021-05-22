package org.ashina.ecommerce.order.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.order.application.command.UpdateCartLineCommand;
import org.ashina.ecommerce.order.domain.CartLine;
import org.ashina.ecommerce.order.infrastructure.persistence.CartLinePersistence;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.model.Command;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.ashina.ecommerce.sharedkernel.exception.DomainException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
public class UpdateCartLineCommandHandler implements CommandHandler<UpdateCartLineCommand> {

    private final CartLinePersistence cartLinePersistence;

    @Override
    public Class<? extends Command> support() {
        return UpdateCartLineCommand.class;
    }

    @Override
    @Transactional
    public void handle(UpdateCartLineCommand command) throws DomainException {
        // Create cart line if not exist
        Optional<CartLine> cartLineOpt = cartLinePersistence.findByCustomerIdAndProductId(
                command.getCustomerId(), command.getProductId());
        CartLine cartLine;
        if (cartLineOpt.isPresent()) {
            cartLine = cartLineOpt.get();
            cartLine.setQuantity(command.getQuantity());
        } else {
            cartLine = new CartLine(DomainEntityIdentifierGenerator.uuid());
            cartLine.setCustomerId(command.getCustomerId());
            cartLine.setProductId(command.getProductId());
            cartLine.setQuantity(command.getQuantity());
        }

        // Save cart line
        cartLinePersistence.save(cartLine);
    }
}
