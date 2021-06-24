package org.ashina.ecommerce.faker;

import org.ashina.ecommerce.faker.model.product.Product;
import org.ashina.ecommerce.faker.repository.product.ProductRepository;
import org.ashina.ecommerce.faker.service.ProductFakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FakerApplication implements CommandLineRunner {

	@Autowired
	private ProductFakerService productFakerService;

	public static void main(String[] args) {
		SpringApplication.run(FakerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		productFakerService.fake(10);
	}
}
