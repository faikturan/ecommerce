package org.ashina.ecommerce.product.infrastructure.event.publisher;

import org.ashina.ecommerce.product.infrastructure.event.publisher.kafka.publisher.KafkaReserveProductRepliedPublisher;
import org.ashina.ecommerce.product.infrastructure.event.publisher.kafka.source.ReserveProductRepliedSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({ReserveProductRepliedSource.class})
public class EventPublisherRegistry {

    @Bean
    public ReserveProductRepliedPublisher reserveProductRequestedPublisher(ReserveProductRepliedSource source) {
        return new KafkaReserveProductRepliedPublisher(source);
    }
}
