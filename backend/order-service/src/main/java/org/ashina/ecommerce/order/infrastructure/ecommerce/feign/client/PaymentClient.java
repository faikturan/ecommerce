package org.ashina.ecommerce.order.infrastructure.ecommerce.feign.client;

import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.configuration.DefaultFeignConfiguration;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model.ProcessPaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service", configuration = DefaultFeignConfiguration.class)
public interface PaymentClient {

    @PostMapping("/api/v1/payments")
    void processPayment(ProcessPaymentDto dto);
}