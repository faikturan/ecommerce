package org.ashina.ecommerce.product.application.command.handler;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.product.application.command.model.RefundProductsCommand;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefundProductsCommandHandler implements CommandHandler<RefundProductsCommand, Void> {

    private final ProductRepository productRepository;

    @Override
    public Class<?> support() {
        return RefundProductsCommand.class;
    }

    @Override
    public Void handle(RefundProductsCommand command) {
        command.getLines().forEach(line -> {
            UpdateResult updateResult = productRepository.refundProduct(line.getProductId(), line.getQuantity());
            log.debug("Refunded {} items of product {} done", line.getQuantity(), line.getProductId());
        });
        return null;
    }
}
