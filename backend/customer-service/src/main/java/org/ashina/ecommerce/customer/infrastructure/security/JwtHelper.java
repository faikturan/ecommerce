package org.ashina.ecommerce.customer.infrastructure.security;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

public class JwtHelper {

    private JwtHelper() {
    }

    public static String currentCustomerId(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        return (String) claims.getOrDefault("customerId", "");
    }
}
