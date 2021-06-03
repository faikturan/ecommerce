package org.ashina.ecommerce.payment.infrastructure.event.subscriber.kafka.subscriber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.payment.application.event.handler.CreatePaymentRequestedHandler;
import org.ashina.ecommerce.payment.infrastructure.event.subscriber.CreatePaymentRequestedSubscriber;
import org.ashina.ecommerce.payment.infrastructure.event.subscriber.kafka.sink.CreatePaymentRequestedSink;
import org.ashina.ecommerce.sharedkernel.event.model.payment.CreatePaymentRequested;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class KafkaCreatePaymentRequestedSubscriber implements CreatePaymentRequestedSubscriber {

    private final CreatePaymentRequestedHandler handler;

    @Override
    @StreamListener(CreatePaymentRequestedSink.INPUT)
    public void subscribe(@Payload CreatePaymentRequested event, @Headers Map<String, Object> headers) {
        log.info("Received event: {}. Partition: {}. Offset: {}",
                event, headers.get(KafkaHeaders.RECEIVED_PARTITION_ID), headers.get(KafkaHeaders.OFFSET));
        handler.handle(event);
    }
}
