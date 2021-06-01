package org.ashina.ecommerce.product.application.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetProductsDto {

    @Data
    public static class Product {
        private String id;
        private String name;
        private Integer price;
        private Integer quantity;
    }

    private List<Product> products;
}
