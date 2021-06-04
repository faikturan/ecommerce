package org.ashina.ecommerce.customer.domain;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.customer.infrastructure.persistence.entity.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
@Getter
@Setter
public class Customer extends BaseEntity {

    private String fullName;

    private String email;
}
