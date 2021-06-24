package org.ashina.ecommerce.faker.service;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.faker.model.product.Product;
import org.ashina.ecommerce.faker.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductFakerService implements FakerService {

    private final Faker faker;
    private final ProductRepository productRepository;

    private static final int BATCH_SIZE = 100;

    @Override
    public void fake(int count) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Product product = fake();
            products.add(product);
            if (products.size() % BATCH_SIZE == 0) {
                productRepository.saveAll(products);
                log.debug("Fake {} products done", BATCH_SIZE);
                products.clear();
            }
        }
        if (!products.isEmpty()) {
            productRepository.saveAll(products);
        }
    }

    private Product fake() {
        Product product = new Product();
        product.setName(faker.commerce().productName());
        product.setImage(faker.internet().image());
        product.setPrice((int) Double.parseDouble(faker.commerce().price()));
        product.setDescription(faker.lorem().paragraph(5));
        return product;
    }
}
