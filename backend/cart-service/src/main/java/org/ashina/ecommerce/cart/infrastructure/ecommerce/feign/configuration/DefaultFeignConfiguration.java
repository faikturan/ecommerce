package org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.configuration;

import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableFeignClients(basePackages = "org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.client")
public class DefaultFeignConfiguration {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options feignRequestOptions() {
        return new Request.Options(30, TimeUnit.SECONDS, 30, TimeUnit.SECONDS, true);
    }

    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return new AuthorizationRequestInterceptor();
    }
}
