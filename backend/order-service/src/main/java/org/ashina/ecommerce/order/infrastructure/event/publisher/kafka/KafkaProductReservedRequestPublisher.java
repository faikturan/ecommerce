package org.ashina.ecommerce.order.infrastructure.event.publisher.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.order.infrastructure.event.publisher.ProductReservedRequestPublisher;
import org.ashina.ecommerce.sharedkernel.event.model.product.ProductReservedRequest;
import org.springframework.messaging.Message;

@RequiredArgsConstructor
@Slf4j
public class KafkaProductReservedRequestPublisher implements ProductReservedRequestPublisher {

    private final ProductReservedRequestSource source;

    @Override
    public void publish(ProductReservedRequest event) {
        Message<ProductReservedRequest> message = MessageFactory.newMessage(event);
        source.output().send(message);
        log.info("Publish event {}", event);
    }
}
