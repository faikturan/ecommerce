package org.ashina.ecommerce.product.infrastructure.event.subscriber;

import org.ashina.ecommerce.product.application.event.handler.OrderCanceledHandler;
import org.ashina.ecommerce.product.infrastructure.event.subscriber.kafka.KafkaOrderCanceledSubscriber;
import org.ashina.ecommerce.product.infrastructure.event.subscriber.kafka.OrderCanceledSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({OrderCanceledSink.class})
public class EventSubscriberRegistry {

    @Bean
    public OrderCanceledSubscriber orderCanceledSubscriber(OrderCanceledHandler orderCanceledHandler) {
        return new KafkaOrderCanceledSubscriber(orderCanceledHandler);
    }
}