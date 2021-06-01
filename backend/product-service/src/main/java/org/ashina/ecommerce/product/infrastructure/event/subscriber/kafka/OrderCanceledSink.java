package org.ashina.ecommerce.product.infrastructure.event.subscriber.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OrderCanceledSink {

    String INPUT = "order-canceled-in";

    @Input(OrderCanceledSink.INPUT)
    SubscribableChannel input();
}
