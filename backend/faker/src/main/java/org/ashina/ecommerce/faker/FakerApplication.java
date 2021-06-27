package org.ashina.ecommerce.faker;

import org.ashina.ecommerce.faker.model.product.Product;
import org.ashina.ecommerce.faker.repository.product.ProductRepository;
import org.ashina.ecommerce.faker.service.CustomerFakerService;
import org.ashina.ecommerce.faker.service.ProductFakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class FakerApplication implements CommandLineRunner {

	@Autowired
	private CustomerFakerService customerFakerService;

	@Autowired
	private ProductFakerService productFakerService;

	@Autowired
	private ProductRepository productRepository;

	private static final Random random = new Random();

	public static void main(String[] args) {
		SpringApplication.run(FakerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		customerFakerService.fake(1000000);
//		productFakerService.fake(1000000);
//		while (true) {
//			int random = (int) (Math.random() * 100 + 1);
//			List<Product> products = productRepository.findByPriceBetween(random, random + 30);
//			Thread.sleep(100);
//		}
	}
}
