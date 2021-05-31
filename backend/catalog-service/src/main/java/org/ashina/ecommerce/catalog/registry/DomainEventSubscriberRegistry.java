package org.ashina.ecommerce.catalog.registry;

import org.ashina.ecommerce.catalog.application.event.handler.OrderCanceledHandler;
import org.ashina.ecommerce.catalog.infrastructure.event.subscriber.OrderCanceledSubscriber;
import org.ashina.ecommerce.catalog.infrastructure.event.subscriber.kafka.KafkaOrderCanceledSubscriber;
import org.ashina.ecommerce.catalog.infrastructure.event.subscriber.kafka.OrderCanceledSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({OrderCanceledSink.class})
public class DomainEventSubscriberRegistry {

    @Bean
    public OrderCanceledSubscriber orderCanceledSubscriber(OrderCanceledHandler orderCanceledHandler) {
        return new KafkaOrderCanceledSubscriber(orderCanceledHandler);
    }
}
