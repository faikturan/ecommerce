package org.ashina.ecommerce.order.domain;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.sharedkernel.domain.DomainAggregateRoot;

import java.util.List;

@Getter
@Setter
public class Order extends DomainAggregateRoot<String> {

    private String customerId;
    private OrderStatus status;
    private List<OrderLine> lines;
    private String fullName;
    private String phoneNumber;
    private String address;

    public Order(String id) {
        super(id);
    }
}
