package org.ashina.ecommerce.order.infrastructure.ecommerce.feign.client;

import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.configuration.DefaultFeignConfiguration;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model.RefundProductsDto;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model.ReserveProductsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "product-service", configuration = DefaultFeignConfiguration.class)
public interface ProductClient {

    @GetMapping("/api/v1/products?action=reserve")
    void reserveProducts(ReserveProductsDto dto);

    @GetMapping("/api/v1/products?action=refund")
    void refundProducts(RefundProductsDto dto);
}