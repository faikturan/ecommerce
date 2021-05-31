package org.ashina.ecommerce.shipment.domain;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.shipment.infrastructure.persistence.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "shipments")
@Getter
@Setter
public class Shipment extends BaseEntity {

    private String orderId;

    private String fullName;

    private String phoneNo;

    private String address;

    public Shipment(String id) {
        super(id);
    }
}
