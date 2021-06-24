package org.ashina.ecommerce.faker.model.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "products")
@Getter
@Setter
public class Product extends BaseEntity {

    private String name;

    private String image;

    private Integer price;

    private String description;

    private Map<String, Object> attributes;

    private Integer quantity;
}
