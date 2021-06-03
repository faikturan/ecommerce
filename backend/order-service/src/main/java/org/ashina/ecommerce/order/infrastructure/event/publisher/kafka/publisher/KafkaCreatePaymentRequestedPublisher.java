package org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.order.infrastructure.event.publisher.CreatePaymentRequestedPublisher;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.MessageFactory;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.source.CreatePaymentRequestedSource;
import org.ashina.ecommerce.sharedkernel.event.model.payment.CreatePaymentRequested;
import org.springframework.messaging.Message;

@RequiredArgsConstructor
@Slf4j
public class KafkaCreatePaymentRequestedPublisher implements CreatePaymentRequestedPublisher {

    private final CreatePaymentRequestedSource source;

    @Override
    public void publish(CreatePaymentRequested event) {
        Message<CreatePaymentRequested> message = MessageFactory.newMessage(event);
        source.output().send(message);
        log.info("Publish event {}", event);
    }
}
