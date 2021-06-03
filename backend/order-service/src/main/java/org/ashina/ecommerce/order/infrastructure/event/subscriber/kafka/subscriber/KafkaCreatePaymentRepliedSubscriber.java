package org.ashina.ecommerce.order.infrastructure.event.subscriber.kafka.subscriber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.order.application.event.handler.CreatePaymentRepliedHandler;
import org.ashina.ecommerce.order.infrastructure.event.subscriber.CreatePaymentRepliedSubscriber;
import org.ashina.ecommerce.order.infrastructure.event.subscriber.kafka.sink.CreatePaymentRepliedSink;
import org.ashina.ecommerce.sharedkernel.event.model.payment.CreatePaymentReplied;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class KafkaCreatePaymentRepliedSubscriber implements CreatePaymentRepliedSubscriber {

    private final CreatePaymentRepliedHandler handler;

    @Override
    @StreamListener(CreatePaymentRepliedSink.INPUT)
    public void subscribe(@Payload CreatePaymentReplied event, @Headers Map<String, Object> headers) {
        log.info("Received event: {}. Partition: {}. Offset: {}",
                event, headers.get(KafkaHeaders.RECEIVED_PARTITION_ID), headers.get(KafkaHeaders.OFFSET));
        handler.handle(event);
    }
}
