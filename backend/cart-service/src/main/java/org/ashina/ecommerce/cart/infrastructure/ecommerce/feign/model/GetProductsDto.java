package org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetProductsDto {

    @Getter
    @Setter
    public static class Product {
        private String id;
        private String name;
        private String image;
        private Integer price;
        private Integer quantity;
    }

    private List<Product> products;
}
