package org.ashina.ecommerce.order.infrastructure.event.subscriber;

import org.ashina.ecommerce.order.application.event.handler.ReserveProductRepliedHandler;
import org.ashina.ecommerce.order.infrastructure.event.subscriber.kafka.sink.CreatePaymentRepliedSink;
import org.ashina.ecommerce.order.infrastructure.event.subscriber.kafka.sink.ReserveProductRepliedSink;
import org.ashina.ecommerce.order.infrastructure.event.subscriber.kafka.subscriber.KafkaReserveProductRepliedSubscriber;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({ReserveProductRepliedSink.class, CreatePaymentRepliedSink.class})
public class EventSubscriberRegistry {

    @Bean
    public ReserveProductRepliedSubscriber reserveProductRepliedSubscriber(ReserveProductRepliedHandler handler) {
        return new KafkaReserveProductRepliedSubscriber(handler);
    }
}
