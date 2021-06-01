package org.ashina.ecommerce.product.application.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchProductsDto {

    @Data
    public static class Product {
        private String id;
        private String name;
        private String description;
        private String image;
        private Integer price;
    }

    private List<Product> products;
}
