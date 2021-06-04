package org.ashina.ecommerce.product.application.command.handler;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.product.application.command.model.ReserveProductsCommand;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefundProductsCommandHandler implements CommandHandler<ReserveProductsCommand, Void> {

    private final ProductRepository productRepository;

    @Override
    public Class<?> support() {
        return ReserveProductsCommand.class;
    }

    @Override
    @Transactional
    public Void handle(ReserveProductsCommand command) {
        command.getLines().forEach(line -> {
            UpdateResult updateResult = productRepository.refundProduct(line.getProductId(), line.getQuantity());
            log.debug("Refund {}/{} items of product {}",
                    updateResult.getModifiedCount(), line.getQuantity(), line.getProductId());
        });
        return null;
    }
}
