package org.ashina.ecommerce.order.infrastructure.event.publisher.kafka.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CreatePaymentRequestedSource {

    String OUTPUT = "create-payment-requested-out";

    @Output(CreatePaymentRequestedSource.OUTPUT)
    MessageChannel output();
}
