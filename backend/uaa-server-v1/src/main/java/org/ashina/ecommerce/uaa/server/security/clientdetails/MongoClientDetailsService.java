package org.ashina.ecommerce.uaa.server.security.clientdetails;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.uaa.server.entity.OAuthClient;
import org.ashina.ecommerce.uaa.server.repository.OAuthClientDetailsRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongoClientDetailsService implements ClientDetailsService {

    // Dependencies
    // -----------------------------------------------------------------------------------------------------------------

    private final OAuthClientDetailsRepository oAuthClientDetailsRepository;

    // Methods
    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OAuthClient oAuthClient = oAuthClientDetailsRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchClientException("No client with requested id: " + clientId));
        return newBaseClientDetails(oAuthClient);
    }

    private BaseClientDetails newBaseClientDetails(OAuthClient oAuthClient) {
        BaseClientDetails baseClientDetails = new BaseClientDetails();
        baseClientDetails.setClientId(oAuthClient.getClientId());
        baseClientDetails.setClientSecret(oAuthClient.getClientSecret());
        baseClientDetails.setScope(oAuthClient.getScopes());
        baseClientDetails.setAutoApproveScopes(oAuthClient.getScopes());
        baseClientDetails.setAuthorizedGrantTypes(oAuthClient.getAuthorizedGrantTypes());
        baseClientDetails.setAccessTokenValiditySeconds(oAuthClient.getAccessTokenValiditySeconds());
        baseClientDetails.setRefreshTokenValiditySeconds(oAuthClient.getRefreshTokenValiditySeconds());
        baseClientDetails.setRegisteredRedirectUri(oAuthClient.getRegisteredRedirectUris());
        return baseClientDetails;
    }
}
