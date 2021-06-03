package org.ashina.ecommerce.payment.infrastructure.event.publisher.kafka.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.payment.infrastructure.event.publisher.CreatePaymentRepliedPublisher;
import org.ashina.ecommerce.payment.infrastructure.event.publisher.kafka.MessageFactory;
import org.ashina.ecommerce.payment.infrastructure.event.publisher.kafka.source.CreatePaymentRepliedSource;
import org.ashina.ecommerce.sharedkernel.event.model.payment.CreatePaymentReplied;
import org.springframework.messaging.Message;

@RequiredArgsConstructor
@Slf4j
public class KafkaCreatePaymentRepliedPublisher implements CreatePaymentRepliedPublisher {

    private final CreatePaymentRepliedSource source;

    @Override
    public void publish(CreatePaymentReplied event) {
        Message<CreatePaymentReplied> message = MessageFactory.newMessage(event);
        source.output().send(message);
        log.info("Publish event {}", event);
    }
}
