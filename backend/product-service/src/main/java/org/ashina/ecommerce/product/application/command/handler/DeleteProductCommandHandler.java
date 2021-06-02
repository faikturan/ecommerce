package org.ashina.ecommerce.product.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.product.application.command.model.DeleteProductCommand;
import org.ashina.ecommerce.product.application.error.ErrorCode;
import org.ashina.ecommerce.product.application.error.ServiceException;
import org.ashina.ecommerce.product.domain.Product;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.product.infrastructure.search.SearchService;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteProductCommandHandler implements CommandHandler<DeleteProductCommand, Void> {

    private final ProductRepository productRepository;
    private final SearchService searchService;

    @Override
    public Class<?> support() {
        return DeleteProductCommand.class;
    }

    @Override
    public Void handle(DeleteProductCommand command) {
        Product product = productRepository.findById(command.getProductId())
                .orElseThrow(() -> new ServiceException(
                        ErrorCode.PRODUCT_NOT_FOUND,
                        String.format("Product %s not found", command.getProductId()),
                        HttpStatus.NOT_FOUND
                ));

        productRepository.delete(product);

        searchService.delete(command.getProductId());

        return null;
    }
}
