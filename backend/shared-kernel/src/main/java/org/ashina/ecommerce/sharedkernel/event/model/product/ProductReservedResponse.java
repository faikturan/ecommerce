package org.ashina.ecommerce.sharedkernel.event.model.product;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.sharedkernel.event.model.DomainEvent;

@Getter
@Setter
public class ProductReservedResponse extends DomainEvent {

    private String transactionId;
    private boolean isDone;
    private String throwable;
}
