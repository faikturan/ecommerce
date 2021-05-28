package org.ashina.ecommerce.order.infrastructure.persistence.jpa.converter;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.order.domain.Order;
import org.ashina.ecommerce.order.infrastructure.persistence.jpa.entity.JpaOrder;
import org.ashina.ecommerce.sharedkernel.persistence.PersistenceConverter;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JpaOrderConverter implements PersistenceConverter<Order, JpaOrder> {

    private final JpaOrderLineConverter jpaOrderLineConverter;

    @Override
    public JpaOrder mapToPersistentObject(Order order) {
        JpaOrder jpaOrder = new JpaOrder();
        jpaOrder.setId(order.getId());
        jpaOrder.setCustomerId(order.getCustomerId());
        jpaOrder.setStatus(order.getStatus());
        jpaOrder.setLines(order.getLines()
                .stream()
                .map(jpaOrderLineConverter::mapToPersistentObject)
                .collect(Collectors.toList())
        );
        jpaOrder.setFullName(order.getFullName());
        jpaOrder.setPhoneNumber(order.getPhoneNumber());
        jpaOrder.setAddress(order.getAddress());
        return jpaOrder;
    }

    @Override
    public Order mapToDomainEntity(JpaOrder jpaOrder) {
        Order order = new Order(jpaOrder.getId());
        order.setCustomerId(jpaOrder.getCustomerId());
        order.setStatus(jpaOrder.getStatus());
        order.setLines(jpaOrder.getLines()
                .stream()
                .map(jpaOrderLineConverter::mapToDomainEntity)
                .collect(Collectors.toList()));
        order.setFullName(jpaOrder.getFullName());
        order.setPhoneNumber(jpaOrder.getPhoneNumber());
        order.setAddress(jpaOrder.getAddress());
        return order;
    }
}
