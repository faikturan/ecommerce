package org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.client;

import org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.configuration.DefaultFeignConfiguration;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.model.GetProductsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@FeignClient(name = "product-service", configuration = DefaultFeignConfiguration.class)
public interface ProductClient {

    @GetMapping("/api/v1/products?action=get")
    GetProductsDto getProducts(@RequestParam Collection<String> ids);
}