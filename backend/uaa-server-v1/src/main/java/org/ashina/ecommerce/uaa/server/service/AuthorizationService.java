package org.ashina.ecommerce.uaa.server.service;

import com.google.common.collect.Sets;
import org.ashina.ecommerce.sharedkernel.security.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorizationService {

    private AuthorizationService() {
    }

    public static Set<Role> asRoles(boolean isAdmin) {
        return isAdmin ? Sets.newHashSet(Role.ROLE_ADMIN, Role.ROLE_MEMBER) : Collections.singleton(Role.ROLE_MEMBER);
    }

    public static List<GrantedAuthority> asGrantedAuthorities(Set<Role> roles) {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }
}
