package org.ashina.ecommerce.order.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;

public class SecurityContextHelper {

    private SecurityContextHelper() {
    }

    public static String currentCustomerId(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        return (String) claims.getOrDefault("customerId", "");
    }

    public static String getCurrentAccessToken() {
        JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return token.getToken().getTokenValue();
    }
}
