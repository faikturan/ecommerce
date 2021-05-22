package org.ashina.ecommerce.uaa.server.security.configuration;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.uaa.server.security.clientdetails.MongoClientDetailsService;
import org.ashina.ecommerce.uaa.server.security.jwt.enhancer.IdTokenEnhancer;
import org.ashina.ecommerce.uaa.server.security.jwt.enhancer.JwtClaimsEnhancer;
import org.ashina.ecommerce.uaa.server.security.jwt.jwk.KeytoolGenerator;
import org.ashina.ecommerce.uaa.server.security.userdetails.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.List;

@Configuration
@EnableAuthorizationServer
@Order(2)
@RequiredArgsConstructor
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    // Dependencies
    // -----------------------------------------------------------------------------------------------------------------

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final MongoClientDetailsService clientDetailsService;
    private final DataSource dataSource;

    // Authorization code configuration
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Stores authorization code into oauth_code table
     */
    @Bean
    public JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    // Token enhancer configuration
    // -----------------------------------------------------------------------------------------------------------------

    @Bean
    public KeyPair keyPair() throws Exception {
        return new KeytoolGenerator().keyPair();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() throws Exception {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    private TokenEnhancerChain tokenEnhancerChain() throws Exception {
        List<TokenEnhancer> tokenEnhancers = Lists.newArrayList(
                new JwtClaimsEnhancer(clientDetailsService),
                jwtAccessTokenConverter(),
                new IdTokenEnhancer(jwtAccessTokenConverter())
        );
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        return tokenEnhancerChain;
    }

    // Token store configuration
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Store access token into oauth_access_token table
     * Store refresh token into oauth_refresh_token table
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    // Authorization server configuration
    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .authorizationCodeServices(jdbcAuthorizationCodeServices())
                .tokenEnhancer(tokenEnhancerChain())
                .tokenStore(tokenStore());
    }
}
