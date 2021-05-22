package org.ashina.ecommerce.catalog.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.catalog.application.command.model.CreateProductCommand;
import org.ashina.ecommerce.catalog.domain.Product;
import org.ashina.ecommerce.catalog.infrastructure.persistence.ProductPersistence;
import org.ashina.ecommerce.catalog.infrastructure.search.ProductSearch;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.model.Command;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.ashina.ecommerce.sharedkernel.exception.DomainException;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class CreateProductCommandHandler implements CommandHandler<CreateProductCommand> {

    private final ProductPersistence productPersistence;
    private final ProductSearch productSearch;

    @Override
    public Class<? extends Command> support() {
        return CreateProductCommand.class;
    }

    @Override
    @Transactional
    public void handle(CreateProductCommand command) throws DomainException {
        // Create product
        Product product = newProduct(command);

        // Save DB
        productPersistence.save(product);

        // Index product
        productSearch.save(product);
    }

    private Product newProduct(CreateProductCommand command) {
        Product product = new Product(DomainEntityIdentifierGenerator.uuid());
        product.setName(command.getName());
        product.setDescription(command.getDescription());
        product.setImage(command.getImage());
        product.setPrice(command.getPrice());
        return product;
    }
}
