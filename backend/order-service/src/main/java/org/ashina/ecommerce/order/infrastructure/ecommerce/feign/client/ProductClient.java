package org.ashina.ecommerce.order.infrastructure.ecommerce.feign.client;

import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.configuration.DefaultFeignConfiguration;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model.RefundProductsDto;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model.ReserveProductsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "product-service", configuration = DefaultFeignConfiguration.class)
public interface ProductClient {

    @PostMapping("/api/v1/inventory?action=reserve")
    void reserveProducts(ReserveProductsDto dto);

    @PostMapping("/api/v1/inventory?action=refund")
    void refundProducts(RefundProductsDto dto);
}