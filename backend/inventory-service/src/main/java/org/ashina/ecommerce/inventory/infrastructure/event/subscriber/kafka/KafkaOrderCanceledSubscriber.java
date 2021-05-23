package org.ashina.ecommerce.inventory.infrastructure.event.subscriber.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.inventory.application.event.handler.OrderCanceledHandler;
import org.ashina.ecommerce.inventory.infrastructure.event.subscriber.OrderCanceledSubscriber;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCanceled;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class KafkaOrderCanceledSubscriber implements OrderCanceledSubscriber {

    private final OrderCanceledHandler orderCanceledHandler;

    @Override
    @StreamListener(OrderCanceledSink.INPUT)
    public void subscribe(@Payload OrderCanceled event, @Headers Map<String, Object> headers) {
        log.info("Received event: {}. Partition: {}. Offset: {}",
                event, headers.get(KafkaHeaders.RECEIVED_PARTITION_ID), headers.get(KafkaHeaders.OFFSET));
        orderCanceledHandler.handle(event);
    }
}
