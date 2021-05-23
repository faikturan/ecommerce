package org.ashina.ecommerce.inventory.infrastructure.event.subscriber.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.inventory.application.event.handler.OrderCreatedHandler;
import org.ashina.ecommerce.inventory.infrastructure.event.subscriber.OrderCreatedSubscriber;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCreated;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class KafkaOrderCreatedSubscriber implements OrderCreatedSubscriber {

    private final OrderCreatedHandler orderCreatedHandler;

    @Override
    @StreamListener(OrderCreatedSink.INPUT)
    public void subscribe(@Payload OrderCreated event, @Headers Map<String, Object> headers) {
        log.info("Received event: {}. Partition: {}. Offset: {}",
                event, headers.get(KafkaHeaders.RECEIVED_PARTITION_ID), headers.get(KafkaHeaders.OFFSET));
        orderCreatedHandler.handle(event);
    }
}
