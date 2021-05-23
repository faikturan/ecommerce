package org.ashina.ecommerce.gateway.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(authorizeExchange())
                .oauth2Login(Customizer.withDefaults())
                .csrf().disable()
                .build();
    }

    private Customizer<ServerHttpSecurity.AuthorizeExchangeSpec> authorizeExchange() {
        return exchanges -> exchanges
                .pathMatchers(HttpMethod.POST, "/api/customer-service/v1/customers").permitAll()
                .anyExchange().authenticated();
    }
}