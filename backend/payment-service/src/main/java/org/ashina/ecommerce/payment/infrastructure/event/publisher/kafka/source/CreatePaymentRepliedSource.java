package org.ashina.ecommerce.payment.infrastructure.event.publisher.kafka.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CreatePaymentRepliedSource {

    String OUTPUT = "create-payment-replied-out";

    @Output(CreatePaymentRepliedSource.OUTPUT)
    MessageChannel output();
}
