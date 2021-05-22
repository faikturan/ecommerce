package org.ashina.ecommerce.order.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.order.application.command.AddProductToCartCommand;
import org.ashina.ecommerce.order.application.error.ErrorCode;
import org.ashina.ecommerce.order.domain.CartLine;
import org.ashina.ecommerce.order.infrastructure.ecommerce.CatalogService;
import org.ashina.ecommerce.order.infrastructure.persistence.CartLinePersistence;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.model.Command;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.ashina.ecommerce.sharedkernel.exception.DomainException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
public class AddProductToCartCommandHandler implements CommandHandler<AddProductToCartCommand> {

    private final CartLinePersistence cartLinePersistence;
    private final CatalogService catalogService;

    @Override
    public Class<? extends Command> support() {
        return AddProductToCartCommand.class;
    }

    @Override
    @Transactional
    public void handle(AddProductToCartCommand command) throws DomainException {
        // Get product
        catalogService.getProduct(command.getProductId())
                .orElseThrow(() -> new DomainException(
                        ErrorCode.PRODUCT_NOT_FOUND,
                        String.format("Product %s not found", command.getProductId())
                ));

        // Create cart line if not exist
        Optional<CartLine> cartLineOpt = cartLinePersistence.findByCustomerIdAndProductId(
                command.getCustomerId(), command.getProductId());
        CartLine cartLine;
        if (cartLineOpt.isPresent()) {
            cartLine = cartLineOpt.get();
            cartLine.setQuantity(cartLine.getQuantity() + command.getQuantity());
        } else {
            cartLine = new CartLine(DomainEntityIdentifierGenerator.uuid());
            cartLine.setCustomerId(command.getCustomerId());
            cartLine.setProductId(command.getProductId());
            cartLine.setQuantity(command.getQuantity());
        }

        // Validate max quantity
        if (cartLine.getQuantity() > CartLine.MAX_QUANTITY) {
            throw new DomainException(
                    ErrorCode.LINE_QUANTITY_INVALID,
                    String.format("Quantity must be less than or equal %d", CartLine.MAX_QUANTITY)
            );
        }

        // Save cart line
        cartLinePersistence.save(cartLine);
    }
}
