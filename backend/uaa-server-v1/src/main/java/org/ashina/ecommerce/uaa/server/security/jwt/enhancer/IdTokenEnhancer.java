package org.ashina.ecommerce.uaa.server.security.jwt.enhancer;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

@RequiredArgsConstructor
public class IdTokenEnhancer implements TokenEnhancer {

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (accessToken.getScope().contains(OidcScopes.OPENID)) {
            DefaultOAuth2AccessToken idToken = new DefaultOAuth2AccessToken(accessToken);
            idToken.setScope(Sets.newHashSet(OidcScopes.OPENID));
            idToken.setRefreshToken(null);
            Map<String, Object> additionalInformation = Maps.newHashMap();
            additionalInformation.put(OidcParameterNames.ID_TOKEN,
                    this.jwtAccessTokenConverter.enhance(idToken, authentication).getValue());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        }
        return accessToken;
    }
}
