package org.ashina.ecommerce.order.infrastructure.ecommerce.feign.configuration;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "org.ashina.ecommerce.order.infrastructure.ecommerce.feign.client")
public class DefaultFeignConfiguration {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor authorizationRequestInterceptor() {
        return new AuthorizationRequestInterceptor();
    }
}
