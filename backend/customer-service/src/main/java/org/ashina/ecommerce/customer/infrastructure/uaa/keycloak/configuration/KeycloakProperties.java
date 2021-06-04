package org.ashina.ecommerce.customer.infrastructure.uaa.keycloak.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "uaa.keycloak")
@Getter
@Setter
public class KeycloakProperties {

    private String serverUrl;
    private String realm;
    private String clientId;
    private String username;
    private String password;
}
