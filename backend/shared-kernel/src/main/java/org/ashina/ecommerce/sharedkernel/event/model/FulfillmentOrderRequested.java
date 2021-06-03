package org.ashina.ecommerce.sharedkernel.event.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FulfillmentOrderRequested extends DomainEvent {

    private String transactionId;
    private boolean isRollback;
}
