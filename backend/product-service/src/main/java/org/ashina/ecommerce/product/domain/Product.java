package org.ashina.ecommerce.product.domain;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.product.infrastructure.persistence.entity.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "products")
@Getter
@Setter
public class Product extends BaseEntity {

    private String name;

    private String image;

    private Integer price;

    private String description;

    private Map<String, Object> attributes = new HashMap<>();

    private Integer quantity;

    public Product(String id) {
        super(id);
    }
}
