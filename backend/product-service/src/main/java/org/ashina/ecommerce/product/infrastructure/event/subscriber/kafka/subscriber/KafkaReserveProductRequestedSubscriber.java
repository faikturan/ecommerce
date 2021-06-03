package org.ashina.ecommerce.product.infrastructure.event.subscriber.kafka.subscriber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.product.application.event.handler.ReserveProductRequestedHandler;
import org.ashina.ecommerce.product.infrastructure.event.subscriber.ReserveProductRequestedSubscriber;
import org.ashina.ecommerce.product.infrastructure.event.subscriber.kafka.sink.ReserveProductRequestedSink;
import org.ashina.ecommerce.sharedkernel.event.model.product.ReserveProductRequested;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class KafkaReserveProductRequestedSubscriber implements ReserveProductRequestedSubscriber {

    private final ReserveProductRequestedHandler handler;

    @Override
    @StreamListener(ReserveProductRequestedSink.INPUT)
    public void subscribe(@Payload ReserveProductRequested event, @Headers Map<String, Object> headers) {
        log.info("Received event: {}. Partition: {}. Offset: {}",
                event, headers.get(KafkaHeaders.RECEIVED_PARTITION_ID), headers.get(KafkaHeaders.OFFSET));
        handler.handle(event);
    }
}
