package org.ashina.ecommerce.sharedkernel.registry;

import org.ashina.ecommerce.sharedkernel.query.gateway.DefaultQueryGateway;
import org.ashina.ecommerce.sharedkernel.query.gateway.QueryGateway;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;

@Configuration
public class QueryGatewayRegistry {

    @Bean
    public QueryGateway queryGateway(ApplicationContext applicationContext,
                                     Validator validator) {
        return new DefaultQueryGateway(applicationContext, validator);
    }
}
