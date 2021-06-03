package org.ashina.ecommerce.product.infrastructure.event.publisher.kafka.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ReserveProductRepliedSource {

    String OUTPUT = "reserve-product-replied-out";

    @Output(ReserveProductRepliedSource.OUTPUT)
    MessageChannel output();
}
