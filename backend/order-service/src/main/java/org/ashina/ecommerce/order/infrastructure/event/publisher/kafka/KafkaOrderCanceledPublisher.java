package org.ashina.ecommerce.order.infrastructure.event.publisher.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.order.infrastructure.event.publisher.OrderCanceledPublisher;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCanceled;
import org.springframework.messaging.Message;

@RequiredArgsConstructor
@Slf4j
public class KafkaOrderCanceledPublisher implements OrderCanceledPublisher {

    private final OrderCanceledSource source;

    @Override
    public void publish(OrderCanceled event) {
        Message<OrderCanceled> message = MessageFactory.newMessage(event);
        source.output().send(message);
        log.info("Publish event {}", event);
    }
}
