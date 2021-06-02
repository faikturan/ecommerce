package org.ashina.ecommerce.order.infrastructure.event.publisher.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.order.infrastructure.event.publisher.OrderCreatedPublisher;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCreated;
import org.springframework.messaging.Message;

@RequiredArgsConstructor
@Slf4j
public class KafkaOrderCreatedPublisher implements OrderCreatedPublisher {

    private final OrderCreatedSource source;

    @Override
    public void publish(OrderCreated event) {
        Message<OrderCreated> message = MessageFactory.newMessage(event);
        source.output().send(message);
        log.info("Publish event {}", event);
    }
}
