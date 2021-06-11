package org.ashina.ecommerce.sharedkernel.event.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class DomainEvent {

    protected String eventId;
    protected LocalDateTime createdAt;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }
}
