package org.ashina.ecommerce.uaa.server.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ashina.ecommerce.uaa.server.dto.request.CreateAccountRequest;
import org.ashina.ecommerce.uaa.server.entity.Account;
import org.ashina.ecommerce.uaa.server.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    // Dependencies
    // -----------------------------------------------------------------------------------------------------------------

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    // Methods
    // -----------------------------------------------------------------------------------------------------------------

    public void createAccount(CreateAccountRequest request) {
        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        Account account = newAccount(request);
        accountRepository.save(account);
    }

    private Account newAccount(CreateAccountRequest request) {
        Account account = new Account();
        if (StringUtils.isNotBlank(request.getId())) {
            // Customer registration
            account.setId(request.getId());
        } else {
            account.setId(UUID.randomUUID().toString());
        }
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRoles(AuthorizationService.asRoles(request.isAdmin()));
        return account;
    }
}
