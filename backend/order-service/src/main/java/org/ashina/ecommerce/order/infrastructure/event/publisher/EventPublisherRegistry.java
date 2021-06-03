package org.ashina.ecommerce.order.infrastructure.event.publisher;

import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.KafkaOrderCanceledPublisher;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.KafkaProductReservedRequestPublisher;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.OrderCanceledSource;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.ProductReservedRequestSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({ProductReservedRequestSource.class, OrderCanceledSource.class})
public class EventPublisherRegistry {

    @Bean
    public ProductReservedRequestPublisher productReservedRequestPublisher(ProductReservedRequestSource productReservedRequestSource) {
        return new KafkaProductReservedRequestPublisher(productReservedRequestSource);
    }

    @Bean
    public OrderCanceledPublisher orderCanceledPublisher(OrderCanceledSource orderCanceledSource) {
        return new KafkaOrderCanceledPublisher(orderCanceledSource);
    }
}
