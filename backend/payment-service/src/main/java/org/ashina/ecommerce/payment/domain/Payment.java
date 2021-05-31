package org.ashina.ecommerce.payment.domain;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.payment.infrastructure.persistence.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment extends BaseEntity {

    private String orderId;

    private Integer amount;

    public Payment(String id) {
        super(id);
    }
}
