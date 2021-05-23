package org.ashina.ecommerce.inventory.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.inventory.application.command.model.CreatePurchaseCommand;
import org.ashina.ecommerce.inventory.application.error.ErrorCode;
import org.ashina.ecommerce.inventory.application.error.ServiceException;
import org.ashina.ecommerce.inventory.domain.Purchase;
import org.ashina.ecommerce.inventory.domain.Stock;
import org.ashina.ecommerce.inventory.infrastructure.ecommerce.CatalogService;
import org.ashina.ecommerce.inventory.infrastructure.persistence.PurchasePersistence;
import org.ashina.ecommerce.inventory.infrastructure.persistence.StockPersistence;
import org.ashina.ecommerce.inventory.infrastructure.persistence.SupplierPersistence;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.model.Command;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class CreatePurchaseCommandHandler implements CommandHandler<CreatePurchaseCommand> {

    private final StockPersistence stockPersistence;
    private final SupplierPersistence supplierPersistence;
    private final PurchasePersistence purchasePersistence;
    private final CatalogService catalogService;

    @Override
    public Class<? extends Command> support() {
        return CreatePurchaseCommand.class;
    }

    @Override
    @Transactional
    public void handle(CreatePurchaseCommand command) {
        // Check supplier exist
        supplierPersistence.findById(command.getSupplierId())
                .orElseThrow(() -> ServiceException.of(
                        ErrorCode.SUPPLIER_NOT_FOUND,
                        String.format("Supplier %s not found", command.getSupplierId()),
                        HttpStatus.NOT_FOUND
                ));

        // Check product exist
        if (!catalogService.getProduct(command.getProductId()).isPresent()) {
            throw ServiceException.of(
                    ErrorCode.PRODUCT_NOT_FOUND,
                    String.format("Product %s not found", command.getProductId()),
                    HttpStatus.NOT_FOUND
            );
        }

        // Create purchase
        Purchase purchase = newPurchase(command);
        purchasePersistence.save(purchase);

        // Update stock
        Stock stock = stockPersistence.findByProductId(command.getProductId())
                .orElseGet(() -> new Stock(command.getProductId()));
        stock.setQuantity(stock.getQuantity() + command.getQuantity());
        stockPersistence.save(stock);
    }

    private Purchase newPurchase(CreatePurchaseCommand command) {
        Purchase purchase = new Purchase(DomainEntityIdentifierGenerator.uuid());
        purchase.setSupplierId(command.getSupplierId());
        purchase.setProductId(command.getProductId());
        purchase.setQuantity(command.getQuantity());
        return purchase;
    }

}
