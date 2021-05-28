package org.ashina.ecommerce.cart.registry;

import org.ashina.ecommerce.cart.infrastructure.ecommerce.CatalogService;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.adapter.FeignCatalogService;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.client.CatalogClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Please comment @EnableFeignClients if you use other adapter
 */
@Configuration
public class EcommerceServiceRegistry {

    @Bean
    public CatalogService catalogService(CatalogClient catalogClient) {
        return new FeignCatalogService(catalogClient);
    }
}
