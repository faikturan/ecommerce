package org.ashina.ecommerce.uaa.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.ashina.ecommerce")
public class UaaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaaServerApplication.class, args);
	}
}
