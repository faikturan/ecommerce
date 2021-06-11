package org.ashina.ecommerce.cart.application.command.handler;

import lombok.RequiredArgsConstructor;
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
public class UpdateCartLineCommandHandler implements CommandHandler<UpdateCartLineCommand, Void> {

    private final CartRepository cartRepository;

    @Override
    public Class<?> support() {
        return UpdateCartLineCommand.class;
    }

    @Override
    public Void handle(UpdateCartLineCommand command) {
        // Get cart
        Cart cart = cartRepository.findByCustomerId(command.getCustomerId())
                .orElseThrow(() -> ServiceException.of(
                        ErrorCode.CART_NOT_FOUND,
                        String.format("Cart of customer %s not found", command.getCustomerId()),
                        HttpStatus.NOT_FOUND
                ));

        // Update cart line
        Cart.Line line = cart.getLines()
                .stream()
                .filter(it -> it.getProductId().equals(command.getCustomerId()))
                .findAny()
                .orElseThrow(() -> ServiceException.of(
                        ErrorCode.PRODUCT_NOT_FOUND,
                        String.format("Cart of customer %s does not have product %s",
                                command.getCustomerId(), command.getProductId()),
                        HttpStatus.NOT_FOUND
                ));
        line.setQuantity(command.getQuantity());

        // Save cart
        cartRepository.save(cart);

        return null;
    }
}
