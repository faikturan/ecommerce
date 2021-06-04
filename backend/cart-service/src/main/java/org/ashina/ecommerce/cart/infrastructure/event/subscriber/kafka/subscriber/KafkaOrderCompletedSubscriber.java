package org.ashina.ecommerce.cart.infrastructure.event.subscriber.kafka.subscriber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.cart.application.event.handler.OrderCompletedHandler;
import org.ashina.ecommerce.cart.infrastructure.event.subscriber.OrderCompletedSubscriber;
import org.ashina.ecommerce.cart.infrastructure.event.subscriber.kafka.sink.OrderCompletedSink;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCompleted;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class KafkaOrderCompletedSubscriber implements OrderCompletedSubscriber {

    private final OrderCompletedHandler handler;

    @Override
    @StreamListener(OrderCompletedSink.INPUT)
    public void subscribe(@Payload OrderCompleted event, @Headers Map<String, Object> headers) {
        log.info("Received event: {}. Partition: {}. Offset: {}",
                event, headers.get(KafkaHeaders.RECEIVED_PARTITION_ID), headers.get(KafkaHeaders.OFFSET));
        handler.handle(event);
    }
}
