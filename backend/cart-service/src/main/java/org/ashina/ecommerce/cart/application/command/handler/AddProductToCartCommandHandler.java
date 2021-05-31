package org.ashina.ecommerce.cart.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.cart.application.command.AddProductToCartCommand;
import org.ashina.ecommerce.cart.application.error.ErrorCode;
import org.ashina.ecommerce.cart.application.error.ServiceException;
import org.ashina.ecommerce.cart.domain.Cart;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.CatalogService;
import org.ashina.ecommerce.cart.infrastructure.persistence.repository.CartRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
public class AddProductToCartCommandHandler implements CommandHandler<AddProductToCartCommand, Void> {

    private final CartRepository cartRepository;
    private final CatalogService catalogService;

    @Override
    public Class<?> support() {
        return AddProductToCartCommand.class;
    }

    @Override
    @Transactional
    public Void handle(AddProductToCartCommand command) {
        // Get cart
        Cart cart = cartRepository.findByCustomerId(command.getCustomerId())
                .orElseGet(() -> {
                    // Create new cart if not exist
                    Cart newCart = new Cart(DomainEntityIdentifierGenerator.uuid());
                    newCart.setCustomerId(command.getCustomerId());
                    return newCart;
                });

        // Get product
        catalogService.getProduct(command.getProductId())
                .orElseThrow(() -> ServiceException.of(
                        ErrorCode.PRODUCT_NOT_FOUND,
                        String.format("Product %s not found", command.getProductId()),
                        HttpStatus.NOT_FOUND
                ));

        // Create new cart line if not exist
        Optional<Cart.Line> lineOpt = cart.getLines()
                .stream()
                .filter(line -> line.getProductId().equals(command.getCustomerId()))
                .findAny();
        Cart.Line line;
        if (lineOpt.isPresent()) {
            line = lineOpt.get();
            line.setQuantity(line.getQuantity() + command.getQuantity());
        } else {
            line = new Cart.Line();
            line.setProductId(command.getProductId());
            line.setQuantity(command.getQuantity());
            cart.addLine(line);
        }

        // Validate max quantity of cart line
        if (line.getQuantity() > Cart.Line.MAX_QUANTITY) {
            throw ServiceException.of(
                    ErrorCode.LINE_QUANTITY_INVALID,
                    String.format("Quantity must be less than or equal %d", Cart.Line.MAX_QUANTITY),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Save cart
        cartRepository.save(cart);

        return null;
    }
}