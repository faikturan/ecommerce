package org.ashina.ecommerce.cart.infrastructure.event.subscriber;

import org.ashina.ecommerce.cart.application.event.handler.OrderCompletedHandler;
import org.ashina.ecommerce.cart.infrastructure.event.subscriber.kafka.sink.OrderCompletedSink;
import org.ashina.ecommerce.cart.infrastructure.event.subscriber.kafka.subscriber.KafkaOrderCompletedSubscriber;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({OrderCompletedSink.class})
public class EventSubscriberRegistry {

    @Bean
    public OrderCompletedSubscriber orderCompletedSubscriber(OrderCompletedHandler handler) {
        return new KafkaOrderCompletedSubscriber(handler);
    }
}
