package org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OrderCompletedSource {

    String OUTPUT = "order-completed-out";

    @Output(OrderCompletedSource.OUTPUT)
    MessageChannel output();
}
