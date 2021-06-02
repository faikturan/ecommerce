package org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.model;

import lombok.Data;

import java.util.List;

@Data
public class GetProductsDto {

    @Data
    public static class Product {
        private String id;
        private String name;
        private String image;
        private Integer price;
        private Integer quantity;
    }

    private List<Product> products;
}
