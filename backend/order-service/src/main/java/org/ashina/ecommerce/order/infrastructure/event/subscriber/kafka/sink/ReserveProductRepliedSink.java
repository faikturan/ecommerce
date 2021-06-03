package org.ashina.ecommerce.order.infrastructure.event.subscriber.kafka.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ReserveProductRepliedSink {

    String INPUT = "reserve-product-replied-in";

    @Input(ReserveProductRepliedSink.INPUT)
    SubscribableChannel input();
}
