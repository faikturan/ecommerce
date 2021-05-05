package org.ashina.ecommerce.catalog.application.query.model;

import lombok.Data;
import org.ashina.ecommerce.sharedkernel.query.model.View;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetProductsView extends View {

    @Data
    public static class Product {
        private String id;
        private String name;
        private String image;
        private Integer price;

        public Product(org.ashina.ecommerce.catalog.domain.Product domainProduct) {
            this.id = domainProduct.getId();
            this.name = domainProduct.getName();
            this.image = domainProduct.getName();
            this.price = domainProduct.getPrice();
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
