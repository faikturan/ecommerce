package org.ashina.ecommerce.order.infrastructure.event.publisher.kafka;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProductReservedRequestSource {

    String OUTPUT = "product-reserved-request-out";

    @Output(ProductReservedRequestSource.OUTPUT)
    MessageChannel output();
}
