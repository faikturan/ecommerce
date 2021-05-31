package org.ashina.ecommerce.sharedkernel.registry;

import org.ashina.ecommerce.sharedkernel.command.gateway.DefaultCommandGateway;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;

@Configuration
public class CommandGatewayRegistry {

    @Bean
    public DefaultCommandGateway commandGateway(ApplicationContext applicationContext,
                                                Validator validator) {
        return new DefaultCommandGateway(applicationContext, validator);
    }
}
