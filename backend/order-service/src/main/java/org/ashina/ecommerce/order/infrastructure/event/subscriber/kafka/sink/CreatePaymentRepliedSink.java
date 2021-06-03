package org.ashina.ecommerce.order.infrastructure.event.subscriber.kafka.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CreatePaymentRepliedSink {

    String INPUT = "create-payment-replied-in";

    @Input(CreatePaymentRepliedSink.INPUT)
    SubscribableChannel input();
}
