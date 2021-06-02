package org.ashina.ecommerce.order.infrastructure.event.publisher;

import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.KafkaOrderCanceledPublisher;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.KafkaOrderCreatedPublisher;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.OrderCanceledSource;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.OrderCreatedSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({OrderCreatedSource.class, OrderCanceledSource.class})
public class EventPublisherRegistry {

    @Bean
    public OrderCreatedPublisher orderCreatedPublisher(OrderCreatedSource orderCreatedSource) {
        return new KafkaOrderCreatedPublisher(orderCreatedSource);
    }

    @Bean
    public OrderCanceledPublisher orderCanceledPublisher(OrderCanceledSource orderCanceledSource) {
        return new KafkaOrderCanceledPublisher(orderCanceledSource);
    }
}
