package org.ashina.ecommerce.payment.infrastructure.event.subscriber;

import org.ashina.ecommerce.payment.application.event.handler.CreatePaymentRequestedHandler;
import org.ashina.ecommerce.payment.infrastructure.event.subscriber.kafka.sink.CreatePaymentRequestedSink;
import org.ashina.ecommerce.payment.infrastructure.event.subscriber.kafka.subscriber.KafkaCreatePaymentRequestedSubscriber;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({CreatePaymentRequestedSink.class})
public class EventSubscriberRegistry {

    @Bean
    public CreatePaymentRequestedSubscriber createPaymentRequestedSubscriber(CreatePaymentRequestedHandler handler) {
        return new KafkaCreatePaymentRequestedSubscriber(handler);
    }
}
