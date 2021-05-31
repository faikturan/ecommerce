package org.ashina.ecommerce.catalog.infrastructure.search.elasticsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "products")
@Data
public class EsProduct {

    @Id
    private String id;

    @Field(name = "name", type = FieldType.Text)
    private String name;

    @Field(name = "description", type = FieldType.Text)
    private String description;
}
