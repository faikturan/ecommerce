package org.ashina.ecommerce.cart.infrastructure.persistence.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cart_lines")
@Getter
@Setter
public class JpaCartLine extends JpaBaseEntity {

    @Id
    private String id;

    private String customerId;

    private String productId;

    private Integer quantity;
}
