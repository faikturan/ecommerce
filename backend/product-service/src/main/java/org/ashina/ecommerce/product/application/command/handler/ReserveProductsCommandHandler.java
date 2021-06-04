package org.ashina.ecommerce.product.application.command.handler;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.product.application.command.model.ReserveProductsCommand;
import org.ashina.ecommerce.product.application.error.ErrorCode;
import org.ashina.ecommerce.product.application.error.ServiceException;
import org.ashina.ecommerce.product.infrastructure.persistence.repository.ProductRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReserveProductsCommandHandler implements CommandHandler<ReserveProductsCommand, Void> {

    private final ProductRepository productRepository;

    @Override
    public Class<?> support() {
        return ReserveProductsCommand.class;
    }

    @Override
    @Transactional
    public Void handle(ReserveProductsCommand command) {
        Map<String, Integer> reservedProducts = new HashMap<>();
        for (ReserveProductsCommand.Line line : command.getLines()) {
            UpdateResult updateResult = productRepository.reserveProduct(line.getProductId(), line.getQuantity());
            log.debug("Reserve {}/{} items of product {}",
                    updateResult.getModifiedCount(), line.getQuantity(), line.getProductId());
            if (updateResult.getModifiedCount() == 0) {
                log.warn("Product {} is out of stock", line.getProductId());
                refundProducts(reservedProducts);
                throw ServiceException.of(
                        ErrorCode.PRODUCT_OUT_OF_STOCK,
                        String.format("Product %s is out of stock", line.getProductId()),
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
            } else {
                reservedProducts.put(line.getProductId(), line.getQuantity());
            }
        }
        return null;
    }

    private void refundProducts(Map<String, Integer> reservedProducts) {
        reservedProducts.forEach(productRepository::refundProduct);
    }
}
