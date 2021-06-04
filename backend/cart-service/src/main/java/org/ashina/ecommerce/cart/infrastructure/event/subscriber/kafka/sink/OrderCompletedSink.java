package org.ashina.ecommerce.cart.infrastructure.event.subscriber.kafka.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OrderCompletedSink {

    String INPUT = "order-completed-in";

    @Input(OrderCompletedSink.INPUT)
    SubscribableChannel input();
}
