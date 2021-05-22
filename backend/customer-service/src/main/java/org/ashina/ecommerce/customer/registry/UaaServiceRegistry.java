package org.ashina.ecommerce.customer.registry;

import org.ashina.ecommerce.customer.infrastructure.uaa.UaaService;
import org.ashina.ecommerce.customer.infrastructure.uaa.spring.adapter.SpringUaaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UaaServiceRegistry {

    @Bean
    public UaaService uaaService(RestTemplate restTemplate) {
        return new SpringUaaService(restTemplate);
    }
}
