package org.ashina.ecommerce.catalog.application.query.model;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetProductsView {

    @Data
    public static class Product {
        private String id;
        private String name;
        private Integer price;
        private Integer quantity;

        public Product(org.ashina.ecommerce.catalog.domain.Product domainProduct) {
            this.id = domainProduct.getId();
            this.name = domainProduct.getName();
            this.price = domainProduct.getPrice();
            this.quantity = domainProduct.getQuantity();
        }
    }

    private List<Product> products;

    public GetProductsView(List<org.ashina.ecommerce.catalog.domain.Product> domainProducts) {
        this.products = domainProducts
                .stream()
                .map(Product::new)
                .collect(Collectors.toList());
    }
}
