package org.ashina.ecommerce.inventory.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.inventory.application.command.model.PurchaseProductCommand;
import org.ashina.ecommerce.inventory.application.error.ErrorCode;
import org.ashina.ecommerce.inventory.application.error.ServiceException;
import org.ashina.ecommerce.inventory.domain.Stock;
import org.ashina.ecommerce.inventory.infrastructure.ecommerce.CatalogService;
import org.ashina.ecommerce.inventory.infrastructure.persistence.repository.StockRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class PurchaseProductCommandHandler implements CommandHandler<PurchaseProductCommand, Void> {

    private final StockRepository stockRepository;
    private final CatalogService catalogService;

    @Override
    public Class<?> support() {
        return PurchaseProductCommand.class;
    }

    @Override
    @Transactional
    public Void handle(PurchaseProductCommand command) {
        // Check product exist
        if (!catalogService.getProduct(command.getProductId()).isPresent()) {
            throw ServiceException.of(
                    ErrorCode.PRODUCT_NOT_FOUND,
                    String.format("Product %s not found", command.getProductId()),
                    HttpStatus.NOT_FOUND
            );
        }

        // Update stock quantity
        Stock stock = stockRepository.findByProductId(command.getProductId())
                .orElseGet(() -> {
                    Stock newStock = new Stock(DomainEntityIdentifierGenerator.uuid());
                    newStock.setProductId(command.getProductId());
                    return newStock;
                });
        stock.setQuantity(stock.getQuantity() + command.getQuantity());

        // Save stock
        stockRepository.save(stock);

        return null;
    }
}
