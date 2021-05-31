package org.ashina.ecommerce.inventory.domain;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.inventory.infrastructure.persistence.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stocks")
@Getter
@Setter
public class Stock extends BaseEntity {

    @Id
    private String productId;

    private Integer quantity;

    public Stock(String id) {
        super(id);
    }
}
