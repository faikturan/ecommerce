package org.ashina.ecommerce.customer.infrastructure.uaa;

import org.ashina.ecommerce.customer.infrastructure.uaa.keycloak.KeycloakUaaService;
import org.ashina.ecommerce.customer.infrastructure.uaa.keycloak.configuration.KeycloakProperties;
import org.keycloak.admin.client.Keycloak;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UaaServiceRegistry {

    @Bean
    public UaaService uaaService(Keycloak keycloak, KeycloakProperties keycloakProperties) {
        return new KeycloakUaaService(keycloak, keycloakProperties);
    }
}
