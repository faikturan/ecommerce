package org.ashina.ecommerce.catalog.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.catalog.application.command.model.PurchaseProductCommand;
import org.ashina.ecommerce.catalog.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.springframework.transaction.annotation.Transactional;

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
