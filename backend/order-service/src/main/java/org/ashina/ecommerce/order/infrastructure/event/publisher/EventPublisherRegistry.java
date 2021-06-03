package org.ashina.ecommerce.order.infrastructure.event.publisher;

import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.publisher.KafkaCreatePaymentRequestedPublisher;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.publisher.KafkaOrderCompletedPublisher;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.publisher.KafkaReserveProductRequestedPublisher;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.source.CreatePaymentRequestedSource;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.source.OrderCompletedSource;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.source.ReserveProductRequestedSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({ReserveProductRequestedSource.class, CreatePaymentRequestedSource.class, OrderCompletedSource.class})
public class EventPublisherRegistry {

    @Bean
    public ReserveProductRequestedPublisher reserveProductRequestedPublisher(ReserveProductRequestedSource source) {
        return new KafkaReserveProductRequestedPublisher(source);
    }

    @Bean
    public CreatePaymentRequestedPublisher createPaymentRequestedPublisher(CreatePaymentRequestedSource source) {
        return new KafkaCreatePaymentRequestedPublisher(source);
    }

    @Bean
    public OrderCompletedPublisher orderCompletedPublisher(OrderCompletedSource source) {
        return new KafkaOrderCompletedPublisher(source);
    }
}
