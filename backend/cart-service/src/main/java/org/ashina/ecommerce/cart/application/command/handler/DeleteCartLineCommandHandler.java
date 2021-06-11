package org.ashina.ecommerce.cart.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.cart.application.command.DeleteCartLineCommand;
import org.ashina.ecommerce.cart.application.command.UpdateCartLineCommand;
import org.ashina.ecommerce.cart.application.error.ErrorCode;
import org.ashina.ecommerce.cart.application.error.ServiceException;
import org.ashina.ecommerce.cart.domain.Cart;
import org.ashina.ecommerce.cart.infrastructure.persistence.repository.CartRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCartLineCommandHandler implements CommandHandler<DeleteCartLineCommand, Void> {

    private final CartRepository cartRepository;

    @Override
    public Class<?> support() {
        return UpdateCartLineCommand.class;
    }

    @Override
    public Void handle(DeleteCartLineCommand command) {
        // Get cart
        Cart cart = cartRepository.findByCustomerId(command.getCustomerId())
                .orElseThrow(() -> ServiceException.of(
                        ErrorCode.CART_NOT_FOUND,
                        String.format("Cart of customer %s not found", command.getCustomerId()),
                        HttpStatus.NOT_FOUND
                ));

        // Remove cart line
        cart.getLines().removeIf(line -> line.getProductId().equals(command.getProductId()));

        // Save
        cartRepository.save(cart);

        return null;
    }
}
