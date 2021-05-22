package org.ashina.ecommerce.uaa.server.security.jwt.enhancer;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.uaa.server.security.clientdetails.MongoClientDetailsService;
import org.ashina.ecommerce.uaa.server.security.userdetails.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtClaimsEnhancer implements TokenEnhancer {

    private final MongoClientDetailsService clientDetailsService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final String issuer = ServletUriComponentsBuilder.fromCurrentRequest().build().toString();
        final Map<String, Object> additionalInformation = new LinkedHashMap<>();
        final Instant expiration = accessToken.getExpiration().toInstant();

        final Authentication client = SecurityContextHolder.getContext().getAuthentication();
        final String clientId = client.getName();
        final ClientDetails clientDetails = this.clientDetailsService.loadClientByClientId(clientId);

        additionalInformation.put(JwtClaimNames.ISS, issuer);
        additionalInformation.put(JwtClaimNames.EXP, expiration.getEpochSecond());
        additionalInformation.put(JwtClaimNames.IAT, expiration.minusSeconds(clientDetails.getAccessTokenValiditySeconds()).getEpochSecond());
        additionalInformation.put(JwtClaimNames.AUD, Lists.newArrayList(clientId));
        final String nonce = authentication.getOAuth2Request().getRequestParameters().get((OidcParameterNames.NONCE));
        if (nonce != null) {
            additionalInformation.put(OidcParameterNames.NONCE, nonce);
        }
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            additionalInformation.put(JwtClaimNames.SUB, userDetails.getId() /* UserID */);
            additionalInformation.put("email", userDetails.getUsername());
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        return accessToken;
    }
}
