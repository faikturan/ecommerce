package org.ashina.ecommerce.cart.domain;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.sharedkernel.domain.DomainAggregateRoot;

@Getter
@Setter
public class CartLine extends DomainAggregateRoot<String> {

    private String customerId;
    private String productId;
    private Integer quantity;

    public static final int MAX_QUANTITY = 10;

    public CartLine(String id) {
        super(id);
    }
}
