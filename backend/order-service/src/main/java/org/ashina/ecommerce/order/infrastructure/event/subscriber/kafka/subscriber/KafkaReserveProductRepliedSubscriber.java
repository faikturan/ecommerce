package org.ashina.ecommerce.order.infrastructure.event.subscriber.kafka.subscriber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.order.application.event.handler.ReserveProductRepliedHandler;
import org.ashina.ecommerce.order.infrastructure.event.subscriber.ReserveProductRepliedSubscriber;
import org.ashina.ecommerce.order.infrastructure.event.subscriber.kafka.sink.ReserveProductRepliedSink;
import org.ashina.ecommerce.sharedkernel.event.model.product.ReserveProductReplied;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class KafkaReserveProductRepliedSubscriber implements ReserveProductRepliedSubscriber {

    private final ReserveProductRepliedHandler handler;

    @Override
    @StreamListener(ReserveProductRepliedSink.INPUT)
    public void subscribe(@Payload ReserveProductReplied event, @Headers Map<String, Object> headers) {
        log.info("Received event: {}. Partition: {}. Offset: {}",
                event, headers.get(KafkaHeaders.RECEIVED_PARTITION_ID), headers.get(KafkaHeaders.OFFSET));
        handler.handle(event);
    }
}
