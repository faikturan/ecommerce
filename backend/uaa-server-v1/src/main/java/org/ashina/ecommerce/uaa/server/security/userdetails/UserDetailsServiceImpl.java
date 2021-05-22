package org.ashina.ecommerce.uaa.server.security.userdetails;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.uaa.server.entity.Account;
import org.ashina.ecommerce.uaa.server.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    // Dependencies
    // -----------------------------------------------------------------------------------------------------------------

    private final AccountRepository accountRepository;

    // Methods
    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + username));
        return new UserDetailsImpl(account);
    }
}
