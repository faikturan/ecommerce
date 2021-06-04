package org.ashina.ecommerce.order.infrastructure.event.publisher;

import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.publisher.KafkaOrderCompletedPublisher;
import org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.source.OrderCompletedSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({OrderCompletedSource.class})
public class EventPublisherRegistry {

    @Bean
    public OrderCompletedPublisher orderCompletedPublisher(OrderCompletedSource source) {
        return new KafkaOrderCompletedPublisher(source);
    }
}
