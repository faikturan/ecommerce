package org.ashina.ecommerce.product.infrastructure.event.publisher.kafka;

import org.ashina.ecommerce.sharedkernel.event.model.DomainEvent;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

public class MessageFactory {

    private MessageFactory() {
    }

    public static <T extends DomainEvent> Message<T> newMessage(T event) {
        return MessageBuilder
                .withPayload(event)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
    }
}
