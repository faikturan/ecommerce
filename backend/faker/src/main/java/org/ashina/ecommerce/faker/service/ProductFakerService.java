package org.ashina.ecommerce.faker.service;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.faker.model.product.Product;
import org.ashina.ecommerce.faker.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductFakerService implements FakerService {

    private final ProductRepository productRepository;
    private final Faker faker;

    public ProductFakerService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.faker = faker();
    }

    private Faker faker() {
        return new Faker();
    }

    @Override
    public void fake() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            Product product = create();
            products.add(product);
            if (products.size() % 1000 == 0) {
                productRepository.saveAll(products);
                products.clear();
            }
        }
        if (!products.isEmpty()) {
            productRepository.saveAll(products);
        }
    }

    private Product create() {
        Product product = new Product();
        product.setName(faker.commerce().productName());
        product.setImage(faker.internet().image());
        product.setPrice((int) Double.parseDouble(faker.commerce().price()));
        product.setDescription(faker.lorem().paragraph(5));
        return product;
    }
}
