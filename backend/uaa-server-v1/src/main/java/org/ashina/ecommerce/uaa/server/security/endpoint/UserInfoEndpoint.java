package org.ashina.ecommerce.uaa.server.security.endpoint;

import org.ashina.ecommerce.uaa.server.security.userdetails.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserInfoEndpoint {

    @GetMapping("/userinfo")
    public Map<String, Object> tokenInfo(@AuthenticationPrincipal UserDetailsImpl principal) {
        Map<String, Object> info = new HashMap<>();
        info.put("sub", "ed00bef3-4a4d-4499-a436-f49fa61ce801");
        return info;
    }
}
