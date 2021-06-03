package org.ashina.ecommerce.payment.infrastructure.event.subscriber.kafka.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CreatePaymentRequestedSink {

    String INPUT = "create-payment-requested-in";

    @Input(CreatePaymentRequestedSink.INPUT)
    SubscribableChannel input();
}
