package org.ashina.ecommerce.payment.domain;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.payment.infrastructure.persistence.entity.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payments")
@Getter
@Setter
public class Payment extends BaseEntity {

    private String customerId;

    private String orderId;

    private Integer amount;
}