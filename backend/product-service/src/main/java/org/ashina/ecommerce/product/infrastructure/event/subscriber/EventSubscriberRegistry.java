package org.ashina.ecommerce.product.infrastructure.event.subscriber;

import org.ashina.ecommerce.product.application.event.handler.ReserveProductRequestedHandler;
import org.ashina.ecommerce.product.infrastructure.event.subscriber.kafka.sink.ReserveProductRequestedSink;
import org.ashina.ecommerce.product.infrastructure.event.subscriber.kafka.subscriber.KafkaReserveProductRequestedSubscriber;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({ReserveProductRequestedSink.class})
public class EventSubscriberRegistry {

    @Bean
    public ReserveProductRequestedSubscriber reserveProductRequestedSubscriber(ReserveProductRequestedHandler handler) {
        return new KafkaReserveProductRequestedSubscriber(handler);
    }
}
