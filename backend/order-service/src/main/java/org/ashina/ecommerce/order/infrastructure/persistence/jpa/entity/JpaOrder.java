package org.ashina.ecommerce.order.infrastructure.persistence.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.order.domain.OrderStatus;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class JpaOrder extends JpaBaseEntity {

    @Id
    private String id;

    private String customerId;

    private OrderStatus status;

    private String fullName;

    private String phoneNumber;

    private String address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private List<JpaOrderLine> lines;

}
