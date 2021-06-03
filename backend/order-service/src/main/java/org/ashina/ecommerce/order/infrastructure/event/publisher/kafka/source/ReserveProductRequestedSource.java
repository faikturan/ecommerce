package org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ReserveProductRequestedSource {

    String OUTPUT = "reserve-product-requested-out";

    @Output(ReserveProductRequestedSource.OUTPUT)
    MessageChannel output();
}
