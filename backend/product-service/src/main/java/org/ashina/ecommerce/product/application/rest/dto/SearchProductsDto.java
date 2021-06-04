package org.ashina.ecommerce.product.application.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchProductsDto {

    @Getter
    @Setter
    public static class Product {
        private String id;
        private String name;
        private String description;
        private String image;
        private Integer price;
    }

    private List<Product> products;
}
