package org.ashina.ecommerce.sharedkernel.event.model.payment;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.sharedkernel.event.model.DomainEvent;

@Getter
@Setter
public class PaymentCreatedRequest extends DomainEvent {

    private String transactionId;
    private String orderId;
    private Integer amount;
}
