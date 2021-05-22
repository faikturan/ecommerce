package org.ashina.ecommerce.uaa.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("oauth_clients")
@Data
public class OAuthClient {

    @Id
    private String clientId;

    private String clientSecret;

    private Set<String> scopes;

    private Set<String> authorizedGrantTypes;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    private Set<String> registeredRedirectUris;
}
