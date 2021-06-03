package org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.order.infrastructure.event.publisher.OrderCompletedPublisher;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.MessageFactory;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.source.OrderCompletedSource;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCompleted;
import org.springframework.messaging.Message;

@RequiredArgsConstructor
@Slf4j
public class KafkaOrderCompletedPublisher implements OrderCompletedPublisher {

    private final OrderCompletedSource source;

    @Override
    public void publish(OrderCompleted event) {
        Message<OrderCompleted> message = MessageFactory.newMessage(event);
        source.output().send(message);
        log.info("Publish event {}", event);
    }
}
