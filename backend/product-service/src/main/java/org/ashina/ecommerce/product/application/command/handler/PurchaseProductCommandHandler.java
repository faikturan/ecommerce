package org.ashina.ecommerce.product.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.product.application.command.model.PurchaseProductCommand;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PurchaseProductCommandHandler implements CommandHandler<PurchaseProductCommand, Void> {

    private final ProductRepository productRepository;

    @Override
    public Class<?> support() {
        return PurchaseProductCommand.class;
    }

    @Override
    @Transactional
    public Void handle(PurchaseProductCommand command) {
        productRepository.increaseQuantity(command.getProductId(), command.getQuantity());
        return null;
    }
}
