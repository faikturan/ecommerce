package org.ashina.ecommerce.product.infrastructure.event.subscriber.kafka.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ReserveProductRequestedSink {

    String INPUT = "reserve-product-requested-in";

    @Input(ReserveProductRequestedSink.INPUT)
    SubscribableChannel input();
}
