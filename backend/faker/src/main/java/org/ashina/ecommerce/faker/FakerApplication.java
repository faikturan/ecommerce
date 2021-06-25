package org.ashina.ecommerce.faker;

import org.ashina.ecommerce.faker.repository.product.ProductRepository;
import org.ashina.ecommerce.faker.service.ProductFakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@SpringBootApplication
public class FakerApplication implements CommandLineRunner {

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
//		productFakerService.fake(1000);
		while (true) {
			int randomNumber = random.nextInt(100 - 1 + 1) + 1;
			productRepository.findByPriceBetween(randomNumber, randomNumber + 10);
		}
	}
}
