package org.ashina.ecommerce.payment.infrastructure.event.publisher;

import org.ashina.ecommerce.payment.infrastructure.event.publisher.kafka.publisher.KafkaCreatePaymentRepliedPublisher;
import org.ashina.ecommerce.payment.infrastructure.event.publisher.kafka.source.CreatePaymentRepliedSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({CreatePaymentRepliedSource.class})
public class EventPublisherRegistry {

    @Bean
    public CreatePaymentRepliedPublisher createPaymentRepliedPublisher(CreatePaymentRepliedSource source) {
        return new KafkaCreatePaymentRepliedPublisher(source);
    }
}
