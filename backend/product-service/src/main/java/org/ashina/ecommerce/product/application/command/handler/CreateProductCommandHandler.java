package org.ashina.ecommerce.product.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.product.application.command.model.CreateProductCommand;
import org.ashina.ecommerce.product.domain.Product;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.product.infrastructure.search.SearchService;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class CreateProductCommandHandler implements CommandHandler<CreateProductCommand, Void> {

    private final ProductRepository productRepository;
    private final SearchService searchService;

    @Override
    public Class<?> support() {
        return CreateProductCommand.class;
    }

    @Override
    @Transactional
    public Void handle(CreateProductCommand command) {
        // Create product
        Product product = newProduct(command);

        // Save DB
        productRepository.save(product);

        // Index product
        searchService.save(product);

        return null;
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
