package org.ashina.ecommerce.customer.infrastructure.uaa.keycloak.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "uaa.keycloak")
@Data
public class KeycloakProperties {

    private String serverUrl;
    private String realm;
    private String clientId;
    private String username;
    private String password;
}
