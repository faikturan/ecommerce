package org.ashina.ecommerce.sharedkernel.event.model.order;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.sharedkernel.event.model.DomainEvent;

@Getter
@Setter
public class OrderCompleted extends DomainEvent {

    private String customerId;
    private String orderId;
}
