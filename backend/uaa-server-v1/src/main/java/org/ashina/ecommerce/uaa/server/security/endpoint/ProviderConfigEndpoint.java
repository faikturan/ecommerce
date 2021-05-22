package org.ashina.ecommerce.uaa.server.security.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class ProviderConfigEndpoint {

    /**
     * https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderConfig
     */
    @GetMapping("/.well-known/openid-configuration")
    public Map<String, Object> openIdConfiguration(UriComponentsBuilder builder) {
        log.debug("Access /.well-known/openid-configuration ...");
        Map<String, Object> map = new HashMap<>();
        map.put("issuer", builder.build().toString());
        map.put("authorization_endpoint", builder.replacePath("oauth/authorize").build().toString());
        map.put("token_endpoint", builder.replacePath("oauth/token").build().toString());
        map.put("userinfo_endpoint", builder.replacePath("userinfo").build().toString());
        map.put("jwks_uri", builder.replacePath("oauth/token_keys").build().toString());
        map.put("subject_types_supported", Collections.singletonList("public"));
        return map;
    }

}
