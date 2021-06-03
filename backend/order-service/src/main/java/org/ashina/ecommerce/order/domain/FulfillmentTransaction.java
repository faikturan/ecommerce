package org.ashina.ecommerce.order.domain;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.order.infrastructure.persistence.entity.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fulfillment_transactions")
@Getter
@Setter
public class FulfillmentTransaction extends BaseEntity {

    public enum Status {
        ORDER_CREATED,
        PRODUCT_RESERVED,
        PAYMENT_CREATED,
        ORDER_COMPLETED
    }

    private String orderId;

    private Status status;
}
