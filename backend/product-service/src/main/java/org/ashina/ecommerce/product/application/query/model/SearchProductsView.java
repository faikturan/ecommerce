package org.ashina.ecommerce.product.application.query.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SearchProductsView {

    @Getter
    @Setter
    public static class Product {

        private String id;
        private String name;
        private String description;
        private String image;
        private Integer price;

        public Product(org.ashina.ecommerce.product.domain.Product domainProduct) {
            this.id = domainProduct.getId();
            this.name = domainProduct.getName();
            this.description = domainProduct.getDescription();
            this.image = domainProduct.getImage();
            this.price = domainProduct.getPrice();
        }
    }

    private List<Product> products;

    public SearchProductsView(List<org.ashina.ecommerce.product.domain.Product> domainProducts) {
        this.products = domainProducts
                .stream()
                .map(Product::new)
                .collect(Collectors.toList());
    }

}
