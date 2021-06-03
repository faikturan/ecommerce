package org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.order.infrastructure.event.publisher.ReserveProductRequestedPublisher;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.MessageFactory;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.source.ReserveProductRequestedSource;
import org.ashina.ecommerce.sharedkernel.event.model.product.ReserveProductRequested;
import org.springframework.messaging.Message;

@RequiredArgsConstructor
@Slf4j
public class KafkaReserveProductRequestedPublisher implements ReserveProductRequestedPublisher {

    private final ReserveProductRequestedSource source;

    @Override
    public void publish(ReserveProductRequested event) {
        Message<ReserveProductRequested> message = MessageFactory.newMessage(event);
        source.output().send(message);
        log.info("Publish event {}", event);
    }
}
