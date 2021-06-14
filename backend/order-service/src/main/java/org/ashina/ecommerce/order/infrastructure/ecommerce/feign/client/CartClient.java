package org.ashina.ecommerce.order.infrastructure.ecommerce.feign.client;

import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.configuration.DefaultFeignConfiguration;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.fallback.GetCartFallbackFactory;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model.GetCartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "cart-service",
        configuration = DefaultFeignConfiguration.class,
        fallbackFactory = GetCartFallbackFactory.class
)
public interface CartClient {

    @GetMapping("/api/v1/carts")
    GetCartDto getCart();
}
