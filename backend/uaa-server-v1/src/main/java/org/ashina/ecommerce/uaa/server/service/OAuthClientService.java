package org.ashina.ecommerce.uaa.server.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.ashina.ecommerce.uaa.server.dto.request.CreateOAuthClientRequest;
import org.ashina.ecommerce.uaa.server.dto.response.CreateOAuthClientResponse;
import org.ashina.ecommerce.uaa.server.entity.OAuthClient;
import org.ashina.ecommerce.uaa.server.repository.OAuthClientDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OAuthClientService {

    // Dependencies
    // -----------------------------------------------------------------------------------------------------------------

    private final OAuthClientDetailsRepository oAuthClientDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    // Methods
    // -----------------------------------------------------------------------------------------------------------------

    public CreateOAuthClientResponse createOAuthClient(CreateOAuthClientRequest request) {
        if (oAuthClientDetailsRepository.findById(request.getClientId()).isPresent()) {
            throw new IllegalArgumentException(String.format("Client ID %s already exists", request.getClientId()));
        }

        String clientSecret = newClientSecret();
        OAuthClient oAuthClient = newOAuthClient(request, clientSecret);
        oAuthClientDetailsRepository.save(oAuthClient);

        return new CreateOAuthClientResponse(request.getClientId(), clientSecret);
    }

    private OAuthClient newOAuthClient(CreateOAuthClientRequest request, String clientSecret) {
        OAuthClient oAuthClient = new OAuthClient();
        oAuthClient.setClientId(request.getClientId());
        oAuthClient.setClientSecret(passwordEncoder.encode(clientSecret));
        oAuthClient.setScopes(request.getScopes());
        oAuthClient.setAuthorizedGrantTypes(
                new HashSet<>(Arrays.asList("authorization_code","password","refresh_token")));
        oAuthClient.setAccessTokenValiditySeconds(
                Objects.nonNull(request.getAccessTokenValiditySeconds()) ?
                        request.getAccessTokenValiditySeconds() : 2592000); // 30 days
        oAuthClient.setRefreshTokenValiditySeconds(
                Objects.nonNull(request.getRefreshTokenValiditySeconds()) ?
                        request.getRefreshTokenValiditySeconds() : 2592000);
        oAuthClient.setRegisteredRedirectUris(request.getRegisteredRedirectUris());
        return oAuthClient;
    }

    private String newClientSecret() {
        return RandomStringUtils.random(10, true, true);
    }

    public void deleteClient(String clientId) {
        oAuthClientDetailsRepository.deleteById(clientId);
    }
}
