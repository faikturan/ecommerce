package org.ashina.ecommerce.sharedkernel.event.model.payment;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.sharedkernel.event.model.FulfillmentOrderRequested;

@Getter
@Setter
public class CreatePaymentRequested extends FulfillmentOrderRequested {

    private String orderId;
    private Integer amount;
}
