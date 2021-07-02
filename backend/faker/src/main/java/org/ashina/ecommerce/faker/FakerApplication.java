package org.ashina.ecommerce.faker;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.faker.service.CustomerFakerService;
import org.ashina.ecommerce.faker.service.OrderFakerService;
import org.ashina.ecommerce.faker.service.ProductFakerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class FakerApplication implements CommandLineRunner {

	private final CustomerFakerService customerFakerService;
	private final ProductFakerService productFakerService;
	private final OrderFakerService orderFakerService;

	public static void main(String[] args) {
		SpringApplication.run(FakerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		customerFakerService.fake(1000000);
//		productFakerService.fake(1000000);
		orderFakerService.fake();
	}
}
