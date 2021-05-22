package org.ashina.ecommerce.uaa.server.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.uaa.server.dto.request.CreateAccountRequest;
import org.ashina.ecommerce.uaa.server.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {

    // Dependencies
    // -----------------------------------------------------------------------------------------------------------------

    private final AccountService accountService;

    // APIs
    // -----------------------------------------------------------------------------------------------------------------

    @PostMapping("/api/v1/accounts")
    public ResponseEntity<Void> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        accountService.createAccount(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
