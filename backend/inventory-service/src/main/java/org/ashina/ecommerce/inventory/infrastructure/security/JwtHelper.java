package org.ashina.ecommerce.inventory.infrastructure.security;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

public class JwtHelper {

    private JwtHelper() {
    }

    public String currentCustomerId(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        return "";
    }
}
