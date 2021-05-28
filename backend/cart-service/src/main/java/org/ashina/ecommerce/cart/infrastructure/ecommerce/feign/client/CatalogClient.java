package org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.client;

import org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.configuration.DefaultFeignConfiguration;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@FeignClient(name = "catalog", configuration = DefaultFeignConfiguration.class)
public interface CatalogClient {

    @GetMapping("/api/v1/products")
    List<Product> getProducts(@RequestParam Collection<String> ids);
}