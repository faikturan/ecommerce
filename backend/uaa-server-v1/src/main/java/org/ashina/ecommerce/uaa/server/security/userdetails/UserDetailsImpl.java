package org.ashina.ecommerce.uaa.server.security.userdetails;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.uaa.server.entity.Account;
import org.ashina.ecommerce.uaa.server.service.AuthorizationService;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class UserDetailsImpl extends User {

    private String id;

    public UserDetailsImpl(Account account) {
        super(account.getEmail(), account.getPassword(), AuthorizationService.asGrantedAuthorities(account.getRoles()));
        this.id = account.getId();
    }
}
