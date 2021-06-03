package org.ashina.ecommerce.product.infrastructure.event.publisher.kafka.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.product.infrastructure.event.publisher.ReserveProductRepliedPublisher;
import org.ashina.ecommerce.product.infrastructure.event.publisher.kafka.MessageFactory;
import org.ashina.ecommerce.product.infrastructure.event.publisher.kafka.source.ReserveProductRepliedSource;
import org.ashina.ecommerce.sharedkernel.event.model.product.ReserveProductReplied;
import org.springframework.messaging.Message;

@RequiredArgsConstructor
@Slf4j
public class KafkaReserveProductRepliedPublisher implements ReserveProductRepliedPublisher {

    private final ReserveProductRepliedSource source;

    @Override
    public void publish(ReserveProductReplied event) {
        Message<ReserveProductReplied> message = MessageFactory.newMessage(event);
        source.output().send(message);
        log.info("Publish event {}", event);
    }
}
