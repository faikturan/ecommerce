package org.ashina.ecommerce.uaa.server.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class CreateOAuthClientRequest {

    @NotBlank
    private String clientId;

    @NotEmpty
    private Set<String> scopes;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

//    @NotEmpty
    private Set<String> registeredRedirectUris;
}
